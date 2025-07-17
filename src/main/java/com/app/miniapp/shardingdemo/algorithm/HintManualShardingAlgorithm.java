package com.app.miniapp.shardingdemo.algorithm;

import org.apache.shardingsphere.sharding.api.sharding.hint.HintShardingAlgorithm;
import org.apache.shardingsphere.sharding.api.sharding.hint.HintShardingValue;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.LinkedHashSet;

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
public class HintManualShardingAlgorithm implements HintShardingAlgorithm<Integer> {

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames,
                                         HintShardingValue<Integer> shardingValue) {
        Collection<String> result = new LinkedHashSet<>();
        for (Integer value : shardingValue.getValues()) {
            int suffix = value % 3; // 手动指定逻辑
            for (String tableName : availableTargetNames) {
                if (tableName.endsWith(String.valueOf(suffix))) {
                    result.add(tableName);
                }
            }
        }
        return result;
    }
}
