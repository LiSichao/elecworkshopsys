package com.design.elecworkshopsys.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.design.elecworkshopsys.bean.Fault;
import com.design.elecworkshopsys.bean.Process;
import com.design.elecworkshopsys.bean.Station;
import com.design.elecworkshopsys.dao.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProcessService {
    @Resource
    private ProcessMapper processMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private FaultMapper faultMapper;
    @Resource
    private StationMapper stationMapper;
    @Resource
    private DeptMapper deptMapper;

    public List<Process> listProcess(Process process) {
        List<Process> processList = processMapper.selectList(Wrappers.<Process>lambdaQuery()
                .eq(process.getState() != null, Process::getState, process.getState())
                .eq(process.getUserId() != null, Process::getUserId, process.getUserId())
                .eq(process.getFaultId() != null, Process::getFaultId, process.getFaultId())
                .eq(process.getNotice() != null, Process::getNotice, process.getNotice())
                .eq(process.getNoticeDate() != null, Process::getNoticeDate, process.getNoticeDate())
        );
        for (Process processItem : processList) {
            processItem.setUser(userMapper.selectById(processItem.getUserId()));
            Fault fault = faultMapper.selectById(processItem.getFaultId());
            fault.setStation(stationMapper.selectById(fault.getStationId()));
            fault.setDept(deptMapper.selectById(fault.getDeptId()));
            processItem.setFault(fault);
        }
        return processList;
    }

    public Process getProcessById(Integer id) {
        return processMapper.selectById(id);
    }

    public int insertProcess(Process process) {
        process.setDatetime(LocalDateTime.now());
        return processMapper.insert(process);
    }

    public int updateProcess(Process process) {
        return processMapper.updateById(process);
    }

    public int deleteProcess(Integer id) {
        return processMapper.deleteById(id);
    }

}
