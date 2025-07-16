package com.app.miniapp.shardingdemo.mapper;

import com.app.miniapp.shardingdemo.dto.TicketJoinFile;
import com.app.miniapp.shardingdemo.entity.HtTicket;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

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
@Mapper
public interface HtTicketMapper extends BaseMapper<HtTicket> {
    List<TicketJoinFile> selectJoinFileList(TicketJoinFile ticketJoinFile);
}
