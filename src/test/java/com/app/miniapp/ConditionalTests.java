package com.app.miniapp;

import com.app.miniapp.conditionaldemo.service.ConditionalService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * <p>PURPOSE:
 * <p>DESCRIPTION:
 * <p>CALLED BY: wanjian
 * <p>CREATE DATE: 2025/7/15
 * <p>UPDATE DATE: 2025/7/15
 * <p>UPDATE USER:
 * <p>HISTORY: 1.0
 *
 * @author wanjian
 * @version 1.0
 * @see
 * @since java 1.8
 */
@SpringBootTest
public class ConditionalTests {

    @Autowired
    private ConditionalService conditionalService;

    @Test
    void testConditionalService() {
        // 根据配置条件加载不同的实现类
        conditionalService.printConditionalMessage();
    }

}
