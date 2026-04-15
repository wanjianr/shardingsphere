package com.app.worktest.pro.constant;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * NHSA H5接口常量类
 * <p>
 * 集中管理所有常量配置，包括：
 * <ul>
 *   <li>API基础URL配置</li>
 *   <li>加密密钥配置</li>
 *   <li>接口路径配置</li>
 *   <li>请求头配置</li>
 *   <li>媒体类型配置</li>
 * </ul>
 * </p>
 *
 * @author MiniMax Agent
 * @version 1.0
 * @since 2026-03-23
 */
public final class NhsaConstants {

    // ==================== 私有构造函数，防止实例化 ====================
    private NhsaConstants() {
        throw new IllegalStateException("常量类不允许实例化");
    }

    // ==================== API基础URL配置 ====================

    /**
     * 数据共享测试环境基础URL
     */
    public static final String BASE_URL_TEST = "https://cas-datashare-test.nhsa.gov.cn:30080/aaoi/web";

    /**
     * 数据共享生产环境基础URL
     */
    public static final String BASE_URL_PROD = "https://cas-datashare.nhsa.gov.cn/aaoi/web";

    /**
     * Token获取接口URL
     */
    public static final String TOKEN_URL = "https://datashare-test.nhsa.gov.cn:30080/aaoi/web/auth/login/authToken";

    /**
     * 接口日志查询URL
     */
    public static final String QUERY_INTFLOG_BY_TOKEN_KEY_URL = "https://datashare-test.nhsa.gov.cn:30080/aoi/web/insurance/queryIntfLogByTokenKey";

    // ==================== 渠道和请求头配置 ====================

    /**
     * 渠道标识
     */
    public static final String CHANNEL = "92";

    /**
     * 媒体类型 - JSON
     */
    public static final String MEDIA_TYPE_JSON = "application/json; charset=utf-8";

    /**
     * 请求头名称 - 签名
     */
    public static final String HEADER_SIGNATURE = "hsa-dsm-signature";

    /**
     * 请求头名称 - 时间戳
     */
    public static final String HEADER_TIMESTAMP = "hsa-dsm-timestamp";

    /**
     * 请求头名称 - 访问密钥
     */
    public static final String HEADER_AK = "hsa-dsm-ak";

    /**
     * 请求头名称 - 渠道
     */
    public static final String HEADER_CHANNEL = "channel";

    /**
     * 请求头名称 - 授权令牌
     */
    public static final String HEADER_AUTHORIZATION = "Authorization";

    /**
     * 授权令牌前缀
     */
    public static final String BEARER_PREFIX = "Bearer ";

    // ==================== 加密密钥配置 ====================

    /**
     * 前端硬编码密钥（用于getOAuthKey接口）
     * 32位十六进制字符串，对应16字节
     */
    public static final String HARDCODED_KEY = "fedcba9876543210fedcba9876543210";

    /**
     * AES加密算法
     */
    public static final String AES_ALGORITHM = "AES";

    /**
     * AES加密模式
     */
    public static final String AES_CIPHER_PADDING = "AES/ECB/PKCS5Padding";

    /**
     * HMAC算法
     */
    public static final String HMAC_ALGORITHM = "HmacSHA256";

    /**
     * 编码格式
     */
    public static final String ENCODING = "UTF-8";

    // ==================== 接口路径配置 ====================

    /**
     * 使用硬编码密钥的接口路径集合
     */
    public static final Set<String> USE_PRIVATE_KEY_URLS = new HashSet<>(
            Arrays.asList(
                    "/auth/security/getOAuthKey"
            )
    );

    /**
     * 响应需要SM4解密的接口路径集合
     */
    public static final Set<String> NEED_DECRYPT_URLS = new HashSet<>(
            Arrays.asList(
                    "/auth/login/v2/checkToken",
                    "/auth/insuAuth/listOptLogByPsnNo",
                    "/auth/insuAuth/listIntfUseLogByPsnNo",
                    "/auth/insuAuth/getInsuInfoByCert",
                    "/auth/insuAuth/getSetlList",
                    "/setl/query/getSetlDetailsById"
            )
    );

    /**
     * 动态密钥接口路径
     */
    public static final String PATH_GET_OAUTH_KEY = "/auth/security/getOAuthKey";

    /**
     * Token验证接口路径
     */
    public static final String PATH_CHECK_TOKEN = "/auth/login/v2/checkToken";

    /**
     * 保存事件追踪接口路径
     */
    public static final String PATH_SAVE_EVENT_TRACK = "/web/eventTrack/saveEventTrack";

    /**
     * 获取参保信息接口路径
     */
    public static final String PATH_GET_INSU_INFO_BY_CERT = "/auth/insuAuth/getInsuInfoByCert";

    /**
     * 获取场景列表接口路径
     */
    public static final String PATH_SCEN_LIST_BY_JOIN_MGT_ID = "/auth/insuAuth/scenListByJoinMgtId";

    /**
     * 获取结算列表接口路径
     */
    public static final String PATH_GET_SETL_LIST = "/auth/insuAuth/getSetlList";

    /**
     * 授权确认接口路径
     */
    public static final String PATH_CONFIRM = "/auth/insuAuth/confirm";

    // ==================== 业务常量配置 ====================

    /**
     * 默认证件类型 - 身份证
     */
    public static final String CERT_TYPE_ID_CARD = "01";

    /**
     * 默认参保地编码
     */
    public static final String DEFAULT_ADMDVS = "100000";

    /**
     * 操作行为编码 - 授权确认
     */
    public static final String OPRT_BHVR_AUTH_CONFIRM = "400";

    /**
     * 操作标志 - 开始
     */
    public static final String OPRT_FLAG_START = "1";

    /**
     * 默认理赔类型
     */
    public static final String CLAIM_TYPE_DEFAULT = "0";

    // ==================== 动态配置属性（通过@Value注入）====================

    /**
     * 访问密钥（AK）- 运行时配置
     */
    public static String AK = "051251d895e3408780aceba262de1f56";

    /**
     * 秘钥（SK）- 运行时配置
     */
    public static String SK = "6654bb58ecd749fc842eaca8b6d03e6f";

    /**
     * 是否为生产环境 - 运行时配置
     */
    public static boolean IS_PRODUCTION = false;

    /**
     * 获取当前环境的基础URL
     *
     * @return 基础URL
     */
    public static String getBaseUrl() {
        return IS_PRODUCTION ? BASE_URL_PROD : BASE_URL_TEST;
    }
}
