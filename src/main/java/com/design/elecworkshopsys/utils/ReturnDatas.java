package com.design.elecworkshopsys.utils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.github.pagehelper.PageInfo;
import java.io.Serializable;
import java.util.Map;

/**
 * 返回对象的封装
 *
 * @author Guan
 * @version 1.0.0
 */
@JsonInclude(value = Include.NON_NULL)
public class ReturnDatas implements Serializable {
    public static final String SUCCESS = "success";
    public static final String ERROR = "error";
    public static final String WARNING = "warning";
    private String statusCode = "200";
    private String status;
    private String message;
    private Object data;
    private Map<?, ?> map;
    private Object queryBean;
    private String turnTo;
    private PageInfo<?> pageInfo;

    public ReturnDatas() { }

    public ReturnDatas(String status) {
        this.status = status;
    }

    public ReturnDatas(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public ReturnDatas(String status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static ReturnDatas success() {
        return new ReturnDatas(ReturnDatas.SUCCESS);
    }

    public static ReturnDatas error() {
        return new ReturnDatas(ReturnDatas.ERROR);
    }

    public static ReturnDatas warning() {
        return new ReturnDatas(ReturnDatas.WARNING);
    }

    public String getStatus() {
        return status;
    }

    public ReturnDatas status(String status) {
        this.status = status;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ReturnDatas message(String message) {
        this.message = message;
        return this;
    }

    public Object getData() {
        return data;
    }

    public ReturnDatas data(Object obj) {
        this.data = obj;
        return this;
    }

    public Map<?, ?> getMap() {
        return map;
    }

    public ReturnDatas map(Map<?, ?> map) {
        this.map = map;
        return this;
    }

    public Object getQueryBean() {
        return queryBean;
    }

    public ReturnDatas queryBean(Object queryBean) {
        this.queryBean = queryBean;
        return this;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public ReturnDatas statusCode(String statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public String getTurnTo() {
        return turnTo;
    }

    public ReturnDatas turnTo(String turnTo) {
        this.turnTo = turnTo;
        return this;
    }

    public PageInfo<?> getPageInfo() {
        return pageInfo;
    }

    public ReturnDatas pageInfo(PageInfo<?> pageInfo) {
        this.pageInfo = pageInfo;
        return this;
    }

}
