package com.app.miniapp.feign.client;

import com.app.miniapp.feign.dto.StandardInput;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URI;

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
@FeignClient(value="tfpgx3",url="Empty", contextId="tfpgx3")
public interface FeignClientC {

    /**
     * 上传接口：调用 c调用COM程序
     * @param input
     * @return
     */
    @PostMapping
    public byte[] callService(URI uri, @RequestBody(required = true) StandardInput input);

}
