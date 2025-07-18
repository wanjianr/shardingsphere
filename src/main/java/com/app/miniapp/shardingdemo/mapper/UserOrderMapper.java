package com.app.miniapp.shardingdemo.mapper;

import com.app.miniapp.shardingdemo.entity.UserOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>PURPOSE:
 * <p>DESCRIPTION:
 * <p>CALLED BY: wanjian
 * <p>CREATE DATE: 2025/7/17
 * <p>UPDATE DATE: 2025/7/17
 * <p>UPDATE USER:
 * <p>HISTORY: 1.0
 *
 * @author wanjian
 * @version 1.0
 * @see
 * @since java 1.8
 */
@Mapper
public interface UserOrderMapper extends BaseMapper<UserOrder> {
    // This interface can be used to define custom query methods for Orders if needed.
}
