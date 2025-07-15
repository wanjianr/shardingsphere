package com.app.miniapp;

import com.app.miniapp.shardingdemo.entity.Orders;
import com.app.miniapp.shardingdemo.service.OrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MiniappApplicationTests {

    @Autowired
    private OrderService orderService;

    @Test
    public void testCRUD() {
        Orders orders = new Orders();
        orders.setId(System.currentTimeMillis());
        orders.setUserId(123L);
        orders.setOrderNo("1");
        orders.setAmount(99.99);

        // Save
        Assertions.assertTrue(orderService.save(orders));

        // Get
        Orders dbOrders = orderService.getById(orders.getId());
        Assertions.assertNotNull(dbOrders);

        // Update
        dbOrders.setAmount(199.99);
        Assertions.assertTrue(orderService.update(dbOrders));

        // Delete
//        Assertions.assertTrue(orderService.deleteById(orders.getId()));
    }
}
