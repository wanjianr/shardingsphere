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
// 当 pcc.sign 属性在配置文件中不存在（即为空或未定义）时，Spring 会认为条件匹配，从而初始化该 bean。
@ConditionalOnProperty(prefix = "pcc", value = "sign", havingValue = "A", matchIfMissing = true)
public class ConditionalServiceAImpl implements com.app.miniapp.conditionaldemo.service.ConditionalService {

    @Override
    public void printConditionalMessage() {
        System.out.println("This is ConditionalServiceAImpl");
    }
}
