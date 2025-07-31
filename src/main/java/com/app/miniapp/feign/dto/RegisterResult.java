
package com.app.miniapp.feign.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 *
 * <p>PURPOSE:
 * <p>DESCRIPTION: 签到结果
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegisterResult {

	/**
	 * 签到时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date sign_time;

	/**
	 * 签到编号
	 */
	private String sign_no;

	public Date getSign_time() {
		return sign_time;
	}

	public String getSign_no() {
		return sign_no;
	}

	public void setSign_time(Date sign_time) {
		this.sign_time = sign_time;
	}

	public void setSign_no(String sign_no) {
		this.sign_no = sign_no;
	}

}
