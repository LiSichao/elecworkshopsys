package com.design.elecworkshopsys.controller;

import com.design.elecworkshopsys.bean.Station;
import com.design.elecworkshopsys.service.StationService;
import com.design.elecworkshopsys.utils.PageInfo;
import com.design.elecworkshopsys.utils.ReturnDatas;
import com.github.pagehelper.PageHelper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api")
public class StationController {

    @Resource
    private StationService stationService;

    @GetMapping("/station")
    public ReturnDatas listPojo(PageInfo<?> pageInfo, Station station) {
        PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
        List<Station> stationList = stationService.listStation(station);
        return ReturnDatas.success().data(stationList).pageInfo(new PageInfo<Station>(stationList));
    }

    @GetMapping("/station/{id}")
    public ReturnDatas getPojo(@PathVariable Integer id) {
        return ReturnDatas.success().data(stationService.getStationById(id));
    }

    @PostMapping("/station")
    public ReturnDatas insertPojo(Station station) {
        Integer i = stationService.insertStation(station);
        return ReturnDatas.success().message("新增成功");
    }

    @PutMapping("/station")
    public ReturnDatas updatePojo(Station station) {
        Integer i = stationService.updateStation(station);
        return ReturnDatas.success().message("更新成功");
    }

    @DeleteMapping("/station/{id}")
    public ReturnDatas deletePojo(@PathVariable Integer id) {
        Integer i = stationService.deleteStation(id);
        return ReturnDatas.success().message("删除成功");
    }

    @GetMapping("/station/count")
    public ReturnDatas getPojoCount(){
        return ReturnDatas.success().data(stationService.countStation());
    }

}
