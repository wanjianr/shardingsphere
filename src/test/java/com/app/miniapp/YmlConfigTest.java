package com.app.miniapp;

import com.app.miniapp.ymltest.controller.YmalController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
public class YmlConfigTest {

    @Autowired
    private YmalController ymalController;

    @Test
    public void testPrintYmlConfig() {
        // 测试打印 YML 配置
        ymalController.print();
    }
}
