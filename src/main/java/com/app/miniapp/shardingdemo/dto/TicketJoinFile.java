package com.app.miniapp.shardingdemo.dto;

import com.app.miniapp.shardingdemo.entity.HtTicket;
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
public class TicketJoinFile extends HtTicket {
    private String year;
    private String fileId;
}
