package com.app.miniapp.shardingdemo.service.impl;

import com.app.miniapp.shardingdemo.entity.HtPay;
import com.app.miniapp.shardingdemo.mapper.HtPayMapper;
import com.app.miniapp.shardingdemo.service.HtPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
@Service
public class HtPayServiceImpl implements HtPayService {

    @Autowired
    private HtPayMapper htPayMapper;

    @Override
    public void save(HtPay pay) {
        htPayMapper.insert(pay);
    }
}
