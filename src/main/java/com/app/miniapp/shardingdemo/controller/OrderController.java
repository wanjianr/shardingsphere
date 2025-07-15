package com.app.miniapp.shardingdemo.controller;

import com.app.miniapp.shardingdemo.entity.Orders;
import com.app.miniapp.shardingdemo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public boolean addOrder(@RequestBody Orders orders) {
        return orderService.save(orders);
    }

    @GetMapping("/{id}")
    public Orders getOrder(@PathVariable Long id) {
        return orderService.getById(id);
    }

    @PutMapping
    public boolean updateOrder(@RequestBody Orders orders) {
        return orderService.update(orders);
    }

    @DeleteMapping("/{id}")
    public boolean deleteOrder(@PathVariable Long id) {
        return orderService.deleteById(id);
    }
}
