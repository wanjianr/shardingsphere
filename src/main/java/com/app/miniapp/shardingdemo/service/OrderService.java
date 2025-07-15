package com.app.miniapp.shardingdemo.service;

import com.app.miniapp.shardingdemo.entity.Orders;

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
public interface OrderService {
    public boolean save(Orders orders);
    public Orders getById(Long id);
    public boolean update(Orders orders);
    public boolean deleteById(Long id);
}
