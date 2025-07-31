
package com.app.miniapp.feign.dto;


/**
 *
 * <p>PURPOSE:
 * <p>DESCRIPTION: 9001 请求实体类
 * <p>DESCRIPTION:
 * <p>CALLED BY:	ZhangShihua
 * <p>CREATE DATE: 2023年3月22日
 * <p>UPDATE DATE: 2023年3月22日
 * <p>UPDATE USER:
 * <p>HISTORY:		1.0
 * @version 1.0
 * @author ZhangShihua(张士华)
 * @since java 1.8.0
 * @see
 */
public class SignIn {

	/**
	 * 节点信息
	 */
	private RegisterRequest signIn;


	public SignIn(RegisterRequest signIn) {
		super();
		this.signIn = signIn;
	}


	public SignIn() {
		super();
	}

	public RegisterRequest getSignIn() {
		return signIn;
	}

	public void setSignIn(RegisterRequest signIn) {
		this.signIn = signIn;
	}


}
