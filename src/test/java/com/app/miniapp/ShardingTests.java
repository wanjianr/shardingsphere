package com.app.miniapp;

import com.app.miniapp.shardingdemo.dto.TicketJoinFile;
import com.app.miniapp.shardingdemo.entity.*;
import com.app.miniapp.shardingdemo.service.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.shardingsphere.infra.hint.HintManager;
import org.apache.shardingsphere.infra.util.json.JsonUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Date;
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
@SpringBootTest
public class ShardingTests {

    @Autowired
    private HtTicketService ticketService;

    @Autowired
    private HtPayService payService;

    @Autowired
    private HtFileService fileService;

    /**
     * 命中分片字段
     */
    @Test
    void testInsertWithShardingColumn() {
        HtTicket ticket = new HtTicket();
        ticket.setYear("2025");
        ticket.setTicketNo("T2025001");
        ticket.setCreateTime(new Date());
        // 将路由到 ht_ticket_2024 表
        ticketService.saveHtTicket(ticket);
    }

    /**
     * 未命中分片字段 使用union ALL 拼接
     */
    @Test
    void testQueryWithoutShardingColumn() {
        HtTicket ticket = new HtTicket();
//        ticket.setTicketNo("T2024001");
//        ticket.setYear("2024");
        // 会遍历所有分片表
        List<HtTicket> tickets = ticketService.list(ticket);
        System.out.println(JsonUtils.toJsonString(tickets));
    }


    /**
     * 批量更新未命中分片字段
     */
    @Test
    void testBatchUpdO() {
        HtTicket ticket = new HtTicket();
        ticket.setYear("2025");
        ticket.setTicketNo("T2025002");
        List<HtTicket> list = ticketService.list(ticket);
        System.out.println(JsonUtils.toJsonString(list));

        HtTicket htTicket = list.get(0);
        htTicket.setTicketNo("T2025003");
        ticketService.updateTicketNo(htTicket);
    }

    /**
     * 批量保存不同年度的记录
     */
    @Test
    void testBatchSaveOperations() {
        List<HtFile> files = Arrays.asList(
                createFile("2024", "F001"),
                createFile("2025", "F002")
        );
        fileService.saveBatch(files);
    }


    private HtFile createFile(String year, String fileId) {
        HtFile file = new HtFile();
//        file.setId(System.currentTimeMillis());
        file.setYear(year);
        file.setFileId(fileId);
        file.setCreateTime(new Date());
        return file;
    }


    /**
     * 带运算的表达书 或 通过Java类实现
     */
    @Test
    void testSavePayService() {
        // 注意字段类型，若不满足则需要编写对应的分片算法类PayTableShardingAlgorithm
        HtPay pay = new HtPay();
        pay.setAmount(102);
        pay.setOrderId("20240500");
        pay.setCreateTime(new Date());
        payService.save(pay);
    }


    /**
     * 多表分片绑定测试  bindingTables
     */
    @Test
    void testJoinDifferentSharding() {
        // bindingTables
        TicketJoinFile ticketJoinFile = new TicketJoinFile();
        ticketJoinFile.setYear("2024");
        List<TicketJoinFile> ticketJoinFiles = ticketService.testTicketJoinFile(ticketJoinFile);
        System.out.println(JsonUtils.toJsonString(ticketJoinFiles));
    }


    /**
     * 强制分片  HT_HINT_CLASS
     */

    @Autowired
    private HtHintService htHintService;

    @Test
    void testHint() {
        try (HintManager hintManager = HintManager.getInstance()) {
            hintManager.addTableShardingValue("ht_hint", 0);
            HtHint hint = new HtHint();
            hint.setId(System.currentTimeMillis());
            hint.setName("Hint写入测试");
            htHintService.save(hint);
        }
    }

    /**
     * 多条件分表路由配置
     */

    @Autowired
    private HtProvTransService htProvTransService;

    @Test
    void testSaveProvTrans() {
        HtProvTrans htProvTrans = new HtProvTrans();
        htProvTrans.setYear("2025");
        htProvTrans.setProv("430000");
        htProvTrans.setAmount(100.2);
        htProvTrans.setCreateTime(new Date());
        htProvTransService.save(htProvTrans);
    }

    @Test
    void testQuryProvTrans() {

        LambdaQueryWrapper<HtProvTrans> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(HtProvTrans::getYear, "2024")
                .eq(HtProvTrans::getProv, "430000");
        List<HtProvTrans> list = htProvTransService.list(wrapper);
        System.out.println(JsonUtils.toJsonString(list));
    }
}
