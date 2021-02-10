package com.design.elecworkshopsys.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.design.elecworkshopsys.bean.Fault;
import com.design.elecworkshopsys.bean.Spare;
import com.design.elecworkshopsys.dao.SpareMapper;
import com.design.elecworkshopsys.dao.StationMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SpareService {
    @Resource
    private SpareMapper spareMapper;
    @Resource
    private StationMapper stationMapper;

    public List<Spare> listSpare(Spare spare) {
        List<Spare> spareList = spareMapper.selectList(Wrappers.<Spare>lambdaQuery()
                .eq(spare.getState() != null, Spare::getState, spare.getState())
                .ne(Spare::getState, 0)
        );
        for (Spare spareItem : spareList) {
            spareItem.setStation(stationMapper.selectById(spareItem.getStationId()));
        }
        return spareList;
    }

    public Spare getSpareById(Integer id) {
        return spareMapper.selectById(id);
    }

    public int insertSpare(Spare spare) {
        return spareMapper.insert(spare);
    }

    public int updateSpare(Spare spare) {
        return spareMapper.updateById(spare);
    }

    public int deleteSpare(Integer id) {
        return spareMapper.deleteById(id);
    }

    public int countSpare() {
        return spareMapper.selectCount(Wrappers.<Spare>lambdaQuery().ne(Spare::getState, 0));
    }
}
