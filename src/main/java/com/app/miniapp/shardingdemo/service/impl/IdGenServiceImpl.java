package com.app.miniapp.shardingdemo.service.impl;

import com.app.miniapp.shardingdemo.entity.IdGen;
import com.app.miniapp.shardingdemo.mapper.IdGenMapper;
import com.app.miniapp.shardingdemo.service.IdGenService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>PURPOSE:
 * <p>DESCRIPTION:
 * <p>CALLED BY: wanjian
 * <p>CREATE DATE: 2025/7/28
 * <p>UPDATE DATE: 2025/7/28
 * <p>UPDATE USER:
 * <p>HISTORY: 1.0
 *
 * @author wanjian
 * @version 1.0
 * @see
 * @since java 1.8
 */
@Service
public class IdGenServiceImpl extends ServiceImpl<IdGenMapper, IdGen> implements IdGenService {
}
