package com.app.worktest.pro.controller;

import com.app.worktest.pro.dto.response.BaseResponseDTO;
import com.app.worktest.pro.dto.token.TokenRequestDTO;
import com.app.worktest.pro.service.NhsaH5Service;
import com.app.worktest.pro.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/**
 * NHSA H5控制器
 * <p>
 * 提供医保H5接口的HTTP端点，包括：
 * <ul>
 *   <li>Token获取接口</li>
 *   <li>H5授权流程执行接口</li>
 * </ul>
 * </p>
 *
 * @author MiniMax Agent
 * @version 1.0
 * @since 2026-03-23
 */
@Slf4j
@RestController
@RequestMapping("/api/nhsa")
public class NhsaController {

    /**
     * Token服务
     */
    private final TokenService tokenService;

    /**
     * NHSA H5服务
     */
    private final NhsaH5Service nhsaH5Service;

    /**
     * 构造函数
     *
     * @param tokenService    Token服务
     * @param nhsaH5Service NHSA H5服务
     */
    public NhsaController(TokenService tokenService, NhsaH5Service nhsaH5Service) {
        this.tokenService = tokenService;
        this.nhsaH5Service = nhsaH5Service;
    }

    // ==================== 端点定义 ====================

    /**
     * 获取BankH5访问Token
     * <p>
     * 使用AK/SK认证方式获取访问Token，用于后续接口调用。
     * </p>
     *
     * @param request Token请求参数
     * @return Token响应
     */
    @PostMapping("/token")
    public ResponseEntity<BaseResponseDTO<String>> getToken(@RequestBody TokenRequestDTO request) {
        log.info("收到Token获取请求 - 参保地: {}, 证件类型: {}, 证件号: {}",
                request.getAdmdvs(),
                request.getPsnCertType(),
                maskCertNo(request.getCertno()));

        try {
            String token = tokenService.getBankH5Token(request);

            BaseResponseDTO<String> response = BaseResponseDTO.<String>builder()
                    .code(0)
                    .message("Token获取成功")
                    .data(token)
                    .build();

            log.info("Token获取成功");
            return ResponseEntity.ok(response);

        } catch (TokenService.TokenException e) {
            log.error("Token获取失败: {}", e.getMessage());

            BaseResponseDTO<String> response = BaseResponseDTO.<String>builder()
                    .code(-1)
                    .message("Token获取失败: " + e.getMessage())
                    .build();

            return ResponseEntity.internalServerError().body(response);
        }
    }

    /**
     * 执行H5授权流程
     * <p>
     * 执行完整的医保H5授权流程，包括：
     * <ol>
     *   <li>获取动态密钥</li>
     *   <li>验证Token</li>
     *   <li>获取参保信息</li>
     *   <li>获取场景列表</li>
     *   <li>获取结算列表</li>
     *   <li>提交授权确认</li>
     * </ol>
     * </p>
     *
     * @param token      访问Token
     * @param years      年份列表（逗号分隔，如：2024,2025）
     * @param mdtrtType  就诊类型（可选，空表示全部）
     * @return 授权结果
     */
    @PostMapping("/auth/flow")
    public ResponseEntity<BaseResponseDTO<String>> executeAuthFlow(
            @RequestHeader("Authorization") String token,
            @RequestParam("years") String years,
            @RequestParam(value = "mdtrtType", required = false) String mdtrtType) {

        // 移除Bearer前缀
        String cleanToken = token.replace("Bearer ", "").trim();
        log.info("收到授权流程请求 - Token长度: {}, 年份: {}, 就诊类型: {}",
                cleanToken.length(), years, mdtrtType);

        try {

            // 计算时间范围
            LocalDate now = LocalDate.now();
            String dataScpCurTime = now.toString().replace("-", ".");
            String dataScpBegnTime = now.minusYears(2).toString().replace("-", ".");
            String dataScpNextTime = now.plusYears(1).toString().replace("-", ".");

            // 执行授权流程
            NhsaH5Service.ConfirmResponseDTO result = nhsaH5Service.executeAuthFlow(
                    cleanToken,
                    dataScpCurTime,
                    dataScpBegnTime,
                    dataScpNextTime,
                    mdtrtType
            );

            BaseResponseDTO<String> response = BaseResponseDTO.<String>builder()
                    .code(result.getCode())
                    .message(result.getMessage())
                    .build();

            log.info("授权流程执行完成 - 结果: {}", result.getMessage());
            return ResponseEntity.ok(response);

        } catch (NhsaH5Service.NhsaServiceException e) {
            log.error("授权流程执行失败: {}", e.getMessage(), e);

            BaseResponseDTO<String> response = BaseResponseDTO.<String>builder()
                    .code(-1)
                    .message("授权流程执行失败: " + e.getMessage())
                    .build();

            return ResponseEntity.internalServerError().body(response);
        }
    }

    /**
     * 执行H5授权流程（简化版，使用固定参数）
     * <p>
     * 适用于开发测试阶段，使用固定的时间范围和默认参数。
     * </p>
     *
     * @param token 访问Token
     * @return 授权结果
     */
    @PostMapping("/auth/flow/simple")
    public ResponseEntity<BaseResponseDTO<String>> executeAuthFlowSimple(
            @RequestHeader("Authorization") String token) {

        String cleanToken = token.replace("Bearer ", "").trim();
        log.info("收到简化授权流程请求 - Token长度: {}", cleanToken.length());

        try {
            // 使用固定参数
            List<String> yearList = Arrays.asList("2025");
            String dataScpBegnTime = "2024.03.19";
            String dataScpCurTime = "2026.03.19";
            String dataScpEndTime = "2027.03.19";

            // 执行授权流程
            NhsaH5Service.ConfirmResponseDTO result = nhsaH5Service.executeAuthFlow(
                    cleanToken,
                    dataScpCurTime,
                    dataScpBegnTime,
                    dataScpEndTime,
                    null
            );

            BaseResponseDTO<String> response = BaseResponseDTO.<String>builder()
                    .code(result.getCode())
                    .message(result.getMessage())
                    .build();

            return ResponseEntity.ok(response);

        } catch (NhsaH5Service.NhsaServiceException e) {
            log.error("授权流程执行失败: {}", e.getMessage(), e);

            BaseResponseDTO<String> response = BaseResponseDTO.<String>builder()
                    .code(-1)
                    .message("授权流程执行失败: " + e.getMessage())
                    .build();

            return ResponseEntity.internalServerError().body(response);
        }
    }

    // ==================== 工具方法 ====================

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
}
