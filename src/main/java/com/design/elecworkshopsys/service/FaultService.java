package com.design.elecworkshopsys.service;

import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import cn.hutool.poi.excel.StyleSet;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.design.elecworkshopsys.bean.Dept;
import com.design.elecworkshopsys.bean.Fault;
import com.design.elecworkshopsys.bean.Process;
import com.design.elecworkshopsys.bean.Station;
import com.design.elecworkshopsys.dao.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FaultService {
    @Resource
    private FaultMapper faultMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private StationMapper stationMapper;
    @Resource
    private DeptMapper deptMapper;
    @Resource
    private ProcessMapper processMapper;

    public List<Fault> listFault(Fault fault) {
        List<Integer> station = StringUtils.isBlank(fault.getPlace()) ? new ArrayList<>() : stationMapper
                .selectList(Wrappers.<Station>lambdaQuery().like(Station::getName, fault.getPlace()))
                .stream().map(Station::getId).collect(Collectors.toList());
        List<Integer> dept = StringUtils.isBlank(fault.getPlace()) ? new ArrayList<>() : deptMapper
                .selectList(Wrappers.<Dept>lambdaQuery().like(Dept::getName, fault.getPlace()))
                .stream().map(Dept::getId).collect(Collectors.toList());
        if (StringUtils.isNoneBlank(fault.getPlace()) && station.isEmpty() && dept.isEmpty()) {
            return new ArrayList<>();
        }
        List<Fault> faultList = faultMapper.selectList(Wrappers.<Fault>lambdaQuery()
                .eq(fault.getState() != null, Fault::getState, fault.getState())
                .eq(fault.getUserId() != null, Fault::getUserId, fault.getUserId())
                .eq(fault.getDeptId() != null, Fault::getDeptId, fault.getDeptId())
                .eq(fault.getUserDeptId() != null, Fault::getUserDeptId, fault.getUserDeptId())
                .ge(fault.getDateStart() != null, Fault::getDatetime, fault.getDateStart() != null ? LocalDateTime.of(fault.getDateStart(), LocalTime.MIN) : null)
                .le(fault.getDateEnd() != null, Fault::getDatetime, fault.getDateEnd() != null ? LocalDateTime.of(fault.getDateEnd(), LocalTime.MAX) : null)
                .and(!station.isEmpty() || !dept.isEmpty(),
                        i -> i.in(!station.isEmpty(), Fault::getStationId, station).or().in(!dept.isEmpty(), Fault::getDeptId, dept))
                .ne(Fault::getState, 0)
                .like(StringUtils.isNoneBlank(fault.getDetail()), Fault::getDetail, fault.getDetail())
                .orderByDesc(Fault::getDatetime)
        );
        for (Fault faultItem : faultList) {
            faultItem.setUser(userMapper.selectById(faultItem.getUserId()));
            faultItem.setStation(stationMapper.selectById(faultItem.getStationId()));
            faultItem.setDept(deptMapper.selectById(faultItem.getDeptId()));
        }
        return faultList;
    }

    public Fault getFaultById(Integer id) {
        return faultMapper.selectById(id);
    }

    public int insertFault(Fault fault) {
        LocalDateTime dateTime = fault.getDatetime();
        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyyMMdd");
        String code = "" + dateTimeFormat.format(dateTime);
        LocalDateTime start = LocalDateTime.of(dateTime.toLocalDate(), LocalTime.MIN);
        LocalDateTime end = LocalDateTime.of(dateTime.toLocalDate(), LocalTime.MAX);
        long count = faultMapper.selectCount(Wrappers.<Fault>lambdaQuery()
                .ge(Fault::getDatetime, start)
                .lt(Fault::getDatetime, end)
        );
        count++;
        code = count > 9 ? code + count : code + "0" + count;
        fault.setCode(Integer.parseInt(code));
        return faultMapper.insert(fault);
    }

    public int updateFault(Fault fault) {
        return faultMapper.updateById(fault);
    }

    public int deleteFault(Integer id) {
        return faultMapper.deleteById(id);
    }

    public List<Map<String, Object>> getFaultChart() {
        QueryWrapper<Fault> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("COUNT(*) AS '故障总数'", "COUNT(state=1 OR NULL) AS '待处理'",
                "COUNT(state=2 OR NULL) AS '处理中'", "COUNT(state=3 OR NULL) AS '已处理'",
                "DATE_FORMAT(datetime,'%Y-%m-%d') AS date")
                .ne("state", 0)
                .ge("datetime", LocalDateTime.of(LocalDateTime.now().toLocalDate(), LocalTime.MIN).minusDays(6))
                .groupBy("DATE_FORMAT(datetime,'%Y-%m-%d')")
                .orderByAsc("DATE_FORMAT(datetime,'%Y-%m-%d')");

        List<Map<String, Object>> mapList = faultMapper.selectMaps(queryWrapper);
        return mapList;
    }

    public int countFault() {
        return faultMapper.selectCount(Wrappers.<Fault>lambdaQuery().ne(Fault::getState, 0));
    }

    public void exportFaultExcel(Fault fault, HttpServletResponse response) throws IOException {
        List<Fault> faultList = this.listFault(fault);
        List<Map<String, Object>> rows = new ArrayList<>();
        for (Fault faultItem : faultList) {
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("提报日期", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(faultItem.getDatetime()));
            String state = "";
            switch (faultItem.getState()) {
                case 1:
                    state = "待处理";
                    break;
                case 2:
                    state = "处理中";
                    break;
                case 3:
                    state = "已处理";
            }
            row.put("状态", state);
            row.put("故障现象", faultItem.getDetail());
            row.put("故障地点", faultItem.getStation() != null ? faultItem.getStation().getName() : faultItem.getDept().getName());
            List<Process> processList = processMapper.selectList(Wrappers.<Process>lambdaQuery()
                    .ne(Process::getState, 0)
                    .eq(Process::getFaultId, faultItem.getId())
            );
            row.put("处理进度", processList.isEmpty() ? "" : processList.get(processList.size() - 1).getDetail());
            rows.add(row);
        }
        ExcelWriter writer = ExcelUtil.getWriter(true);
        // 设置排版
        StyleSet styleSet = writer.getStyleSet();
        styleSet.setAlign(HorizontalAlignment.LEFT, VerticalAlignment.CENTER);
        styleSet.setWrapText();
        styleSet.getHeadCellStyle().setAlignment(HorizontalAlignment.CENTER);
        Font font = writer.createFont();
        font.setFontHeightInPoints((short) 12);
        styleSet.getHeadCellStyle().setFont(font);
        // 横向打印
        writer.getSheet().getPrintSetup().setLandscape(true);
        // 页面水平居中
        writer.getSheet().setHorizontallyCenter(true);
        writer.merge(4, "故障信息汇总表");
        writer.setColumnWidth(0, 16);
        writer.setColumnWidth(1, 8);
        writer.setColumnWidth(2, 40);
        writer.setColumnWidth(3, 10);
        writer.setColumnWidth(4, 40);
        writer.write(rows, true);

        // 输出文件流
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=test.xlsx");
        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        writer.close();
        IoUtil.close(out);
    }

}
