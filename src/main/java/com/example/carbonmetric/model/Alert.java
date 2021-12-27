package com.example.carbonmetric.model;

import com.influxdb.annotations.Column;

import java.util.UUID;

import static com.example.carbonmetric.repository.AlertRepository.ALERT_MEASUREMENT_NAME;


@com.influxdb.annotations.Measurement(name = ALERT_MEASUREMENT_NAME)
public class Alert {

    @Column(tag = true)
    UUID uuid;

    @Column
    String startTime;

    @Column
    String endTime;

    @Column
    long measurement1;

    @Column
    long measurement2;

    @Column
    long measurement3;

    public Alert(UUID uuid, String startTime, long measurement1, long measurement2, long measurement3) {
        this.uuid = uuid;
        this.startTime = startTime;
        this.measurement1 = measurement1;
        this.measurement2 = measurement2;
        this.measurement3 = measurement3;
    }

    public Alert() {
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
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
