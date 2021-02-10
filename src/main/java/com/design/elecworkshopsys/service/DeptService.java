package com.design.elecworkshopsys.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.design.elecworkshopsys.bean.Dept;
import com.design.elecworkshopsys.dao.DeptMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DeptService {
    @Resource
    private DeptMapper deptMapper;

    public List<Dept> listDept(Dept dept) {
        List<Dept> deptList = deptMapper.selectList(Wrappers.<Dept>lambdaQuery()
                .eq(dept.getState() != null, Dept::getState, dept.getState())
                .eq(dept.getLevel() != null, Dept::getLevel, dept.getLevel())
                .eq(dept.getType() != null, Dept::getType, dept.getType())
        );
        return deptList;
    }

    public Dept getDeptById(Integer id) {
        return deptMapper.selectById(id);
    }

    public int insertDept(Dept dept) {
        return deptMapper.insert(dept);
    }

    public int updateDept(Dept dept) {
        return deptMapper.updateById(dept);
    }

    public int deleteDept(Integer id) {
        return deptMapper.deleteById(id);
    }

}
