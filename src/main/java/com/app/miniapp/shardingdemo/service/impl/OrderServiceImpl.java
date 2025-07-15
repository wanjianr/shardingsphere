package com.app.miniapp.shardingdemo.service.impl;

import com.app.miniapp.shardingdemo.entity.Orders;
import com.app.miniapp.shardingdemo.mapper.OrderMapper;
import com.app.miniapp.shardingdemo.service.OrderService;
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
public class OrderServiceImpl implements OrderService {


    @Autowired
    private OrderMapper orderMapper;

    public boolean save(Orders orders) {
        return orderMapper.insert(orders) > 0;
    }

    public Orders getById(Long id) {
        return orderMapper.selectById(id);
    }

    public boolean update(Orders orders) {
        return orderMapper.updateById(orders) > 0;
    }

    public boolean deleteById(Long id) {
        return orderMapper.deleteById(id) > 0;
    }
}
