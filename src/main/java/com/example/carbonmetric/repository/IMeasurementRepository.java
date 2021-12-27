package com.example.carbonmetric.repository;

import com.example.carbonmetric.model.Measurement;
import com.example.carbonmetric.model.State;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IMeasurementRepository {
    void save(Measurement measurement);

    Optional<State> getLastState(UUID uuid);

    Optional<Long> getMaxLast30Days(UUID uuid);

    Optional<Double> getAvgLast30Days(UUID uuid);

    List<Long> getLastNMeasurements(UUID uuid, int n);
}
