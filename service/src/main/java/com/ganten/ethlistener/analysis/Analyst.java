package com.ganten.ethlistener.analysis;

import com.ganten.ethlistener.model.MintAnalysisResult;
import com.ganten.ethlistener.model.TransferEvent;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class Analyst {

    private static final String ZeroAddress = "0x0000000000000000000000000000000000000000";

    /**
     * mint 结果
     */
    public static MintAnalysisResult mintAnalysisResult(List<TransferEvent> events) {
        if (events == null || events.isEmpty()) {
            return null;
        }
        double totalValue = 0;
        Map<String, Double> mintMap = new HashMap<>();
        for (TransferEvent event : events) {
            if (!event.getFrom().equals(ZeroAddress)) {
                continue;
            }
            totalValue += event.getValue();
            mintMap.put(event.getTo(), mintMap.getOrDefault(event.getTo(), 0.0) + event.getValue());
        }
        return new MintAnalysisResult(totalValue, mintMap);
    }
}
