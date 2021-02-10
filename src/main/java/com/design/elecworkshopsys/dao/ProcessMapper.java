package com.design.elecworkshopsys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.design.elecworkshopsys.bean.Process;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProcessMapper extends BaseMapper<Process> {
}