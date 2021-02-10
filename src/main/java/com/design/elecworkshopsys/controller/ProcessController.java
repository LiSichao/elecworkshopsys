package com.design.elecworkshopsys.controller;

import com.design.elecworkshopsys.bean.Process;
import com.design.elecworkshopsys.bean.Spare;
import com.design.elecworkshopsys.service.ProcessService;
import com.design.elecworkshopsys.utils.PageInfo;
import com.design.elecworkshopsys.utils.ReturnDatas;
import com.github.pagehelper.PageHelper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ProcessController {

    @Resource
    private ProcessService processService;

    @GetMapping("/process")
    public ReturnDatas listPojo(PageInfo<?> pageInfo, Process process) {
        PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
        List<Process> processList = processService.listProcess(process);
        return ReturnDatas.success().data(processList).pageInfo(new PageInfo<Process>(processList));

    }

    @GetMapping("/process/{id}")
    public ReturnDatas getPojo(@PathVariable Integer id) {
        return ReturnDatas.success().data(processService.getProcessById(id));
    }

    @PostMapping("/process")
    public ReturnDatas insertPojo(Process process) {
        Integer i = processService.insertProcess(process);
        return ReturnDatas.success().message("新增成功");
    }

    @PutMapping("/process")
    public ReturnDatas updatePojo(Process process) {
        Integer i = processService.updateProcess(process);
        return ReturnDatas.success().message("更新成功");
    }

    @DeleteMapping("/process/{id}")
    public ReturnDatas deletePojo(@PathVariable Integer id) {
        Integer i = processService.deleteProcess(id);
        return ReturnDatas.success().message("删除成功");
    }

}
