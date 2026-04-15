package com.app.worktest.pro.service;

import com.app.worktest.pro.constant.NhsaConstants;
import com.app.worktest.pro.dto.request.CheckTokenRequestDTO;
import com.app.worktest.pro.dto.request.ConfirmRequestDTO;
import com.app.worktest.pro.dto.request.EncryptedRequestDTO;
import com.app.worktest.pro.dto.request.GetInsuInfoRequestDTO;
import com.app.worktest.pro.dto.request.GetSetlListRequestDTO;
import com.app.worktest.pro.dto.request.SaveEventTrackRequestDTO;
import com.app.worktest.pro.dto.response.BaseResponseDTO;
import com.app.worktest.pro.dto.response.CheckTokenResponseDTO;
import com.app.worktest.pro.dto.response.GetInsuInfoResponseDTO;
import com.app.worktest.pro.dto.response.GetSetlListResponseDTO;
import com.app.worktest.pro.dto.response.ScenListResponseDTO;
import com.app.worktest.pro.util.CryptoUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * NHSA H5业务服务类
 * <p>
 * 实现医保H5接口的完整授权流程，包括：
 * <ul>
 *   <li>获取动态密钥</li>
 *   <li>验证用户Token</li>
 *   <li>获取参保信息</li>
 *   <li>获取场景列表</li>
 *   <li>获取结算列表</li>
 *   <li>提交授权确认</li>
 * </ul>
 * </p>
 *
 * @author MiniMax Agent
 * @version 1.0
 * @since 2026-03-23
 */
@Slf4j
@Service
public class NhsaH5Service {

    /**
     * HTTP客户端
     */
    private final RestTemplate restTemplate;

    /**
     * JSON序列化工具
     */
    private final ObjectMapper objectMapper;

    /**
     * 当前Token
     */
    private String currentToken;

    /**
     * 构造函数
     *
     * @param restTemplate HTTP客户端
     * @param objectMapper JSON工具
     */
    public NhsaH5Service(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    // ==================== 业务方法 ====================

    /**
     * 执行完整的H5授权流程
     *
     * @param token      访问Token
     * @param dataScpBegnTime 数据范围开始时间
     * @param dataScpEndTime  数据范围结束时间
     * @param mdtrtType        就诊类型（可为null）
     * @return 授权确认响应
     * @throws NhsaServiceException 服务异常
     */
    public ConfirmResponseDTO executeAuthFlow(String token,
                                              String dataScpCurTime,
                                               String dataScpBegnTime,
                                               String dataScpEndTime,
                                               String mdtrtType) throws NhsaServiceException {
        // 获取当前时间的上一季度所在的年份
        String year = "2025";
        log.info("========== 开始执行H5授权流程 ==========");
        log.info("数据范围: {} ~ {}, 年份列表: {}, 就诊类型: {}",
                dataScpBegnTime, dataScpEndTime, year, mdtrtType);

        this.currentToken = token;

        try {
            // Step 1: 获取动态密钥
            String dynamicKey = getOAuthKey();
            CryptoUtils.setDynamicKey(dynamicKey);
            log.info("动态密钥获取成功");

            // Step 2: 验证Token，获取用户信息
            CheckTokenResponseDTO checkTokenResult = checkToken(token);
            log.info("Token验证成功 - 用户: {}", checkTokenResult.getPsnName());

            // Step 3: 记录事件追踪（第一次）
            saveEventTrack(NhsaConstants.OPRT_BHVR_AUTH_CONFIRM, NhsaConstants.OPRT_FLAG_START);

            // Step 4: 获取参保信息
            GetInsuInfoResponseDTO insuInfo = getInsuInfoByCert(checkTokenResult);
            String insuAdmdvsCode = extractInsuAdmdvsCode(insuInfo);
            log.info("参保信息获取成功 - 统筹区编码: {}", insuAdmdvsCode);

            // Step 5: 获取场景列表
            ScenListResponseDTO scenList = getScenList();
            log.info("场景列表获取成功 - 数量: {}", scenList.getList() != null ? scenList.getList().size() : 0);

            // Step 6-8: 按年份循环获取结算列表并确认
            log.info("处理年份: {}", year);

            // Step 6: 获取结算列表
            GetSetlListResponseDTO setlList = getSetlList(
                    checkTokenResult, dataScpBegnTime, dataScpCurTime, year, mdtrtType);

            // 校验结算列表是否为空
            if (setlList == null || setlList.getList() == null || setlList.getList().isEmpty()) {
                log.warn("年份 {} 的结算列表为空，跳过该年份", year);
                return null;
            }
            log.info("结算列表获取成功 - 数量: {}", setlList.getList().size());

            // Step 7: 记录事件追踪（第二次）
            saveEventTrack(NhsaConstants.OPRT_BHVR_AUTH_CONFIRM, NhsaConstants.OPRT_FLAG_START);

            // Step 8: 提交授权确认
            ConfirmRequestDTO confirmRequest = buildConfirmRequest(
                    insuAdmdvsCode, scenList, setlList, dataScpBegnTime, dataScpEndTime);

            ConfirmResponseDTO confirmResponse = confirm(confirmRequest);
            log.info("年份 {} 授权确认完成 - 响应码: {}", year, confirmResponse.getCode());


            log.info("========== H5授权流程执行完成 ==========");
            return confirmResponse;

        } catch (Exception e) {
            log.error("H5授权流程执行异常: {}", e.getMessage(), e);
            throw new NhsaServiceException("H5授权流程执行异常", e);
        }
    }

    // ==================== Step 1: 获取动态密钥 ====================

    /**
     * 获取动态密钥
     * <p>
     * 调用getOAuthKey接口，使用硬编码密钥加密请求，
     * 解密响应获取后续接口使用的动态密钥。
     * </p>
     *
     * @return 动态密钥字符串
     * @throws NhsaServiceException 获取失败时抛出
     */
    public String getOAuthKey() throws NhsaServiceException {
        log.info("Step 1: 获取动态密钥 - 接口: {}", NhsaConstants.PATH_GET_OAUTH_KEY);

        try {
            // 构建空请求体
            String rawJson = "{}";

            // 加密请求
            EncryptedRequestDTO encryptedRequest = CryptoUtils.encryptRequest(rawJson, NhsaConstants.PATH_GET_OAUTH_KEY);

            // 发送请求
            BaseResponseDTO<Object> response = sendEncryptedRequest(
                    NhsaConstants.PATH_GET_OAUTH_KEY, encryptedRequest);

            // 提取动态密钥
            String dataField = (String) response.getData();
            log.debug("动态密钥响应字段: {}", dataField);

            // 兼容明文和密文两种形式
            String dynamicKey = extractDynamicKey(dataField, encryptedRequest.getNoceStr());
            log.info("动态密钥提取成功");

            return dynamicKey;

        } catch (Exception e) {
            log.error("获取动态密钥失败: {}", e.getMessage());
            throw new NhsaServiceException("获取动态密钥失败", e);
        }
    }

    /**
     * 兼容明文和密文的动态密钥提取
     */
    private String extractDynamicKey(String dataField, String nonce) throws Exception {
        boolean isPlainHex = dataField.length() == 32
                && dataField.chars().allMatch(c -> "0123456789abcdefABCDEF".indexOf(c) >= 0);

        if (isPlainHex) {
            log.debug("动态密钥为明文形式");
            return dataField;
        } else {
            log.debug("动态密钥为密文形式，解密中...");
            String raw = CryptoUtils.sm4Decrypt(dataField, NhsaConstants.HARDCODED_KEY, nonce);
            JsonNode node = objectMapper.readTree(raw);
            return node.isTextual() ? node.asText() : raw;
        }
    }

    // ==================== Step 2: 验证Token ====================

    /**
     * 验证Token并获取用户信息
     *
     * @param token 用户Token
     * @return 用户信息
     * @throws NhsaServiceException 验证失败时抛出
     */
    public CheckTokenResponseDTO checkToken(String token) throws NhsaServiceException {
        log.info("Step 2: 验证Token - 接口: {}", NhsaConstants.PATH_CHECK_TOKEN);

        try {
            CheckTokenRequestDTO request = CheckTokenRequestDTO.builder()
                    .token(token)
                    .build();

            String rawJson = objectMapper.writeValueAsString(request);
            EncryptedRequestDTO encryptedRequest = CryptoUtils.encryptRequest(rawJson, NhsaConstants.PATH_CHECK_TOKEN);

            // 发送请求
            JsonNode responseNode = sendEncryptedRequestJson(NhsaConstants.PATH_CHECK_TOKEN, encryptedRequest);

            // 解密响应
            String encData = responseNode.path("data").path("data").asText();
            String decrypted = CryptoUtils.decryptResponse(encData, encryptedRequest.getNoceStr(), NhsaConstants.PATH_CHECK_TOKEN);

            CheckTokenResponseDTO result = objectMapper.readValue(decrypted, CheckTokenResponseDTO.class);
            log.info("Token验证成功 - 用户: {}, 证件号: {}",
                    result.getPsnName(), maskCertNo(result.getInvestigatedCertno()));

            return result;

        } catch (Exception e) {
            log.error("Token验证失败: {}", e.getMessage());
            throw new NhsaServiceException("Token验证失败", e);
        }
    }

    // ==================== Step 3: 保存事件追踪 ====================

    /**
     * 保存事件追踪
     *
     * @param oprtBhvr 操作行为编码
     * @param oprtFlag 操作标志
     * @throws NhsaServiceException 保存失败时抛出
     */
    public void saveEventTrack(String oprtBhvr, String oprtFlag) throws NhsaServiceException {
        log.debug("Step 3/7: 保存事件追踪 - 行为: {}, 标志: {}", oprtBhvr, oprtFlag);

        try {
            SaveEventTrackRequestDTO request = SaveEventTrackRequestDTO.builder()
                    .oprtBhvr(oprtBhvr)
                    .oprtFlag(oprtFlag)
                    .build();

            String rawJson = objectMapper.writeValueAsString(request);
            EncryptedRequestDTO encryptedRequest = CryptoUtils.encryptRequest(rawJson, NhsaConstants.PATH_SAVE_EVENT_TRACK);

            sendEncryptedRequest(NhsaConstants.PATH_SAVE_EVENT_TRACK, encryptedRequest);
            log.debug("事件追踪保存成功");

        } catch (Exception e) {
            log.warn("事件追踪保存失败（不影响流程）: {}", e.getMessage());
        }
    }

    // ==================== Step 4: 获取参保信息 ====================

    /**
     * 根据证件信息获取参保信息
     *
     * @param checkTokenResult Token验证结果（包含用户信息）
     * @return 参保信息
     * @throws NhsaServiceException 获取失败时抛出
     */
    public GetInsuInfoResponseDTO getInsuInfoByCert(CheckTokenResponseDTO checkTokenResult) throws NhsaServiceException {
        log.info("Step 4: 获取参保信息 - 接口: {}", NhsaConstants.PATH_GET_INSU_INFO_BY_CERT);

        try {
            GetInsuInfoRequestDTO request = GetInsuInfoRequestDTO.builder()
                    .psnName(checkTokenResult.getPsnName())
                    .investigatedCertno(checkTokenResult.getInvestigatedCertno())
                    .investigatedCertType(checkTokenResult.getInvestigatedCertType())
                    .build();

            String rawJson = objectMapper.writeValueAsString(request);
            EncryptedRequestDTO encryptedRequest = CryptoUtils.encryptRequest(rawJson, NhsaConstants.PATH_GET_INSU_INFO_BY_CERT);

            JsonNode responseNode = sendEncryptedRequestJson(NhsaConstants.PATH_GET_INSU_INFO_BY_CERT, encryptedRequest);

            // 解密响应
            String decrypted = CryptoUtils.decryptResponse(
                    responseNode.path("data").asText(),
                    encryptedRequest.getNoceStr(),
                    NhsaConstants.PATH_GET_INSU_INFO_BY_CERT);

            // 解析响应（需要提取内层data字段）
            JsonNode decryptedNode = objectMapper.readTree(decrypted);
            GetInsuInfoResponseDTO result = objectMapper.treeToValue(
                    decryptedNode.path("data"),
                    GetInsuInfoResponseDTO.class);

            log.info("参保信息获取成功 - 统筹区数量: {}",
                    result.getInsuAdmdvsNames() != null ? result.getInsuAdmdvsNames().size() : 0);

            return result;

        } catch (Exception e) {
            log.error("获取参保信息失败: {}", e.getMessage());
            throw new NhsaServiceException("获取参保信息失败", e);
        }
    }

    /**
     * 提取参保统筹区编码（第一个）
     */
    private String extractInsuAdmdvsCode(GetInsuInfoResponseDTO insuInfo) throws Exception {
        if (insuInfo == null || insuInfo.getInsuAdmdvsNames() == null || insuInfo.getInsuAdmdvsNames().isEmpty()) {
            throw new NhsaServiceException("参保信息为空，无法获取统筹区编码");
        }
        return insuInfo.getInsuAdmdvsNames().get(0).getCode();
    }

    // ==================== Step 5: 获取场景列表 ====================

    /**
     * 获取授权场景列表
     *
     * @return 场景列表响应
     * @throws NhsaServiceException 获取失败时抛出
     */
    public ScenListResponseDTO getScenList() throws NhsaServiceException {
        log.info("Step 5: 获取场景列表 - 接口: {}", NhsaConstants.PATH_SCEN_LIST_BY_JOIN_MGT_ID);

        try {
            String rawJson = "{}";
            EncryptedRequestDTO encryptedRequest = CryptoUtils.encryptRequest(rawJson, NhsaConstants.PATH_SCEN_LIST_BY_JOIN_MGT_ID);

            JsonNode responseNode = sendEncryptedRequestJson(NhsaConstants.PATH_SCEN_LIST_BY_JOIN_MGT_ID, encryptedRequest);

            // 该接口响应未加密，直接解析data.list
            ScenListResponseDTO result = objectMapper.treeToValue(responseNode.path("data"), ScenListResponseDTO.class);

            log.info("场景列表获取成功 - 数量: {}",
                    result.getList() != null ? result.getList().size() : 0);

            return result;

        } catch (Exception e) {
            log.error("获取场景列表失败: {}", e.getMessage());
            throw new NhsaServiceException("获取场景列表失败", e);
        }
    }

    // ==================== Step 6: 获取结算列表 ====================

    /**
     * 获取结算列表
     *
     * @param checkTokenResult 用户信息
     * @param dataScpBegnTime  数据范围开始时间
     * @param dataScpEndTime   数据范围结束时间
     * @param year             年份
     * @param mdtrtType        就诊类型
     * @return 结算列表响应
     * @throws NhsaServiceException 获取失败时抛出
     */
    public GetSetlListResponseDTO getSetlList(CheckTokenResponseDTO checkTokenResult,
                                               String dataScpBegnTime,
                                               String dataScpEndTime,
                                               String year,
                                               String mdtrtType) throws NhsaServiceException {
        log.info("Step 6: 获取结算列表 - 接口: {}, 年份: {}", NhsaConstants.PATH_GET_SETL_LIST, year);

        try {
            GetSetlListRequestDTO request = GetSetlListRequestDTO.builder()
                    .investigatedCertno(checkTokenResult.getInvestigatedCertno())
                    .investigatedCertType(checkTokenResult.getInvestigatedCertType())
                    .psnName(checkTokenResult.getPsnName())
                    .dataScpBegnTime(dataScpBegnTime)
                    .dataScpEndTime(dataScpEndTime)
                    .mdtrtType(mdtrtType)
                    .year(year)
                    .build();

            String rawJson = objectMapper.writeValueAsString(request);
            EncryptedRequestDTO encryptedRequest = CryptoUtils.encryptRequest(rawJson, NhsaConstants.PATH_GET_SETL_LIST);

            JsonNode responseNode = sendEncryptedRequestJson(NhsaConstants.PATH_GET_SETL_LIST, encryptedRequest);

            // 解密响应
            String decrypted = CryptoUtils.decryptResponse(
                    responseNode.path("data").asText(),
                    encryptedRequest.getNoceStr(),
                    NhsaConstants.PATH_GET_SETL_LIST);

            GetSetlListResponseDTO result = objectMapper.readValue(decrypted, GetSetlListResponseDTO.class);

            log.info("结算列表获取成功 - 年份: {}, 数量: {}",
                    year, result.getList() != null ? result.getList().size() : 0);

            return result;

        } catch (Exception e) {
            log.error("获取结算列表失败: {}", e.getMessage());
            throw new NhsaServiceException("获取结算列表失败", e);
        }
    }

    // ==================== Step 8: 授权确认 ====================

    /**
     * 构建授权确认请求
     */
    private ConfirmRequestDTO buildConfirmRequest(String insuAdmdvsCode,
                                                    ScenListResponseDTO scenList,
                                                    GetSetlListResponseDTO setlList,
                                                    String dataScpBegnTime,
                                                    String dataScpEndTime) {
        // 构建授权场景列表
        List<ConfirmRequestDTO.AuthScenDTO> authScenList = new ArrayList<>();
        if (scenList != null && scenList.getList() != null) {
            for (ScenListResponseDTO.ScenItemDTO scen : scenList.getList()) {
                authScenList.add(ConfirmRequestDTO.AuthScenDTO.builder()
                        .scenId(scen.getScenId())
                        .build());
            }
        }

        // 构建结算列表
        List<ConfirmRequestDTO.SetlInfoDTO> setlInfoList = new ArrayList<>();
        if (setlList != null && setlList.getList() != null) {
            for (GetSetlListResponseDTO.SetlItemDTO setl : setlList.getList()) {
                setlInfoList.add(ConfirmRequestDTO.SetlInfoDTO.builder()
                        .setlId(setl.getSetlId())
                        .setlTime(setl.getSetlTime())
                        .build());
            }
        }

        // 构建授权参与DTO
        ConfirmRequestDTO.AuthJoinDTO authJoinDTO = ConfirmRequestDTO.AuthJoinDTO.builder()
                .usedEndTime(dataScpEndTime)
                .dataScpBegnTime(dataScpBegnTime)
                .dataScpEndTime(dataScpEndTime)
                .insuAdmdvs(insuAdmdvsCode)
                .authScenDDTOList(authScenList)
                .build();

        return ConfirmRequestDTO.builder()
                .authJoinDTO(authJoinDTO)
                .setlList(setlInfoList)
                .build();
    }

    /**
     * 提交授权确认
     *
     * @param request 确认请求
     * @return 确认响应
     * @throws NhsaServiceException 提交失败时抛出
     */
    public ConfirmResponseDTO confirm(ConfirmRequestDTO request) throws NhsaServiceException {
        log.info("Step 8: 提交授权确认 - 接口: {}", NhsaConstants.PATH_CONFIRM);

        try {
            String rawJson = objectMapper.writeValueAsString(request);
            log.debug("授权确认请求: {}", rawJson);

            EncryptedRequestDTO encryptedRequest = CryptoUtils.encryptRequest(rawJson, NhsaConstants.PATH_CONFIRM);

            BaseResponseDTO<Object> stringBaseResponseDTO = sendEncryptedRequest(NhsaConstants.PATH_CONFIRM, encryptedRequest);
            log.info("授权确认响应: {}", stringBaseResponseDTO);

            return ConfirmResponseDTO.builder()
                    .code(0)
                    .data(stringBaseResponseDTO.getData())
                    .message("授权确认成功")
                    .build();

        } catch (JsonProcessingException e) {
            log.error("序列化确认请求失败: {}", e.getMessage());
            throw new NhsaServiceException("序列化确认请求失败", e);
        } catch (Exception e) {
            log.error("提交授权确认失败: {}", e.getMessage());
            throw new NhsaServiceException("提交授权确认失败", e);
        }
    }

    // ==================== HTTP请求发送 ====================

    /**
     * 发送加密请求（返回完整响应）
     */
    private JsonNode sendEncryptedRequestJson(String path, EncryptedRequestDTO request) throws NhsaServiceException {
        String url = NhsaConstants.getBaseUrl() + path;

        log.debug("发送加密请求 - URL: {}, Nonce: {}, Timestamp: {}",
                url, request.getNoceStr(), request.getTimestamp());

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set(NhsaConstants.HEADER_CHANNEL, NhsaConstants.CHANNEL);
            headers.set(NhsaConstants.HEADER_AUTHORIZATION, NhsaConstants.BEARER_PREFIX + currentToken);

            HttpEntity<EncryptedRequestDTO> httpEntity = new HttpEntity<>(request, headers);

            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    httpEntity,
                    String.class
            );

            String responseBody = responseEntity.getBody();
            log.debug("加密请求响应: {}", responseBody);

            if (responseBody == null || responseBody.isEmpty()) {
                throw new NhsaServiceException("响应为空");
            }

            return objectMapper.readTree(responseBody);

        } catch (RestClientException e) {
            log.error("HTTP请求失败: {}", e.getMessage());
            throw new NhsaServiceException("HTTP请求失败: " + e.getMessage(), e);
        } catch (Exception e) {
            log.error("解析响应失败: {}", e.getMessage());
            throw new NhsaServiceException("解析响应失败", e);
        }
    }

    /**
     * 发送加密请求（返回基础响应）
     */
    private BaseResponseDTO<Object> sendEncryptedRequest(String path, EncryptedRequestDTO request) throws Exception {
        JsonNode jsonNode = sendEncryptedRequestJson(path, request);

        try {
            return objectMapper.convertValue(jsonNode, new TypeReference<BaseResponseDTO<Object>>() {});
        } catch (Exception e) {
            throw new NhsaServiceException("解析响应失败", e);
        }
    }

    /**
     * 屏蔽证件号码（用于日志输出）
     */
    private String maskCertNo(String certNo) {
        if (certNo == null || certNo.length() <= 6) {
            return "****";
        }
        return certNo.substring(0, 6) + "****" + certNo.substring(certNo.length() - 4);
    }

    /**
     * NHSA服务异常类
     */
    public static class NhsaServiceException extends Exception {
        public NhsaServiceException(String message) {
            super(message);
        }

        public NhsaServiceException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    // ==================== 确认响应DTO ====================

    /**
     * 确认响应DTO
     */
    @lombok.Data
    @lombok.Builder
    @lombok.NoArgsConstructor
    @lombok.AllArgsConstructor
    public static class ConfirmResponseDTO {
        private Integer code;
        private String message;
        private Object data;
    }
}
