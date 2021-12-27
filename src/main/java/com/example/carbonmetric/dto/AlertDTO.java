package com.example.carbonmetric.dto;

public class AlertDTO {

    String startTime;

    String endTime;

    long measurement1;

    long measurement2;

    long measurement3;

    public AlertDTO(String startTime, String endTime, long measurement1, long measurement2, long measurement3) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.measurement1 = measurement1;
        this.measurement2 = measurement2;
        this.measurement3 = measurement3;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public long getMeasurement1() {
        return measurement1;
    }

    public void setMeasurement1(long measurement1) {
        this.measurement1 = measurement1;
    }

    public long getMeasurement2() {
        return measurement2;
    }

    public void setMeasurement2(long measurement2) {
        this.measurement2 = measurement2;
    }

    public long getMeasurement3() {
        return measurement3;
    }

    public void setMeasurement3(long measurement3) {
        this.measurement3 = measurement3;
    }
}
