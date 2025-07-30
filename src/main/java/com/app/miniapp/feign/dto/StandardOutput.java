package com.app.miniapp.feign.dto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * <p>PURPOSE: 上传执行三部规范接口输出
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class StandardOutput {

    /**
     * 交易状态码
     */
    private String infcode;

    /**
     * 接收方报文 ID 接收方返回，接收方医保区划代码(6)+时间(14)+流水号(10)时间格式：yyyyMMddHHmmss
     */
    private String inf_refmsgid;

    /**
     * 接收报文时间 格式：yyyyMMddHHmmssSSS
     */
    private String refmsg_time;

    /**
     * 响应报文时间 格式：yyyyMMddHHmmssSSS
     */
    private String respond_time;

    /**
     * 接收方系统代码
     */
    private String recer_sys_code;

    /**
     * 数字签名信息
     */
    private String cainfo;

    /**
     * 签名类型
     */
    private String signtype;

    /**
     * 错误信息
     */
    private String err_msg;

    /**
     * 提示信息
     */
    private String warn_msg;

    /**
     * 交易输出
     */
    private Object output;

    public static StandardOutput error(String msg) {
        StandardOutput o = new StandardOutput();
        o.setInfcode("A999");
        o.setErr_msg(msg);
        return o;
    }

    public String getInfcode() {
        return infcode;
    }

    public void setInfcode(String infcode) {
        this.infcode = infcode;
    }

    public String getInf_refmsgid() {
        return inf_refmsgid;
    }

    public void setInf_refmsgid(String inf_refmsgid) {
        this.inf_refmsgid = inf_refmsgid;
    }

    public String getRefmsg_time() {
        return refmsg_time;
    }

    public void setRefmsg_time(String refmsg_time) {
        this.refmsg_time = refmsg_time;
    }

    public String getRespond_time() {
        return respond_time;
    }

    public void setRespond_time(String respond_time) {
        this.respond_time = respond_time;
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

    public String getSigntype() {
        return signtype;
    }

    public void setSigntype(String signtype) {
        this.signtype = signtype;
    }

    public String getErr_msg() {
        return err_msg;
    }

    public void setErr_msg(String err_msg) {
        this.err_msg = err_msg;
    }

    public String getWarn_msg() {
        return warn_msg;
    }

    public void setWarn_msg(String warn_msg) {
        this.warn_msg = warn_msg;
    }

    public Object getOutput() {
        return output;
    }

    public void setOutput(Object output) {
        this.output = output;
    }

    @Override
    public String toString() {
        return "StandardOutput [infcode=" + infcode + ", inf_refmsgid=" + inf_refmsgid + ", refmsg_time=" + refmsg_time
                + ", respond_time=" + respond_time + ", recer_sys_code=" + recer_sys_code + ", cainfo=" + cainfo
                + ", signtype=" + signtype + ", err_msg=" + err_msg + ", warn_msg=" + warn_msg + ", output=" + output
                + "]";
    }



}
