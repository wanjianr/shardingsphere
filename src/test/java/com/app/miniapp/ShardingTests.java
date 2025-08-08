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

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
        ticket.setYear("2026");
        ticket.setTicketNo("T2026001");
        ticket.setCreateTime(new Date());
        // 将路由到 ht_ticket_2024 表
        ticketService.saveHtTicket(ticket);
    }

    @Test
    public void testTransaction() {
        HtTicket ticket = new HtTicket();
        ticket.setYear("2026");
        ticket.setTicketNo("T2026001");
        ticket.setCreateTime(new Date());
        // 测试事务
        ticketService.testTransaction(ticket);
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
     * 更新未命中分片字段
     */
    @Test
    void testBatchUpdO() {
        HtTicket ticket = new HtTicket();
//        ticket.setYear("2025");
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
        pay.setOrderId("20240501");
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
        ticketJoinFile.setYear("2025");
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
            hintManager.addTableShardingValue("ht_hint", 1);
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
        htProvTrans.setYear("2027");
        htProvTrans.setProv("660000");
        htProvTrans.setAmount(100.2);
        htProvTrans.setCreateTime(new Date());
        htProvTransService.save(htProvTrans);
    }

    @Test
    void testQuryProvTrans() {

        LambdaQueryWrapper<HtProvTrans> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(HtProvTrans::getYear, "2024")
                .eq(HtProvTrans::getProv, "660000");
        List<HtProvTrans> list = htProvTransService.list(wrapper);
        System.out.println(JsonUtils.toJsonString(list));
    }


    /**
     * 按照范围分表
     */
    @Autowired
    private UserOrderService userOrderService;

    @Test
    void testUserOrder() {
        for (int i = 25; i < 26; i++) {
            UserOrder userOrder = new UserOrder();
            userOrder.setOrderId((long) (i));
            userOrder.setAmount(new BigDecimal(100 + i));
            userOrder.setCreateTime(new Date());
            userOrderService.save(userOrder);
        }

        // 查询
//        LambdaQueryWrapper<UserOrder> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(UserOrder::getUserId, 1001L);
//        List<UserOrder> orders = userOrderService.list(wrapper);
//        System.out.println(JsonUtils.toJsonString(orders));
    }

    @Autowired
    private IdGenService idGenService;
    @Test
    void testIdGen() {
        IdGen idGen = new IdGen();
        idGen.setYear("2024");
        idGen.setCreateTime(new Date());
        idGenService.save(idGen);
    }

    @Autowired
    private AppOrderService appOrderService;

    @Test
    void appOrderService() {
//        AppOrder appOrder = new AppOrder();
//        appOrder.setOrderId(1001L);
//        appOrder.setOrderName("测试订单");
//        appOrder.setOrderTime(LocalDateTime.now());
//        appOrderService.save(appOrder);

        // 查询
        LambdaQueryWrapper<AppOrder> wrapper = new LambdaQueryWrapper<>();
//        wrapper.lt(AppOrder::getOrderTime, LocalDateTime.now());
        List<AppOrder> orders = appOrderService.list(wrapper);
        System.out.println(JsonUtils.toJsonString(orders));
    }

    @Test
    void testDelete() {
        AppOrder appOrder = new AppOrder();
//        appOrder.setOrderId(1001L);
        appOrder.setOrderName("测试订单");
        appOrder.setAmount(new BigDecimal("1930.23"));
        appOrderService.save(appOrder);

    }
}
