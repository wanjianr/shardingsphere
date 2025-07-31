package com.app.miniapp.feign.dto;


import org.springframework.util.StringUtils;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * <p>PURPOSE:
 * <p>DESCRIPTION: 获取MAC地址和IP地址
 * <p>CALLED BY:
 * <p>CREATE DATE: 2023/6/5 19:06 星期一
 * <p>UPDATE DATE:
 * <p>UPDATE USER:
 * <p>HISTORY:
 *
 * @author xqz
 * @version 1.0
 * @see
 * @since java 1.8
 */
public class MacUtil {
    /**
     * 获取MAC地址和IP地址
     * @return
     */
    public static RegisterRequest macAndIp() throws Exception {
        String mac = null;
        String ip = null;
        try {
            Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            byte[] macAddress = null;
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = allNetInterfaces.nextElement();
                if (netInterface.isLoopback() || netInterface.isVirtual() || netInterface.isPointToPoint()
                        || !netInterface.isUp()) {
                    continue;
                } else {
                    macAddress = netInterface.getHardwareAddress();
                    Enumeration<InetAddress> allInetAddress = netInterface.getInetAddresses();
                    if (macAddress != null) {
                        StringBuilder sb = new StringBuilder(17);
                        for (int i = 0; i < macAddress.length; i++) {
                            sb.append(String.format("%02X%s", macAddress[i], (i < macAddress.length - 1) ? "" : ""));
                        }
                        mac = sb.toString();
                    }
                    InetAddress ipAddress = null;
                    while (allInetAddress.hasMoreElements()) {
                        ipAddress = allInetAddress.nextElement();
                        if (ipAddress != null && ipAddress instanceof Inet4Address) {
                            ip = ipAddress.getHostAddress();
                        }
                    }
                    if(!StringUtils.isEmpty(mac) && !StringUtils.isEmpty(ip)) {
                        break;
                    }
                }
            }
        } catch (SocketException e) {
            throw new Exception("获取机器mac地址和IP地址出错，请查看", e);
        }

        return new RegisterRequest(mac, ip);
    }
}
