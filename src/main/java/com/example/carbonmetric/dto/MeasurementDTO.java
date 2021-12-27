package com.example.carbonmetric.dto;

import java.time.OffsetDateTime;

public class MeasurementDTO {
    long co2;
    String time;

    public long getCo2() {
        return co2;
    }

    public void setCo2(long co2) {
        this.co2 = co2;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
