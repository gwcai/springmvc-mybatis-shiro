package com.spring.base.system;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

@Data
public class ResponseData implements Serializable, Cloneable {
    private static final int OK_CODE = 0;
    private static final int FAIL_CODE = 1;
    private static final int EMPTY_CODE = 2;
    private static final int VERIFICATION_FAIL_CODE = 3;

    @JSONField(
            ordinal = 1,
            name = "code"
    )
    private int code;
    @JSONField(
            ordinal = 2,
            name = "msg"
    )
    private String msg;
    @JSONField(
            ordinal = 3,
            name = "detail"
    )
    private String detail;
    @JSONField(
            ordinal = 4,
            name = "jumpUrl"
    )
    private String jumpUrl;
    @JSONField(
            ordinal = 5,
            name = "data"
    )
    private Object data;
    @JSONField(
            ordinal = 6,
            name = "html"
    )
    private String html;

    private ResponseData(int code) {
        this.code = code;
    }

    public static ResponseData ok() {
        return new ResponseData(OK_CODE);
    }

    public static ResponseData fail() {
        return new ResponseData(FAIL_CODE);
    }

    public static ResponseData empty() {
        return new ResponseData(EMPTY_CODE);
    }

    public static ResponseData verificationFail(BindingResult bindingResult) {
        ResponseData dataResponse = new ResponseData(VERIFICATION_FAIL_CODE);
        dataResponse.setMsg("表单不正确");
        dataResponse.setDetail(((ObjectError)bindingResult.getAllErrors().get(0)).getDefaultMessage());
        return dataResponse;
    }

    public ResponseData msg(String msg) {
        this.msg = msg;
        return this;
    }

    public ResponseData detail(String detail) {
        this.detail = detail;
        return this;
    }

    public ResponseData jumpUrl(String jumpUrl) {
        this.jumpUrl = jumpUrl;
        return this;
    }

    public ResponseData data(Object data) {
        this.data = data;
        return this;
    }

    public ResponseData error(Exception e) {
        this.msg = "操作失败";
        this.detail = e.getMessage();
        return this;
    }
}