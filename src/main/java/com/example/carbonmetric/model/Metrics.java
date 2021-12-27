package com.example.carbonmetric.model;

public class Metrics {
    long maxLast30Days;
    double avgLast30Days;

    public long getMaxLast30Days() {
        return maxLast30Days;
    }

    public Metrics(long maxLast30Days, double avgLast30Days) {
        this.maxLast30Days = maxLast30Days;
        this.avgLast30Days = avgLast30Days;
    }

    public void setMaxLast30Days(long maxLast30Days) {
        this.maxLast30Days = maxLast30Days;
    }

    public double getAvgLast30Days() {
        return avgLast30Days;
    }

    public void setAvgLast30Days(double avgLast30Days) {
        this.avgLast30Days = avgLast30Days;
    }
}
