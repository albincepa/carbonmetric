package com.example.carbonmetric.model;

import com.influxdb.annotations.Column;

import java.util.UUID;

import static com.example.carbonmetric.repository.MeasurementRepository.CARBON_MEASUREMENT_NAME;

@com.influxdb.annotations.Measurement(name = CARBON_MEASUREMENT_NAME
)
public class Measurement {
    @Column(tag = true)
    UUID uuid;

    @Column
    long co2;

    @Column
    String timestamp;

    @Column
    State state;


    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public long getCo2() {
        return co2;
    }

    public void setCo2(long co2) {
        this.co2 = co2;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
