package com.app.miniapp.shardingdemo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>PURPOSE:
 * <p>DESCRIPTION:
 * <p>CALLED BY: wanjian
 * <p>CREATE DATE: 2025/7/17
 * <p>UPDATE DATE: 2025/7/17
 * <p>UPDATE USER:
 * <p>HISTORY: 1.0
 *
 * @author wanjian
 * @version 1.0
 * @see
 * @since java 1.8
 */
@Data
@TableName("user_order")
public class UserOrder {
    @com.baomidou.mybatisplus.annotation.TableId(value = "order_id", type = com.baomidou.mybatisplus.annotation.IdType.AUTO)
    private Long orderId;      // 分片字段
    private String userName;
    private BigDecimal amount;
    private Date createTime;
}
