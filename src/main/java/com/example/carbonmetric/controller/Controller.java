package com.example.carbonmetric.controller;

import com.example.carbonmetric.dto.AlertDTO;
import com.example.carbonmetric.dto.MeasurementDTO;
import com.example.carbonmetric.dto.MetricsDTO;
import com.example.carbonmetric.dto.StatusDTO;
import com.example.carbonmetric.model.Alert;
import com.example.carbonmetric.model.Metrics;
import com.example.carbonmetric.model.Status;
import com.example.carbonmetric.service.IAlerttService;
import com.example.carbonmetric.service.IMetricService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/sensors")
public class Controller {

    private final IMetricService metricService;
    private final IAlerttService alertService;

    public Controller(IMetricService metricService, IAlerttService alertService) {
        this.metricService = metricService;
        this.alertService = alertService;
    }

    @PostMapping("/{uuid}/mesurements")
    public ResponseEntity<?> saveMeasurements(@PathVariable UUID uuid, @RequestBody MeasurementDTO measurementRequest) {
        metricService.saveMeasurement(uuid, measurementRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<?> status(@PathVariable UUID uuid) {

        Optional<Status> status = metricService.getStatus(uuid);
        if (status.isEmpty()) {
            return new ResponseEntity<>("No record found", HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(new StatusDTO(status.get()), HttpStatus.OK);
    }

    @GetMapping("/{uuid}/metrics")
    public ResponseEntity<?> metrics(@PathVariable UUID uuid) {

        Optional<Metrics> metrics = metricService.getMetrics(uuid);
        if (metrics.isEmpty()) {
            return new ResponseEntity<>("No record found", HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(new MetricsDTO(metrics.get().getMaxLast30Days(), metrics.get().getAvgLast30Days()), HttpStatus.OK);
    }

    @GetMapping("/{uuid}/alerts")
    public ResponseEntity<?> alerts(@PathVariable UUID uuid) {

        List<Alert> alerts = alertService.getAllAlerts(uuid);
        if (alerts.isEmpty()) {
            return new ResponseEntity<>("No record found", HttpStatus.NO_CONTENT);
        }
        List<AlertDTO> alertsDTOList = alerts.stream()
                .map(alert ->
                        new AlertDTO(alert.getStartTime(), alert.getEndTime(), alert.getMeasurement1(), alert.getMeasurement2(), alert.getMeasurement3())
                )
                .collect(Collectors.toList());

        return new ResponseEntity<>(alertsDTOList, HttpStatus.OK);
    }
}
