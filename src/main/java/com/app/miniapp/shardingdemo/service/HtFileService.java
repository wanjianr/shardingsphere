package com.app.miniapp.shardingdemo.service;

import com.app.miniapp.shardingdemo.entity.HtFile;

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
public interface HtFileService {
    void saveBatch(List<HtFile> files);
}
