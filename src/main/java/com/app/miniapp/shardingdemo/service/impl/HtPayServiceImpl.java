package com.app.miniapp.shardingdemo.service.impl;

import com.app.miniapp.shardingdemo.entity.HtPay;
import com.app.miniapp.shardingdemo.mapper.HtPayMapper;
import com.app.miniapp.shardingdemo.service.HtPayService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<HtPay> query(List<String> orderIds) {
        LambdaQueryWrapper<HtPay> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(HtPay::getOrderId, orderIds);
        return htPayMapper.selectList(queryWrapper);
    }

    @Override
    public HtPay queryOne(String orderId) {
        LambdaQueryWrapper<HtPay> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ge(HtPay::getOrderId, orderId);
        return htPayMapper.selectOne(queryWrapper);
    }


}
