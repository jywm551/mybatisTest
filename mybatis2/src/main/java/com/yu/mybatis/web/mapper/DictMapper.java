package com.yu.mybatis.web.mapper;

import com.yu.mybatis.web.model.SysDict;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created By Yu On 2018/8/14
 * Description：
 **/
public interface DictMapper {

    SysDict selectByPrimaryKey(Long id);

    List<SysDict> selectBySysDict(SysDict sysDict, RowBounds rowBounds);

    int insert(SysDict sysDict);

    int updateById(SysDict sysDict);

    int deleteById(Long id);
}
