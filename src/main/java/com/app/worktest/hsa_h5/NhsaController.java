package com.app.worktest.hsa_h5;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.app.worktest.hsa_h5.CryptoVerify.RequestBody;
import okhttp3.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 完整流程控制器，对应 Python 版 controller.py。
 * <p>
 * 调用顺序：
 * <ol>
 *   <li>getOAuthKey  → 获取动态密钥</li>
 *   <li>checkToken   → 验证 token，提取用户信息</li>
 *   <li>saveEventTrack（第一次）</li>
 *   <li>getInsuInfoByCert → 获取参保信息</li>
 *   <li>scenListByJoinMgtId → 获取场景列表</li>
 *   <li>getSetlList → 获取结算列表</li>
 *   <li>saveEventTrack（第二次）</li>
 *   <li>confirm → 提交授权确认</li>
 * </ol>
 * </p>
 */
public class NhsaController {

    // ----------------------------------------------------------------
    // 配置
    // ----------------------------------------------------------------
    private static final String BASE_URL   = "";
    private static String TOKEN_STR  = "";
    private static final String CHANNEL    = "92";
    private static final MediaType JSON_MT = MediaType.get("application/json; charset=utf-8");

    private static final OkHttpClient HTTP   = new OkHttpClient();
    private static final ObjectMapper  MAPPER = new ObjectMapper();

    // ----------------------------------------------------------------
    // 入口
    // ----------------------------------------------------------------
    public static void main(String[] args) throws Exception {
        TOKEN_STR = GenTokenService.getBankH5Token();
        new NhsaController().run();
    }

    public void run() throws Exception {
        // todo 通过日期工具类获取dataScpCurTime、dataScpBegnTime、dataScpNextTime
        // 获取当前时间
        String dataScpCurTime = "2026.03.19";
        // 获取dataScpCurTime的前两年
        String dataScpBegnTime = "2024.03.19";
        // 获取dataScpCurTime的后一年
        String dataScpNextTime = "2027.03.19";
        // todo 通过通过@Value获取 yearList
        List<String> yearList = Arrays.asList("2025");
        String mdtrtType = null;

        // ── Step 1: getOAuthKey ──────────────────────────────────────
        String path1  = "/auth/security/getOAuthKey";
        RequestBody body1 = CryptoVerify.encryptRequest("{}", path1);
        System.out.println("[getOAuthKey] 请求体: " + body1);

        JsonNode resp1 = post(BASE_URL + path1, body1);
        System.out.println("[getOAuthKey] 响应: " + resp1);

        String dataField = resp1.path("data").asText();
        CryptoVerify.dynamicKey = extractDynamicKey(dataField, body1.noceStr);
        System.out.println("[动态密钥] 已设置: " + CryptoVerify.dynamicKey);

        // ── Step 2: checkToken ───────────────────────────────────────
        String path2  = "/auth/login/v2/checkToken";
        String raw2   = toJson(MAPPER.createObjectNode().put("token", TOKEN_STR));
        RequestBody body2 = CryptoVerify.encryptRequest(raw2, path2);

        JsonNode resp2 = post(BASE_URL + path2, body2);
        System.out.println("[checkToken] 响应(加密): " + resp2);

        // 取 data.data 字段解密
        String encData2 = resp2.path("data").path("data").asText();
        JsonNode checkTokenResult = MAPPER.readTree(
                CryptoVerify.decryptResponse(encData2, body2.noceStr, path2));
        System.out.println("[checkToken] 解密结果: " + checkTokenResult.toPrettyString());

        String psnName              = checkTokenResult.path("psnName").asText();
        String investigatedCertno   = checkTokenResult.path("investigatedCertno").asText();
        String investigatedCertType = checkTokenResult.path("investigatedCertType").asText();

        // ── Step 3: saveEventTrack（第一次）──────────────────────────
        String path3  = "/web/eventTrack/saveEventTrack";
        String raw3   = toJson(MAPPER.createObjectNode()
                .put("oprtBhvr", "400").put("oprtFlag", "1"));
        RequestBody body3 = CryptoVerify.encryptRequest(raw3, path3);

        JsonNode resp3 = post(BASE_URL + "/eventTrack/saveEventTrack", body3);
        System.out.println("[saveEventTrack #1] 响应: " + resp3);

        // ── Step 4: getInsuInfoByCert ────────────────────────────────
        String path4 = "/auth/insuAuth/getInsuInfoByCert";
        ObjectNode raw4Node = MAPPER.createObjectNode()
                .put("psnName", psnName)
                .put("investigatedCertno", investigatedCertno)
                .put("investigatedCertType", investigatedCertType);
        RequestBody body4 = CryptoVerify.encryptRequest(toJson(raw4Node), path4);

        JsonNode resp4 = post(BASE_URL + path4, body4);
        System.out.println("[getInsuInfoByCert] 响应(加密): " + resp4);

        JsonNode decResp4 = MAPPER.readTree(
                CryptoVerify.decryptResponse(resp4.path("data").asText(), body4.noceStr, path4));
        System.out.println("[getInsuInfoByCert] 解密结果: " + decResp4.toPrettyString());

        // 取第一个 insuAdmdvs code
        String insuAdmdvsCode = decResp4.path("data")
                .path("insuAdmdvsNames").path(0).path("code").asText();

        // ── Step 5: scenListByJoinMgtId ──────────────────────────────
        String path5  = "/auth/insuAuth/scenListByJoinMgtId";
        RequestBody body5 = CryptoVerify.encryptRequest("{}", path5);

        JsonNode resp5 = post(BASE_URL + path5, body5);
        System.out.println("[scenListByJoinMgtId] 响应: " + resp5);

        // 注意：该接口响应未加密，直接取 data.list
        JsonNode scenList = resp5.path("data").path("list");
        for (String year : yearList) {
            // ── Step 6: getSetlList ──────────────────────────────────────
            String path6 = "/auth/insuAuth/getSetlList";
            ObjectNode raw6Node = MAPPER.createObjectNode()
                    .put("investigatedCertno",   investigatedCertno)
                    .put("investigatedCertType", investigatedCertType)
                    .put("psnName",              psnName)
                    .put("dataScpBegnTime",      dataScpBegnTime)
                    .put("dataScpEndTime",       dataScpCurTime)
                    .put("mdtrtType",            mdtrtType)
                    .put("year",                 year);
            RequestBody body6 = CryptoVerify.encryptRequest(toJson(raw6Node), path6);

            JsonNode resp6 = post(BASE_URL + path6, body6);
            System.out.println("[getSetlList] 响应(加密): " + resp6);

            JsonNode decResp6 = MAPPER.readTree(
                    CryptoVerify.decryptResponse(resp6.path("data").asText(), body6.noceStr, path6));
            // todo 响应数据判空，若为空则跳过后续步骤
            System.out.println("[getSetlList] 解密结果: " + decResp6.toPrettyString());

            JsonNode setlList = decResp6.path("list");

            // ── Step 7: saveEventTrack（第二次）──────────────────────────
            String path7  = "/eventTrack/saveEventTrack";
            String raw7   = toJson(MAPPER.createObjectNode()
                    .put("oprtBhvr", "400").put("oprtFlag", "1"));
            RequestBody body7 = CryptoVerify.encryptRequest(raw7, path7);

            JsonNode resp7 = post(BASE_URL + "/eventTrack/saveEventTrack", body7);
            System.out.println("[saveEventTrack #2] 响应: " + resp7);

            // ── Step 8: confirm ──────────────────────────────────────────
            String path8 = "/auth/insuAuth/confirm";

            // 构建 authScenDDTOList
            ArrayNode authScenList = MAPPER.createArrayNode();
            for (JsonNode scen : scenList) {
                authScenList.add(MAPPER.createObjectNode().put("scenId", scen.path("scenId").asText()));
            }

            // 构建 setlList（仅保留 setlTime + setlId）
            ArrayNode setlListReq = MAPPER.createArrayNode();
            for (JsonNode item : setlList) {
                setlListReq.add(MAPPER.createObjectNode()
                        .put("setlTime", item.path("setlTime").asText())
                        .put("setlId",   item.path("setlId").asText()));
            }

            ObjectNode authJoinDTO = MAPPER.createObjectNode()
                    .put("usedEndTime",      dataScpNextTime)
                    .put("dataScpBegnTime",  dataScpBegnTime)
                    .put("dataScpEndTime",   dataScpNextTime)
                    .put("insuAdmdvs",       insuAdmdvsCode);
            authJoinDTO.set("authScenDDTOList", authScenList);

            ObjectNode raw8Node = MAPPER.createObjectNode();
            raw8Node.set("authJoinDTO", authJoinDTO);
            raw8Node.set("setlList",    setlListReq);

            System.out.println("[confirm] 请求参数: " + raw8Node.toPrettyString());
            RequestBody body8 = CryptoVerify.encryptRequest(toJson(raw8Node), path8);

            JsonNode resp8 = post(BASE_URL + path8, body8);
            System.out.println("[confirm] 响应: " + resp8);
        }

    }

    // ----------------------------------------------------------------
    // 动态密钥提取（对应 Python extract_dynamic_key）
    // ----------------------------------------------------------------

    /**
     * 兼容明文和密文两种形式的动态密钥提取。
     * <ul>
     *   <li>明文：32 位纯十六进制字符串，直接返回</li>
     *   <li>密文：用硬编码密钥解密后 JSON 解析</li>
     * </ul>
     */
    private String extractDynamicKey(String dataField, String nonce) throws Exception {
        boolean isPlain = dataField.length() == 32
                && dataField.chars().allMatch(c -> "0123456789abcdefABCDEF".indexOf(c) >= 0);
        if (isPlain) {
            System.out.println("[动态密钥] 明文形式，直接使用: " + dataField);
            return dataField;
        } else {
            System.out.println("[动态密钥] 密文形式，解密中...");
            String raw = CryptoVerify.sm4Decrypt(dataField, CryptoVerify.HARDCODED_KEY, nonce);
            // 服务器返回的 JSON 字符串通常是 "\"<hex key>\"" 或 "<hex key>"
            JsonNode node = MAPPER.readTree(raw);
            return node.isTextual() ? node.asText() : raw;
        }
    }

    // ----------------------------------------------------------------
    // HTTP 工具
    // ----------------------------------------------------------------

    /**
     * POST 请求，携带 channel 和 authorization 头，返回解析后的 JsonNode。
     */
    private JsonNode post(String url, CryptoVerify.RequestBody encBody) throws IOException {
        okhttp3.RequestBody okhttpBody = okhttp3.RequestBody.create(encBody.toString(), JSON_MT);
        Request request = new Request.Builder()
                .url(url)
                .post(okhttpBody)
                .addHeader("channel",       CHANNEL)
                .addHeader("authorization", "Bearer " + TOKEN_STR)
                .build();
        try (Response response = HTTP.newCall(request).execute()) {
            String bodyStr = Objects.requireNonNull(response.body()).string();
            return MAPPER.readTree(bodyStr);
        }
    }

    // ----------------------------------------------------------------
    // JSON 工具
    // ----------------------------------------------------------------

    /**
     * 将 Jackson ObjectNode 序列化为紧凑 JSON 字符串（键值间无多余空格）。
     */
    private String toJson(Object obj) throws Exception {
        return MAPPER.writeValueAsString(obj);
    }
}
