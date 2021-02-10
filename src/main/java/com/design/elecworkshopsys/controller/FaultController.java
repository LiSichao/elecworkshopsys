package com.design.elecworkshopsys.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import cn.hutool.poi.excel.StyleSet;
import com.design.elecworkshopsys.bean.Fault;
import com.design.elecworkshopsys.service.FaultService;
import com.design.elecworkshopsys.utils.PageInfo;
import com.design.elecworkshopsys.utils.ReturnDatas;
import com.github.pagehelper.PageHelper;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class FaultController {

    @Resource
    private FaultService faultService;

    @GetMapping("/fault")
    public ReturnDatas listPojo(PageInfo<?> pageInfo, Fault fault) {
        PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
        List<Fault> faultList = faultService.listFault(fault);
        return ReturnDatas.success().data(faultList).pageInfo(new PageInfo<Fault>(faultList));
    }

    @GetMapping("/fault/{id}")
    public ReturnDatas getPojo(@PathVariable Integer id) {
        return ReturnDatas.success().data(faultService.getFaultById(id));
    }

    @PostMapping("/fault")
    public ReturnDatas insertPojo(Fault fault) {
        Integer i = faultService.insertFault(fault);
        return ReturnDatas.success().message("新增成功");
    }

    @PutMapping("/fault")
    public ReturnDatas updatePojo(Fault fault) {
        Integer i = faultService.updateFault(fault);
        return ReturnDatas.success().message("更新成功");
    }

    @DeleteMapping("/fault/{id}")
    public ReturnDatas deletePojo(@PathVariable Integer id) {
        Integer i = faultService.deleteFault(id);
        return ReturnDatas.success().message("删除成功");
    }

    @GetMapping("/fault/chart")
    public ReturnDatas getPojoChart() {
        return ReturnDatas.success().data(faultService.getFaultChart());
    }

    @GetMapping("/fault/count")
    public ReturnDatas getPojoCount() {
        return ReturnDatas.success().data(faultService.countFault());
    }

    @GetMapping("/fault/export")
    public void getPojoExport(Fault fault, HttpServletResponse response) throws IOException {
        faultService.exportFaultExcel(fault, response);
    }

}
