package com.app.miniapp.redis.controller;

import com.app.miniapp.redis.service.INoRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>PURPOSE:
 * <p>DESCRIPTION:
 * <p>CALLED BY: wanjian
 * <p>CREATE DATE: 2025/8/22
 * <p>UPDATE DATE: 2025/8/22
 * <p>UPDATE USER:
 * <p>HISTORY: 1.0
 *
 * @author wanjian
 * @version 1.0
 * @see
 * @since java 1.8
 */
@RestController
@RequestMapping("/rule")
public class NoRuleController {

    @Autowired
    private INoRuleService noRuleService;

    @GetMapping("/date")
    public String printYmlPath(@RequestParam("date") String date,
                             @RequestParam("key") String key) throws Exception {
        return noRuleService.getBatchNo(key, date);
    }
}
