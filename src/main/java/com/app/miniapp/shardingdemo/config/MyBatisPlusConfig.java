package com.app.miniapp.shardingdemo.config;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

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

@Configuration
@MapperScan("com.app.miniapp.shardingdemo.mapper")
public class MyBatisPlusConfig {
}
