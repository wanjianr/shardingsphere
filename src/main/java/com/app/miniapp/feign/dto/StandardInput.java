package com.app.miniapp.feign.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * <p>PURPOSE: 上传执行三部规范接口输入
 * <p>DESCRIPTION:
 * <p>CALLED BY:
 * <p>CREATE DATE: 2022-12-05
 * <p>UPDATE DATE:
 * <p>UPDATE USER:
 * <p>HISTORY:
 * @version 1.0
 * @author lt
 * @since java 1.8
 * @see
 */
public class StandardInput {

    /**
     * 交易编号
     */
    private String infno;

    /**
     * 消息方发送报文
     */
    private String msgid;

    /**
     * 就医地医保区划
     */
    private String mdtrtarea_admvs;

    /**
     * 参保地医保区划
     */
    private String insuplc_admdvs;

    /**
     * 接收方系统代码
     */
    private String recer_sys_code;

    /**
     * 设备编号
     */
    private String dev_no;

    /**
     * 设备安全信息
     */
    private String dev_safe_info;

    /**
     * 数字签名信息
     */
    private String cainfo;

    /**
     * 签名类型
     */
    private String signtype;

    /**
     * 接口版本号
     */
    private String infver;

    /**
     * 经办人类别
     */
    private String opter_type;

    /**
     * 经办人
     */
    private String opter;

    /**
     * 经办人姓名
     */
    private String opter_name;

    /**
     * 交易时间
     */
    private String inf_time;

    /**
     * 定点医疗机构编号
     */
    private String fixmedins_code;

    /**
     * 定点医疗机构编号
     */
    private String fixmedins_name;

    /**
     * 签到流水号
     */
    private String sign_no;

    /**
     * 渠道id
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String app_id;

    /**
     * 加密方式
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String enc_type;

    /**
     * 电子凭证密码核验token
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String pw_ecToken;

    /**
     * 服务商代码（动态库接口）
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String serv_code;

    /**
     * 服务商识别码（动态库接口）
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String serv_sign;

    /**
     * 交易输入
     */
    private Object input;

    public String getInfno() {
        return infno;
    }

    public void setInfno(String infno) {
        this.infno = infno;
    }

    public String getMsgid() {
        return msgid;
    }

    public void setMsgid(String msgid) {
        this.msgid = msgid;
    }

    public String getInsuplc_admdvs() {
        return insuplc_admdvs;
    }

    public void setInsuplc_admdvs(String insuplc_admdvs) {
        this.insuplc_admdvs = insuplc_admdvs;
    }

    public String getMdtrtarea_admvs() {
        return mdtrtarea_admvs;
    }

    public void setMdtrtarea_admvs(String mdtrtarea_admvs) {
        this.mdtrtarea_admvs = mdtrtarea_admvs;
    }

    public String getRecer_sys_code() {
        return recer_sys_code;
    }

    public void setRecer_sys_code(String recer_sys_code) {
        this.recer_sys_code = recer_sys_code;
    }

    public String getCainfo() {
        return cainfo;
    }

    public void setCainfo(String cainfo) {
        this.cainfo = cainfo;
    }

    public String getDev_no() {
        return dev_no;
    }

    public void setDev_no(String dev_no) {
        this.dev_no = dev_no;
    }

    public String getDev_safe_info() {
        return dev_safe_info;
    }

    public void setDev_safe_info(String dev_safe_info) {
        this.dev_safe_info = dev_safe_info;
    }

    public String getSigntype() {
        return signtype;
    }

    public void setSigntype(String signtype) {
        this.signtype = signtype;
    }

    public String getInfver() {
        return infver;
    }

    public void setInfver(String infver) {
        this.infver = infver;
    }

    public String getOpter_type() {
        return opter_type;
    }

    public void setOpter_type(String opter_type) {
        this.opter_type = opter_type;
    }

    public String getOpter() {
        return opter;
    }

    public void setOpter(String opter) {
        this.opter = opter;
    }

    public String getOpter_name() {
        return opter_name;
    }

    public void setOpter_name(String opter_name) {
        this.opter_name = opter_name;
    }

    public String getInf_time() {
        return inf_time;
    }

    public void setInf_time(String inf_time) {
        this.inf_time = inf_time;
    }

    public String getFixmedins_code() {
        return fixmedins_code;
    }

    public void setFixmedins_code(String fixmedins_code) {
        this.fixmedins_code = fixmedins_code;
    }

    public String getSign_no() {
        return sign_no;
    }

    public void setSign_no(String sign_no) {
        this.sign_no = sign_no;
    }

    public Object getInput() {
        return input;
    }

    public void setInput(Object input) {
        this.input = input;
    }

    public String getFixmedins_name() {
        return fixmedins_name;
    }

    public String getApp_id() {
        return app_id;
    }

    public String getEnc_type() {
        return enc_type;
    }

    public String getPw_ecToken() {
        return pw_ecToken;
    }

    public void setFixmedins_name(String fixmedins_name) {
        this.fixmedins_name = fixmedins_name;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public void setEnc_type(String enc_type) {
        this.enc_type = enc_type;
    }

    public void setPw_ecToken(String pw_ecToken) {
        this.pw_ecToken = pw_ecToken;
    }

    public String getServ_code() {
        return serv_code;
    }

    public String getServ_sign() {
        return serv_sign;
    }

    public void setServ_code(String serv_code) {
        this.serv_code = serv_code;
    }

    public void setServ_sign(String serv_sign) {
        this.serv_sign = serv_sign;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("StandardInput [infno=");
        builder.append(infno);
        builder.append(", msgid=");
        builder.append(msgid);
        builder.append(", mdtrtarea_admvs=");
        builder.append(mdtrtarea_admvs);
        builder.append(", insuplc_admdvs=");
        builder.append(insuplc_admdvs);
        builder.append(", recer_sys_code=");
        builder.append(recer_sys_code);
        builder.append(", dev_no=");
        builder.append(dev_no);
        builder.append(", dev_safe_info=");
        builder.append(dev_safe_info);
        builder.append(", cainfo=");
        builder.append(cainfo);
        builder.append(", signtype=");
        builder.append(signtype);
        builder.append(", infver=");
        builder.append(infver);
        builder.append(", opter_type=");
        builder.append(opter_type);
        builder.append(", opter=");
        builder.append(opter);
        builder.append(", opter_name=");
        builder.append(opter_name);
        builder.append(", inf_time=");
        builder.append(inf_time);
        builder.append(", fixmedins_code=");
        builder.append(fixmedins_code);
        builder.append(", fixmedins_name=");
        builder.append(fixmedins_name);
        builder.append(", sign_no=");
        builder.append(sign_no);
        builder.append(", app_id=");
        builder.append(app_id);
        builder.append(", enc_type=");
        builder.append(enc_type);
        builder.append(", pw_ecToken=");
        builder.append(pw_ecToken);
        builder.append(", serv_code=");
        builder.append(serv_code);
        builder.append(", serv_sign=");
        builder.append(serv_sign);
        builder.append(", input=");
        builder.append(input);
        builder.append("]");
        return builder.toString();
    }

}
