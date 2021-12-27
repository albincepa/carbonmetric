package com.example.carbonmetric.service;

import com.example.carbonmetric.dto.MeasurementDTO;
import com.example.carbonmetric.model.Alert;
import com.example.carbonmetric.repository.AlertRepository;
import com.example.carbonmetric.repository.IAlertRepository;
import com.example.carbonmetric.repository.IMeasurementRepository;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class AlertService implements IAlerttService {
    private final IMeasurementRepository measurementRepository;
    private final IAlertRepository alertRepository;

    public AlertService(IMeasurementRepository measurementRepository, IAlertRepository alertRepository) {
        this.measurementRepository = measurementRepository;
        this.alertRepository = alertRepository;
    }

    @Override
    public void saveAlertStart(UUID uuid, MeasurementDTO measurementRequest) {
        List<Long> last2Measurements = measurementRepository.getLastNMeasurements(uuid, 2);
        Alert alert = new Alert(uuid, measurementRequest.getTime(), last2Measurements.get(0), last2Measurements.get(1), measurementRequest.getCo2());
        alertRepository.save(alert);
    }

    @Override
    public void saveAlertEnd(UUID uuid, String endTime) {
        Instant lastAlertInstant = alertRepository.getLastAlertInstant(uuid).orElseThrow();
        Point point = Point.measurement(AlertRepository.ALERT_MEASUREMENT_NAME)
                .addTag("uuid", uuid.toString())
                .addField("endTime", endTime)
                .time(lastAlertInstant, WritePrecision.S);
        alertRepository.saveAlertEndtime(point);

    }

    @Override
    public List<Alert> getAllAlerts(UUID uuid) {
        return alertRepository.getAllAlerts(uuid);
    }
}
