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
public class TransComplexShardingAlgorithm implements ComplexKeysShardingAlgorithm<String> {

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames,
                                         ComplexKeysShardingValue<String> shardingValue) {
        Integer year = Integer.parseInt(shardingValue.getColumnNameAndShardingValuesMap().get("year").iterator().next().toString());
        String prov = shardingValue.getColumnNameAndShardingValuesMap().get("prov").iterator().next().toString();
        String suffix;
        if (year == 2024 || year == 2025 || year == 2026) {
            suffix = year.toString();
        } else {
            if (prov.startsWith("43") || prov.startsWith("44")) {
                suffix = "2024";
            } else if (prov.startsWith("50") || prov.startsWith("51")) {
                suffix = "2025";
            } else {
                suffix = "2026";
            }
        }
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
