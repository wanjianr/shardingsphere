package com.app.miniapp;

import com.app.miniapp.redis.service.INoRuleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * <p>PURPOSE:
 * <p>DESCRIPTION:
 * <p>CALLED BY: wanjian
 * <p>CREATE DATE: 2025/8/22
 * <p>UPDATE DATE: 2025/8/22
 * <p>UPDATE USER:
 * <p>HISTORY: 1.0
 *
 * @author wanjian
 * @version 1.0
 * @see
 * @since java 1.8
 */
@SpringBootTest
public class INoRuleTest {

    @Autowired
    private INoRuleService noRuleService;

    @Test
    public void testGenerateSerialNumber() {
        String key = "bsw:norule:batchno";
        int length = 5;

        String serialNumber = noRuleService.produceSerialNumber(key, length);
        System.out.println("Generated Serial Number: " + serialNumber);
    }

}
