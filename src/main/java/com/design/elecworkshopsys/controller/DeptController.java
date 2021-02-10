package com.design.elecworkshopsys.controller;

import com.design.elecworkshopsys.bean.Dept;
import com.design.elecworkshopsys.service.DeptService;
import com.design.elecworkshopsys.utils.PageInfo;
import com.design.elecworkshopsys.utils.ReturnDatas;
import com.github.pagehelper.PageHelper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api")
public class DeptController {

    @Resource
    private DeptService deptService;

    @GetMapping("/dept")
    public ReturnDatas listPojo(PageInfo<?> pageInfo, Dept dept) {
        PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
        List<Dept> deptList = deptService.listDept(dept);
        return ReturnDatas.success().data(deptList).pageInfo(new PageInfo<Dept>(deptList));
    }

    @GetMapping("/dept/{id}")
    public ReturnDatas getPojo(@PathVariable Integer id) {
        return ReturnDatas.success().data(deptService.getDeptById(id));
    }

    @PostMapping("/dept")
    public ReturnDatas insertPojo(Dept dept) {
        Integer i = deptService.insertDept(dept);
        return ReturnDatas.success().message("新增成功");
    }

    @PutMapping("/dept")
    public ReturnDatas updatePojo(Dept dept) {
        Integer i = deptService.updateDept(dept);
        return ReturnDatas.success().message("更新成功");
    }

    @DeleteMapping("/dept/{id}")
    public ReturnDatas deletePojo(@PathVariable Integer id) {
        Integer i = deptService.deleteDept(id);
        return ReturnDatas.success().message("删除成功");
    }

}
