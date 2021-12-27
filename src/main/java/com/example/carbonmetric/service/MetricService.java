package com.example.carbonmetric.service;

import com.example.carbonmetric.dto.MeasurementDTO;
import com.example.carbonmetric.model.Measurement;
import com.example.carbonmetric.model.Metrics;
import com.example.carbonmetric.model.State;
import com.example.carbonmetric.model.Status;
import com.example.carbonmetric.repository.IMeasurementRepository;
import com.example.carbonmetric.service.statemachine.StateMachine;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class MetricService implements IMetricService {
    public static final State DEFAULT_STATE = new State(Status.OK, 0);
    private final IMeasurementRepository measurementRepository;
    private final StateMachine stateMachine;
    private final IAlerttService alertService;

    public MetricService(IMeasurementRepository measurementRepository, StateMachine stateMachine, IAlerttService alertService) {
        this.measurementRepository = measurementRepository;
        this.stateMachine = stateMachine;
        this.alertService = alertService;
    }

    @Override
    public void saveMeasurement(UUID uuid, MeasurementDTO measurementRequest) {
        Measurement measurement = new Measurement();
        measurement.setUuid(uuid);
        measurement.setCo2(measurementRequest.getCo2());
        measurement.setTimestamp(measurementRequest.getTime());
        State lastState = measurementRepository.getLastState(uuid).orElse(DEFAULT_STATE);
        State newState = stateMachine.getNewState(lastState, measurementRequest.getCo2());
        measurement.setState(newState);

        // save alert start
        if (Status.WARN == lastState.getStatus() && Status.ALERT == newState.getStatus()) {
            alertService.saveAlertStart(uuid, measurementRequest);
        }
        // save alert start
        if (Status.ALERT == lastState.getStatus() && Status.OK == newState.getStatus()) {
            alertService.saveAlertEnd(uuid, measurementRequest.getTime());
        }

        measurementRepository.save(measurement);

    }

    @Override
    public Optional<Status> getStatus(UUID uuid) {
        Optional<State> lastState = measurementRepository.getLastState(uuid);

        if (lastState.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(lastState.get().getStatus());
    }

    @Override
    public Optional<Metrics> getMetrics(UUID uuid) {

        Optional<Long> maxLast30Days = measurementRepository.getMaxLast30Days(uuid);
        Optional<Double> avgLast30Days = measurementRepository.getAvgLast30Days(uuid);

        if (maxLast30Days.isEmpty() || avgLast30Days.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(new Metrics(maxLast30Days.get(), avgLast30Days.get()));

    }


}
