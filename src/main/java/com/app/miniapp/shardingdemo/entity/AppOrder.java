package com.app.miniapp.shardingdemo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>PURPOSE:
 * <p>DESCRIPTION:
 * <p>CALLED BY: wanjian
 * <p>CREATE DATE: 2025/8/8
 * <p>UPDATE DATE: 2025/8/8
 * <p>UPDATE USER:
 * <p>HISTORY: 1.0
 *
 * @author wanjian
 * @version 1.0
 * @see
 * @since java 1.8
 */
@Data
//@TableName("app_order")
@TableName("app_order_amt")
public class AppOrder {
    @TableId
    private Long orderId;
    private BigDecimal amount;
    //    private LocalDateTime orderTime;
    private String orderName;
}
