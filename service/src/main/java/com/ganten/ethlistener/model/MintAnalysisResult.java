package com.ganten.ethlistener.model;

import java.util.Map;

public class MintAnalysisResult {

    private double totalValue;
    private Map<String, Double> mintMap;

    public MintAnalysisResult(double totalValue, Map<String, Double> mintMap) {
        this.totalValue = totalValue;
        this.mintMap = mintMap;
    }

    public double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(double totalValue) {
        this.totalValue = totalValue;
    }

    public Map<String, Double> getMintMap() {
        return mintMap;
    }

    public void setMintMap(Map<String, Double> mintMap) {
        this.mintMap = mintMap;
    }
}
