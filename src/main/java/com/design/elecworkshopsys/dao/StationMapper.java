package com.design.elecworkshopsys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.design.elecworkshopsys.bean.Station;
import com.design.elecworkshopsys.bean.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StationMapper extends BaseMapper<Station> {
}