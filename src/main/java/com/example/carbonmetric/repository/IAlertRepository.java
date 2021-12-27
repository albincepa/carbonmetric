package com.example.carbonmetric.repository;

import com.example.carbonmetric.model.Alert;
import com.influxdb.client.write.Point;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IAlertRepository {
    void save(Alert alert);

    Optional<Instant> getLastAlertInstant(UUID uuid);

    void saveAlertEndtime(Point point);

    List<Alert> getAllAlerts(UUID uuid);
}
