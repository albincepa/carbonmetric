package com.example.carbonmetric.dto;

public class MetricsDTO {
    long maxLast30Days;
    Double avgLast30Days;

    public MetricsDTO(long maxLast30Days, Double avgLast30Days) {
        this.maxLast30Days = maxLast30Days;
        this.avgLast30Days = avgLast30Days;
    }

    public long getMaxLast30Days() {
        return maxLast30Days;
    }

    public void setMaxLast30Days(long maxLast30Days) {
        this.maxLast30Days = maxLast30Days;
    }

    public Double getAvgLast30Days() {
        return avgLast30Days;
    }

    public void setAvgLast30Days(Double avgLast30Days) {
        this.avgLast30Days = avgLast30Days;
    }
}
