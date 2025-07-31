package com.app.miniapp.feign.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "Spring")
public interface StandardInputConvert {

    /**
     * 构造签到接口入参
     * @param standardInput
     * @return
     */
    @Mappings({
       @Mapping(target = "infno",expression = "java(\"9001\")"),
            @Mapping(target = "input", ignore = true)
    })
    StandardInput standardInputToRegisterInput(StandardInput standardInput);

    @Mappings({
            @Mapping(target = "infno", expression = "java(standardInput.getInfno() == null ? \"\" : standardInput.getInfno())"),
            @Mapping(target = "msgid", expression = "java(standardInput.getMsgid() == null ? \"\" : standardInput.getMsgid())"),
            @Mapping(target = "mdtrtarea_admvs", expression = "java(standardInput.getMdtrtarea_admvs() == null ? \"\" : standardInput.getMdtrtarea_admvs())"),
            @Mapping(target = "insuplc_admdvs", expression = "java(standardInput.getInsuplc_admdvs() == null ? \"\" : standardInput.getInsuplc_admdvs())"),
            @Mapping(target = "recer_sys_code", expression = "java(standardInput.getRecer_sys_code() == null ? \"\" : standardInput.getRecer_sys_code())"),
            @Mapping(target = "dev_no", expression = "java(standardInput.getDev_no() == null ? \"\" : standardInput.getDev_no())"),
            @Mapping(target = "dev_safe_info", expression = "java(standardInput.getDev_safe_info() == null ? \"\" : standardInput.getDev_safe_info())"),
            @Mapping(target = "cainfo", expression = "java(standardInput.getCainfo() == null ? \"\" : standardInput.getCainfo())"),
            @Mapping(target = "signtype", expression = "java(standardInput.getSigntype() == null ? \"\" : standardInput.getSigntype())"),
            @Mapping(target = "infver", expression = "java(standardInput.getInfver() == null ? \"\" : standardInput.getInfver())"),
            @Mapping(target = "opter_type", expression = "java(standardInput.getOpter_type() == null ? \"\" : standardInput.getOpter_type())"),
            @Mapping(target = "opter", expression = "java(standardInput.getOpter() == null ? \"\" : standardInput.getOpter())"),
            @Mapping(target = "opter_name", expression = "java(standardInput.getOpter_name() == null ? \"\" : standardInput.getOpter_name())"),
            @Mapping(target = "inf_time", expression = "java(standardInput.getInf_time() == null ? \"\" : standardInput.getInf_time())"),
            @Mapping(target = "fixmedins_code", expression = "java(standardInput.getFixmedins_code() == null ? \"\" : standardInput.getFixmedins_code())"),
            @Mapping(target = "fixmedins_name", expression = "java(standardInput.getFixmedins_name() == null ? \"\" : standardInput.getFixmedins_name())"),
            @Mapping(target = "sign_no", expression = "java(standardInput.getSign_no() == null ? \"\" : standardInput.getSign_no())")
    })
    StandardInput convertStandardInput(StandardInput standardInput);
}
