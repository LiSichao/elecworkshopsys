package com.design.elecworkshopsys.controller;

import com.design.elecworkshopsys.bean.Spare;
import com.design.elecworkshopsys.service.SpareService;
import com.design.elecworkshopsys.utils.PageInfo;
import com.design.elecworkshopsys.utils.ReturnDatas;
import com.github.pagehelper.PageHelper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api")
public class SpareController {

    @Resource
    private SpareService spareService;

    @GetMapping("/spare")
    public ReturnDatas listPojo(PageInfo<?> pageInfo, Spare spare) {
        PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
        List<Spare> spareList = spareService.listSpare(spare);
        return ReturnDatas.success().data(spareList).pageInfo(new PageInfo<Spare>(spareList));
    }

    @GetMapping("/spare/{id}")
    public ReturnDatas getPojo(@PathVariable Integer id) {
        return ReturnDatas.success().data(spareService.getSpareById(id));
    }

    @PostMapping("/spare")
    public ReturnDatas insertPojo(Spare spare) {
        Integer i = spareService.insertSpare(spare);
        return ReturnDatas.success().message("新增成功");
    }

    @PutMapping("/spare")
    public ReturnDatas updatePojo(Spare spare) {
        Integer i = spareService.updateSpare(spare);
        return ReturnDatas.success().message("更新成功");
    }

    @DeleteMapping("/spare/{id}")
    public ReturnDatas deletePojo(@PathVariable Integer id) {
        Integer i = spareService.deleteSpare(id);
        return ReturnDatas.success().message("删除成功");
    }

    @GetMapping("/spare/count")
    public ReturnDatas getPojoCount(){
        return ReturnDatas.success().data(spareService.countSpare());
    }

}
