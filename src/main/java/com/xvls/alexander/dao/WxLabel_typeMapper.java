package com.xvls.alexander.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xvls.alexander.entity.wx.Label_type;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 标签类型，service与WxLabelService合并了
 */
@Repository
public interface WxLabel_typeMapper extends BaseMapper<Label_type> {

    /**
     * 获得所有标签类型下的所有标签
     * @return
     */
    List<Label_type> getAllLabelList();
}
