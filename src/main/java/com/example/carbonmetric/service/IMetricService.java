package com.example.carbonmetric.service;

import com.example.carbonmetric.dto.MeasurementDTO;
import com.example.carbonmetric.model.Metrics;
import com.example.carbonmetric.model.Status;

import java.util.Optional;
import java.util.UUID;

public interface IMetricService {
    void saveMeasurement(UUID uuid, MeasurementDTO measurement);

    Optional<Status> getStatus(UUID uuid);

    Optional<Metrics> getMetrics(UUID uuid);
}
