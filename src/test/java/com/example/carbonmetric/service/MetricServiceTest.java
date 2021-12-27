package com.example.carbonmetric.service;

import com.example.carbonmetric.model.State;
import com.example.carbonmetric.model.Status;
import com.example.carbonmetric.repository.IMeasurementRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class MetricServiceTest {
    @MockBean
    private IMeasurementRepository repository;

    @Autowired
    private IMetricService metricService;
    private static UUID sampleUuid;


    @BeforeAll
    static void setUp() {
        sampleUuid = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
    }

    @Test
    void saveMeasurement() {
    }

    @Test
    void getStatus() {
        when(repository.getLastState(any())).thenReturn(Optional.of(new State(Status.OK, 0)));
        Assertions.assertEquals(Optional.of(Status.OK), metricService.getStatus(sampleUuid));
    }

    @Test
    void getStatusWhenNoRecords() {
        Assertions.assertEquals(Optional.empty(), metricService.getStatus(sampleUuid));
    }
}