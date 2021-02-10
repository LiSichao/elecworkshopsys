package com.design.elecworkshopsys.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.design.elecworkshopsys.bean.Memo;
import com.design.elecworkshopsys.dao.MemoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MemoService {
    @Resource
    private MemoMapper memoMapper;

    public List<Memo> listMemo(Memo memo) {
        List<Memo> memoList = memoMapper.selectList(Wrappers.<Memo>lambdaQuery()
                .eq(memo.getState() != null, Memo::getState, memo.getState())
                .eq(memo.getUserId() != null, Memo::getUserId, memo.getUserId())
        );
        return memoList;
    }

    public Memo getMemoById(Integer id) {
        return memoMapper.selectById(id);
    }

    public int insertMemo(Memo memo) {
        return memoMapper.insert(memo);
    }

    public int updateMemo(Memo memo) {
        return memoMapper.updateById(memo);
    }

    public int deleteMemo(Integer id) {
        return memoMapper.deleteById(id);
    }

}
