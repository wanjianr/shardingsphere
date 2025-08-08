package com.app.miniapp.ymltest.controller;


import org.springframework.beans.factory.annotation.Value;
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
@RequestMapping("/yml")
public class YmalController {

    @Value("${ctj.isquerycheckcode:false}")
    private boolean isQueryCheckcode;

    @GetMapping("/print")
    public void print() {
        System.out.println("isQueryCheckcode: " + isQueryCheckcode);
    }


}
