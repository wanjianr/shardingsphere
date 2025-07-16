package com.app.miniapp.shardingdemo.service.impl;

import com.app.miniapp.shardingdemo.dto.TicketJoinFile;
import com.app.miniapp.shardingdemo.entity.HtTicket;
import com.app.miniapp.shardingdemo.mapper.HtTicketMapper;
import com.app.miniapp.shardingdemo.service.HtTicketService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
@Service
public class HtTicketServiceImpl implements HtTicketService {

    @Autowired
    private HtTicketMapper htTicketMapper;

    public void save(HtTicket ticket) {
        htTicketMapper.insert(ticket);
    }

    public List<HtTicket> list(HtTicket htTicket) {
        LambdaQueryWrapper<HtTicket> wrapper = new LambdaQueryWrapper<>();
        if (htTicket.getTicketNo() != null) {
            wrapper.eq(HtTicket::getTicketNo, htTicket.getTicketNo());
        }
        if (htTicket.getYear() != null) {
            wrapper.eq(HtTicket::getYear, htTicket.getYear());
        }
        return htTicketMapper.selectList(wrapper);
    }

    @Override
    public List<TicketJoinFile> testTicketJoinFile(TicketJoinFile ticketJoinFile) {
        return htTicketMapper.selectJoinFileList(ticketJoinFile);
    }
}
