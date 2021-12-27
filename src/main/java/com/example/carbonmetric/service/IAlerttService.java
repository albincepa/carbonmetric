package com.example.carbonmetric.service;

import com.example.carbonmetric.dto.MeasurementDTO;
import com.example.carbonmetric.model.Alert;

import java.util.List;
import java.util.UUID;

public interface IAlerttService {
    void saveAlertStart(UUID uuid, MeasurementDTO measurementRequest);

    void saveAlertEnd(UUID uuid, String time);

    List<Alert> getAllAlerts(UUID uuid);
}
