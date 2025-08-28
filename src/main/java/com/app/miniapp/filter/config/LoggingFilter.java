package com.app.miniapp.filter.config;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * <p>PURPOSE:
 * <p>DESCRIPTION:
 * <p>CALLED BY: wanjian
 * <p>CREATE DATE: 2025/8/28
 * <p>UPDATE DATE: 2025/8/28
 * <p>UPDATE USER:
 * <p>HISTORY: 1.0
 *
 * @author wanjian
 * @version 1.0
 * @see
 * @since java 1.8
 */
@WebFilter(urlPatterns = "/*") // 拦截所有请求
public class LoggingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 初始化逻辑
        System.out.println("LoggingFilter initialized");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        long startTime = System.currentTimeMillis();
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();
        System.out.println("Request URI: " + requestURI);

        // 放行请求，继续执行后续 Filter 或 Servlet
        chain.doFilter(request, response);

        long endTime = System.currentTimeMillis();
        System.out.println("Request completed in " + (endTime - startTime) + " ms");
    }

    @Override
    public void destroy() {
        // 销毁逻辑
        System.out.println("LoggingFilter destroyed");
    }
}
