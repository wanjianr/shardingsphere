package com.app.miniapp;

import com.app.miniapp.shardingdemo.dto.TicketJoinFile;
import com.app.miniapp.shardingdemo.entity.HtFile;
import com.app.miniapp.shardingdemo.entity.HtPay;
import com.app.miniapp.shardingdemo.entity.HtTicket;
import com.app.miniapp.shardingdemo.service.HtFileService;
import com.app.miniapp.shardingdemo.service.HtPayService;
import com.app.miniapp.shardingdemo.service.HtTicketService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.ibatis.logging.stdout.StdOutImpl;
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

    // 单表测试 - 命中分片字段
    @Test
    void testInsertWithShardingColumn() {
        HtTicket ticket = new HtTicket();
        ticket.setYear("2025");
        ticket.setTicketNo("T2025001");
        ticket.setCreateTime(new Date());
        // 将路由到 ht_ticket_2024 表
        ticketService.save(ticket);
    }

    @Test
    void testSavePayService() {
        // 注意字段类型，若不满足则需要编写对应的分片算法类PayTableShardingAlgorithm
        HtPay pay = new HtPay();
        pay.setAmount(102.0);
        pay.setOrderId("20240502");
        pay.setCreateTime(new Date());
        payService.save(pay);
    }

    // 单表测试 - 未命中分片字段
    @Test
    void testQueryWithoutShardingColumn() {
        HtTicket ticket = new HtTicket();
//        ticket.setTicketNo("T2024001");
//        ticket.setYear("2024");
        // 会遍历所有分片表
        List<HtTicket> tickets = ticketService.list(ticket);
    }

    // 测试不同分片策略的表关联
    @Test
    void testJoinDifferentSharding() {
        // bindingTables
        TicketJoinFile ticketJoinFile = new TicketJoinFile();
        ticketJoinFile.setYear("2024");
        List<TicketJoinFile> ticketJoinFiles = ticketService.testTicketJoinFile(ticketJoinFile);
        System.out.println(JsonUtils.toJsonString(ticketJoinFiles));
    }

    @Test
    void testBatchOperations() {
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


    public static void main(String[] args) {
        String url = "http://172.16.22.192:34104/realware/pccOrder";
        System.out.println(url.replace("/pccOrder", "/dlr/orgAccessConfig"));
    }
}
