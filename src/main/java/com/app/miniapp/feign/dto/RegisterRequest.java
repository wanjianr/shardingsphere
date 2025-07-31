
package com.app.miniapp.feign.dto;

/**
 *
 * <p>PURPOSE:
 * <p>DESCRIPTION: 签到请求参数
 * <p>DESCRIPTION:
 * <p>CALLED BY:	ZhangShihua
 * <p>CREATE DATE: 2023年3月20日
 * <p>UPDATE DATE: 2023年3月20日
 * <p>UPDATE USER:
 * <p>HISTORY:		1.0
 * @version 1.0
 * @author ZhangShihua(张士华)
 * @since java 1.8.0
 * @see
 */
public class RegisterRequest {

	/**
	 * 操作员编号
	 */
	private String opter_no;

	/**
	 * 签到MAC地址
	 */
	private String mac;

	/**
	 * 签到IP地址
	 */
	private String ip;

	public RegisterRequest(String mac, String ip) {
		super();
		this.mac = mac;
		this.ip = ip;
	}

	public RegisterRequest() {
		super();
	}

	public String getOpter_no() {
		return opter_no;
	}

	public String getMac() {
		return mac;
	}

	public String getIp() {
		return ip;
	}

	public void setOpter_no(String opter_no) {
		this.opter_no = opter_no;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

}
