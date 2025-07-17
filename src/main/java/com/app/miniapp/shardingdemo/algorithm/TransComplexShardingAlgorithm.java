package com.app.miniapp.shardingdemo.algorithm;

import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingValue;
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
@Component
public class TransComplexShardingAlgorithm implements ComplexKeysShardingAlgorithm<String> {

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames,
                                         ComplexKeysShardingValue<String> shardingValue) {
        Integer year = Integer.parseInt(shardingValue.getColumnNameAndShardingValuesMap().get("year").iterator().next().toString());
        String fileType = shardingValue.getColumnNameAndShardingValuesMap().get("file_type").iterator().next().toString();

        String suffix = (year % 2 == 0 && "IMG".equalsIgnoreCase(fileType)) ? "2024" : "2025";
        Collection<String> result = new LinkedHashSet<>();
        for (String targetName : availableTargetNames) {
            if (targetName.endsWith(suffix)) {
                result.add(targetName);
            }
        }
        return result;
    }

    @Override
    public String getType() {
        return "CLASS_BASED";
    }
}
