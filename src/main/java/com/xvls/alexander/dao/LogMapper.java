package com.xvls.alexander.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xvls.alexander.entity.Log;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统日志 Mapper 接口
 * </p>
 *
 * @author wangl
 * @since 2018-01-14
 */
@Repository
public interface LogMapper extends BaseMapper<Log> {

    List<Map> selectSelfMonthData();
}
