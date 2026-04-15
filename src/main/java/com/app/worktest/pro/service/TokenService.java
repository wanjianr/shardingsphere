package com.app.worktest.pro.service;

import com.app.worktest.pro.constant.NhsaConstants;
import com.app.worktest.pro.dto.token.TokenRequestDTO;
import com.app.worktest.pro.dto.token.TokenResponseDTO;
import com.app.worktest.pro.util.AesUtils;
import com.app.worktest.pro.util.HmacUtils;
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

/**
 * Token服务类
 * <p>
 * 负责与医保数据共享平台Token认证接口交互，
 * 提供Token获取和验证功能。
 * </p>
 *
 * <p>
 * 主要功能：
 * <ul>
 *   <li>获取BankH5访问Token</li>
 *   <li>创建HMAC签名</li>
 *   <li>AES加密请求参数</li>
 * </ul>
 * </p>
 *
 * @author MiniMax Agent
 * @version 1.0
 * @since 2026-03-23
 */
@Slf4j
@Service
public class TokenService {

    /**
     * HTTP客户端
     */
    private final RestTemplate restTemplate;

    /**
     * JSON序列化工具
     */
    private final ObjectMapper objectMapper;

    /**
     * 构造函数
     *
     * @param restTemplate HTTP客户端
     * @param objectMapper JSON工具
     */
    public TokenService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    /**
     * 获取BankH5访问Token
     * <p>
     * 使用AK/SK认证方式，通过以下步骤获取Token：
     * <ol>
     *   <li>创建HMAC签名</li>
     *   <li>AES加密请求参数</li>
     *   <li>发送Token获取请求</li>
     *   <li>解析并返回Token</li>
     * </ol>
     * </p>
     *
     * @param request 请求参数
     * @return 访问Token字符串
     * @throws TokenException Token获取失败时抛出
     */
    public String getBankH5Token(TokenRequestDTO request) throws TokenException {
        log.info("开始获取BankH5 Token - 参保地: {}, 证件类型: {}, 证件号: {}",
                request.getAdmdvs(),
                request.getPsnCertType(),
                maskCertNo(request.getCertno()));

        try {
            // 1. 创建HMAC签名
            String timestamp = String.valueOf(System.currentTimeMillis());
            String signature = HmacUtils.createSignature(
                    NhsaConstants.AK,
                    NhsaConstants.SK,
                    timestamp
            );
            log.debug("HMAC签名创建成功 - Timestamp: {}", timestamp);

            // 2. 序列化请求参数
            String jsonParam = objectMapper.writeValueAsString(request);
            log.debug("请求参数JSON: {}", jsonParam);

            // 3. AES加密请求参数
            String encryptedData = AesUtils.encrypt(jsonParam, signature);
            log.debug("AES加密成功");

            // 4. 构建请求体
            TokenRequestEnvelope envelope = new TokenRequestEnvelope();
            envelope.setData(encryptedData);

            // 5. 发送HTTP请求
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Accept", "application/json");
            headers.set(NhsaConstants.HEADER_SIGNATURE, signature);
            headers.set(NhsaConstants.HEADER_TIMESTAMP, timestamp);
            headers.set(NhsaConstants.HEADER_AK, NhsaConstants.AK);

            HttpEntity<TokenRequestEnvelope> httpEntity = new HttpEntity<>(envelope, headers);

            log.debug("发送Token获取请求 - URL: {}", NhsaConstants.TOKEN_URL);

            TokenResponseDTO tokenResponse = restTemplate.postForObject(
                    NhsaConstants.TOKEN_URL,
                    httpEntity,
                    TokenResponseDTO.class
            );

            if (tokenResponse == null) {
                throw new TokenException("Token获取响应为空");
            }

            if (!tokenResponse.isSuccess()) {
                throw new TokenException("Token获取失败 - " + tokenResponse.getMessage());
            }

            String token = tokenResponse.getData();
            log.info("Token获取成功 - Token长度: {}", token != null ? token.length() : 0);

            return token;

        } catch (HmacUtils.HmacException e) {
            log.error("HMAC签名创建失败: {}", e.getMessage());
            throw new TokenException("HMAC签名创建失败", e);
        } catch (AesUtils.AesException e) {
            log.error("AES加密失败: {}", e.getMessage());
            throw new TokenException("AES加密失败", e);
        } catch (RestClientException e) {
            log.error("HTTP请求失败: {}", e.getMessage());
            throw new TokenException("HTTP请求失败", e);
        } catch (Exception e) {
            log.error("Token获取异常: {}", e.getMessage());
            throw new TokenException("Token获取异常", e);
        }
    }

    /**
     * 屏蔽证件号码（用于日志输出）
     *
     * @param certNo 证件号码
     * @return 屏蔽后的证件号
     */
    private String maskCertNo(String certNo) {
        if (certNo == null || certNo.length() <= 6) {
            return "****";
        }
        return certNo.substring(0, 6) + "****" + certNo.substring(certNo.length() - 4);
    }

    /**
     * Token请求信封
     * <p>
     * 用于封装加密后的请求数据。
     * </p>
     */
    private static class TokenRequestEnvelope {
        private String data;

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }
    }

    /**
     * Token服务异常类
     */
    public static class TokenException extends Exception {
        public TokenException(String message) {
            super(message);
        }

        public TokenException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
