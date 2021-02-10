package com.design.elecworkshopsys.controller;

import com.design.elecworkshopsys.bean.Memo;
import com.design.elecworkshopsys.service.MemoService;
import com.design.elecworkshopsys.utils.PageInfo;
import com.design.elecworkshopsys.utils.ReturnDatas;
import com.github.pagehelper.PageHelper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MemoController {



    
    @Resource
    private MemoService memoService;

    @GetMapping("/memo")
    public ReturnDatas listPojo(PageInfo<?> pageInfo, Memo memo) {
        PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
        List<Memo> memoList = memoService.listMemo(memo);
        return ReturnDatas.success().data(memoList).pageInfo(new PageInfo<Memo>(memoList));
    }

    @GetMapping("/memo/{id}")
    public ReturnDatas getPojo(@PathVariable Integer id) {
        return ReturnDatas.success().data(memoService.getMemoById(id));
    }

    @PostMapping("/memo")
    public ReturnDatas insertPojo(Memo memo) {
        Integer i = memoService.insertMemo(memo);
        return ReturnDatas.success().message("新增成功");
    }

    @PutMapping("/memo")
    public ReturnDatas updatePojo(Memo memo) {
        Integer i = memoService.updateMemo(memo);
        return ReturnDatas.success().message("更新成功");
    }

    @DeleteMapping("/memo/{id}")
    public ReturnDatas deletePojo(@PathVariable Integer id) {
        Integer i = memoService.deleteMemo(id);
        return ReturnDatas.success().message("删除成功");
    }

}
