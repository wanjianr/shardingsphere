package com.app.miniapp;

import api.VoucherFileUtil;
import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * <p>PURPOSE:
 * <p>DESCRIPTION:
 * <p>CALLED BY: wanjian
 * <p>CREATE DATE: 2025/8/7
 * <p>UPDATE DATE: 2025/8/7
 * <p>UPDATE USER:
 * <p>HISTORY: 1.0
 *
 * @author wanjian
 * @version 1.0
 * @see
 * @since java 1.8
 */
@SpringBootTest
public class XbrlTest {

    public static void main(String[] args) throws Exception {
        // 对账单
        String filePath1 = "C:\\Users\\wanjian\\Desktop\\xbrl\\bkrs_20210831_C103031100045562XX000000010001XXXXXX.xml";
        String id1 = "bkrs";

        // 发送方回单
        String filePath2 = "C:\\Users\\wanjian\\Desktop\\xbrl\\bker_issuer_20191231_C10303110004552019030390296600243000000000019444.xml";
        String id2 = "bker_issuer";

        // 接收方回单
        String filePath3 = "C:\\Users\\wanjian\\Desktop\\xbrl\\bker_receiver_20191231_C10303110004552019030390296600243000000000019444.xml";
        String id3 = "bker_receiver";

        String content = new String(Files.readAllBytes(Paths.get(filePath1)), "UTF-8");
        JSONObject jsonObject = VoucherFileUtil.xbrl2Json(content, id1);
        System.out.println(jsonObject.toJSONString());
    }

    @Test
    public void testPrintYmlConfig() {
        String filePath = "C:\\Users\\wanjian\\Desktop\\xbrl\\银行对账单.xml";
        String id = "bkrs";
        JSONObject jsonObject = VoucherFileUtil.xbrl2Json(filePath, id);
        System.out.println(jsonObject.toJSONString());
    }
}
