package com.app.miniapp;

import com.app.miniapp.feign.dto.EbcInput;
import com.app.miniapp.feign.dto.StandardInput;
import com.app.miniapp.feign.dto.StandardOutput;
import com.app.miniapp.feign.service.FeignService;
import org.apache.shardingsphere.infra.util.json.JsonUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * <p>PURPOSE:
 * <p>DESCRIPTION:
 * <p>CALLED BY: wanjian
 * <p>CREATE DATE: 2025/7/30
 * <p>UPDATE DATE: 2025/7/30
 * <p>UPDATE USER:
 * <p>HISTORY: 1.0
 *
 * @author wanjian
 * @version 1.0
 * @see
 * @since java 1.8
 */
@SpringBootTest
public class FeignTest {

    @Autowired
    private FeignService feignService;

    @Test
    void testFeignService() {
        // 测试调用 FeignService 的方法
        StandardOutput standardOutput = feignService.callServiceA(buildInput());// 传入 null 仅为示例，实际使用时应传入有效的 StandardInput 对象
        System.out.println(JsonUtils.toJsonString(standardOutput));
    }

    @Test
    void testSign() throws Exception {
        feignService.signIn(buildInput());
    }

    public StandardInput buildInput() {
        StandardInput standardInput = new StandardInput();

        standardInput.setInfno("4902");
        standardInput.setMsgid("H45060300003202507291314400204");
        standardInput.setMdtrtarea_admvs("450600");
        standardInput.setInsuplc_admdvs("450699");
        standardInput.setRecer_sys_code("fsi");
        standardInput.setDev_no(null);
        standardInput.setDev_safe_info(null);
        standardInput.setCainfo("");
        standardInput.setSigntype(null);
        standardInput.setInfver("V1.0");
        standardInput.setOpter_type("1");
        standardInput.setOpter("测试");
        standardInput.setOpter_name("测试");
        standardInput.setInf_time("2025-07-29 13:14:40");
        standardInput.setFixmedins_code("H45060300003");
        standardInput.setFixmedins_name("防城港市中医医院");
        standardInput.setSign_no("123");

        // 创建并填充 Input 实例
        EbcInput input = new EbcInput();
        input.setFixmedinsCode("H45060300003");
        input.setFixmedinsName("防城港市中医医院");
        input.setBizStsb("20257520-20250730");
        input.setUpldBchno("2025073000001");

        // 将 Input 实例设置为 standardInput 的 input 字段
        standardInput.setInput(input);
        return standardInput;
    }
}
