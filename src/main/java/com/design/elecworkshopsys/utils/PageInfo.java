package com.design.elecworkshopsys.utils;

import java.util.List;

public class PageInfo<T> extends com.github.pagehelper.PageInfo {


    public PageInfo() {
    }

    public PageInfo(List<T> list) {
        super(list);
        super.list = null;
    }

}