package com.example.carbonmetric.repository;

import com.example.carbonmetric.configuration.DbProperties;
import com.example.carbonmetric.model.Measurement;
import com.example.carbonmetric.model.State;
import com.google.gson.Gson;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.WriteApi;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.query.FluxTable;
import com.influxdb.query.dsl.Flux;
import com.influxdb.query.dsl.functions.YieldFlux;
import com.influxdb.query.dsl.functions.restriction.Restrictions;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class MeasurementRepository implements IMeasurementRepository {

    public static final String CARBON_MEASUREMENT_NAME = "carbon";
    public static final Gson GSON = new Gson();
    private final DbProperties dbProperties;
    private final InfluxDBClient dbClient;
    private final WriteApi dbWriteApi;

    public MeasurementRepository(DbProperties dbProperties, InfluxDBClient dbClient, WriteApi dbWriteApi) {
        this.dbProperties = dbProperties;
        this.dbClient = dbClient;
        this.dbWriteApi = dbWriteApi;
    }

    @Override
    public void save(Measurement measurement) {
        dbWriteApi.writeMeasurement(WritePrecision.S, measurement);
    }

    @Override
    public Optional<State> getLastState(UUID uuid) {
        YieldFlux query = Flux.from(dbProperties.getBucket())
                .range(Instant.EPOCH)
                .filter(Restrictions.and(
                        Restrictions.measurement().equal(CARBON_MEASUREMENT_NAME),
                        Restrictions.field().equal("state"),
                        Restrictions.tag("uuid").equal(uuid.toString())
                ))
                .sort(new String[]{"_time"}, true)
                .first()
                .yield();
        List<FluxTable> tables = dbClient.getQueryApi().query(query.toString());
        if (tables.isEmpty() || tables.get(0).getRecords().isEmpty()) {
            return Optional.empty();
        }

        String stateJson = (String) tables.get(0).getRecords().get(0).getValue();
        State state = GSON.fromJson(stateJson, State.class);
        return Optional.of(state);
    }

    @Override
    public Optional<Long> getMaxLast30Days(UUID uuid) {

        YieldFlux query = Flux.from(dbProperties.getBucket())
                .range(Instant.now().minus(30, ChronoUnit.DAYS))
                .filter(Restrictions.and(
                        Restrictions.measurement().equal(CARBON_MEASUREMENT_NAME),
                        Restrictions.field().equal("co2"),
                        Restrictions.tag("uuid").equal(uuid.toString())
                ))
                .max()
                .yield();
        List<FluxTable> tables = dbClient.getQueryApi().query(query.toString());
        if (tables.isEmpty() || tables.get(0).getRecords().isEmpty()) {
            return Optional.empty();
        }

        Long maxValue = (Long) tables.get(0).getRecords().get(0).getValue();
        return Optional.of(maxValue);
    }

    @Override
    public Optional<Double> getAvgLast30Days(UUID uuid) {
        YieldFlux query = Flux.from(dbProperties.getBucket())
                .range(Instant.now().minus(30, ChronoUnit.DAYS))
                .filter(Restrictions.and(
                        Restrictions.measurement().equal(CARBON_MEASUREMENT_NAME),
                        Restrictions.field().equal("co2"),
                        Restrictions.tag("uuid").equal(uuid.toString())
                ))
                .mean()
                .yield();
        List<FluxTable> tables = dbClient.getQueryApi().query(query.toString());
        if (tables.isEmpty() || tables.get(0).getRecords().isEmpty()) {
            return Optional.empty();
        }
        Double avgValue = ((Double) tables.get(0).getRecords().get(0).getValue());
        return Optional.of(avgValue);
    }

    @Override
    public List<Long> getLastNMeasurements(UUID uuid, int n) {
        YieldFlux query = Flux.from(dbProperties.getBucket())
                .range(Instant.EPOCH)
                .filter(Restrictions.and(
                        Restrictions.measurement().equal(CARBON_MEASUREMENT_NAME),
                        Restrictions.field().equal("co2"),
                        Restrictions.tag("uuid").equal(uuid.toString())
                ))
                .sort(new String[]{"_time"}, false)
                .tail(n)
                .yield();
        List<FluxTable> tables = dbClient.getQueryApi().query(query.toString());
        if (tables.isEmpty() || tables.get(0).getRecords().isEmpty()) {
            return new ArrayList<>();
        }

        return tables.get(0).getRecords()
                .stream()
                .map(fluxRecord -> (Long) fluxRecord.getValue())
                .collect(Collectors.toList());

    }
}
