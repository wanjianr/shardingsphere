package com.app.miniapp.shardingdemo.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

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
@Data
@TableName("ht_ticket")
public class HtTicket {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private String year;
    private String ticketNo;
    private Date createTime;

    public HtTicket() {
    }
    public HtTicket(String year, String ticketNo, Date date) {
        this.ticketNo = ticketNo;
        this.year = year;
        this.createTime = date;
    }
}
