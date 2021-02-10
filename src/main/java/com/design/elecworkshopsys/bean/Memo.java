package com.design.elecworkshopsys.bean;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class Memo {
    private Integer id;

    private String detail;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    private Integer userId;

    private Integer state;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail == null ? null : detail.trim();
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}