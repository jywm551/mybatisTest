package com.yu.mybatis.web.service.impl;

import com.yu.mybatis.web.mapper.DictMapper;
import com.yu.mybatis.web.model.SysDict;
import com.yu.mybatis.web.service.DictService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created By Yu On 2018/8/14
 * Description：
 **/
@Service
public class DictServiceImpl implements DictService {

    @Autowired
    private DictMapper dictMapper;

    @Override
    public SysDict findById(@NotNull Long id) {
        return dictMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<SysDict> findBySysDict(SysDict sysDict, Integer offset, Integer limit) {
        RowBounds row = RowBounds.DEFAULT;
        if (offset != null && limit != null) {
            row = new RowBounds(offset, limit);
        }
        return dictMapper.selectBySysDict(sysDict, row);
    }

    @Override
    public boolean saveOrUpdate(SysDict sysDict) {
        if (sysDict.getId() == null) {
            return dictMapper.insert(sysDict) == 1;
        }
        return dictMapper.updateById(sysDict) == 1;
    }

    @Override
    public boolean deleteById(Long id) {
        return dictMapper.deleteById(id) == 1;
    }
}
