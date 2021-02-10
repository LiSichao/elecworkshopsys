package com.design.elecworkshopsys.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.design.elecworkshopsys.bean.Fault;
import com.design.elecworkshopsys.bean.Station;
import com.design.elecworkshopsys.dao.DeptMapper;
import com.design.elecworkshopsys.dao.StationMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class StationService {
    @Resource
    private StationMapper stationMapper;
    @Resource
    private DeptMapper deptMapper;

    public List<Station> listStation(Station station) {
        List<Station> stationList = stationMapper.selectList(Wrappers.<Station>lambdaQuery()
                .eq(station.getState() != null, Station::getState, station.getState())
                .eq(station.getDeptId() != null, Station::getDeptId, station.getDeptId())
        );
        for (Station stationItem : stationList) {
            stationItem.setDept(deptMapper.selectById(stationItem.getDeptId()));
        }
        return stationList;
    }

    public Station getStationById(Integer id) {
        return stationMapper.selectById(id);
    }

    public int insertStation(Station station) {
        return stationMapper.insert(station);
    }

    public int updateStation(Station station) {
        return stationMapper.updateById(station);
    }

    public int deleteStation(Integer id) {
        return stationMapper.deleteById(id);
    }

    public int countStation() {
        return stationMapper.selectCount(Wrappers.<Station>lambdaQuery().ne(Station::getState, 0));
    }
}
