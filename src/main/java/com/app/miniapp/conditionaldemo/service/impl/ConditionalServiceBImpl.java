package com.app.miniapp.conditionaldemo.service.impl;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * <p>PURPOSE:
 * <p>DESCRIPTION:
 * <p>CALLED BY: wanjian
 * <p>CREATE DATE: 2025/7/18
 * <p>UPDATE DATE: 2025/7/18
 * <p>UPDATE USER:
 * <p>HISTORY: 1.0
 *
 * @author wanjian
 * @version 1.0
 * @see
 * @since java 1.8
 */
@Service
@ConditionalOnProperty(prefix="pcc",value="sign",havingValue="B")
public class ConditionalServiceBImpl implements com.app.miniapp.conditionaldemo.service.ConditionalService {

    @Override
    public void printConditionalMessage() {
        System.out.println("This is ConditionalServiceBImpl");
    }
}
