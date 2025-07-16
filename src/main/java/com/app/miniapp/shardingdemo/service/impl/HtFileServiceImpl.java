package com.app.miniapp.shardingdemo.service.impl;

import com.app.miniapp.shardingdemo.entity.HtFile;
import com.app.miniapp.shardingdemo.mapper.HtFileMapper;
import com.app.miniapp.shardingdemo.service.HtFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>PURPOSE:
 * <p>DESCRIPTION:
 * <p>CALLED BY: wanjian
 * <p>CREATE DATE: 2025/7/15
 * <p>UPDATE DATE: 2025/7/15
 * <p>UPDATE USER:
 * <p>HISTORY: 1.0
 *
 * @author wanjian
 * @version 1.0
 * @see
 * @since java 1.8
 */
@Service
public class HtFileServiceImpl implements HtFileService {

    @Autowired
    private HtFileMapper htFileMapper;
    @Override
    public void saveBatch(List<HtFile> files) {
        if (files != null && !files.isEmpty()) {
            htFileMapper.saveBatch(files);
        }
    }
}
