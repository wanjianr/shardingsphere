package com.app.miniapp.shardingdemo.controller;

import com.app.miniapp.shardingdemo.entity.Orders;
import com.app.miniapp.shardingdemo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.Properties;

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

    @GetMapping("/printPath")
    public void printYmlPath() throws Exception {
        // 输出user.dir路径
        String userDir = System.getProperty("user.dir");
        String filePath = userDir + File.separator + "application.yml";
        String decode = URLDecoder.decode(filePath, "utf-8");
        System.out.println(decode);

        File file = new File(filePath);
        if (file.exists()) {

            Properties configFileProp = new Properties();
            FileInputStream fileInputStream = new FileInputStream(file);

            configFileProp.load((InputStream) fileInputStream);
            System.out.println("Properties from file: " + configFileProp.values());
        }
    }

}
