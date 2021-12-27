package com.example.carbonmetric.repository;

import com.example.carbonmetric.configuration.DbProperties;
import com.example.carbonmetric.model.Alert;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.WriteApi;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import com.influxdb.query.dsl.Flux;
import com.influxdb.query.dsl.functions.YieldFlux;
import com.influxdb.query.dsl.functions.restriction.Restrictions;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class AlertRepository implements IAlertRepository {
    public static final String ALERT_MEASUREMENT_NAME = "alert";
    private final WriteApi dbWriteApi;
    private final InfluxDBClient dbClient;
    private final DbProperties dbProperties;

    public AlertRepository(WriteApi dbWriteApi, InfluxDBClient dbClient, DbProperties dbProperties) {
        this.dbWriteApi = dbWriteApi;
        this.dbClient = dbClient;
        this.dbProperties = dbProperties;
    }

    @Override
    public void save(Alert alert) {
        dbWriteApi.writeMeasurement(WritePrecision.S, alert);
    }

    @Override
    public Optional<Instant> getLastAlertInstant(UUID uuid) {

        YieldFlux query = Flux.from(dbProperties.getBucket())
                .range(Instant.EPOCH)
                .filter(Restrictions.and(
                        Restrictions.measurement().equal(ALERT_MEASUREMENT_NAME),
                        Restrictions.field().equal("startTime"),
                        Restrictions.tag("uuid").equal(uuid.toString())
                ))
                .sort(new String[]{"_time"}, true)
                .first()
                .yield();
        List<FluxTable> tables = dbClient.getQueryApi().query(query.toString());
        if (tables.isEmpty() || tables.get(0).getRecords().isEmpty()) {
            return Optional.empty();
        }

        return Optional.ofNullable(tables.get(0).getRecords().get(0).getTime());
    }

    @Override
    public void saveAlertEndtime(Point point) {
        dbWriteApi.writePoint(point);
    }

    @Override
    public List<Alert> getAllAlerts(UUID uuid) {
        YieldFlux query = Flux.from(dbProperties.getBucket())
                .range(Instant.EPOCH)
                .filter(Restrictions.and(
                        Restrictions.measurement().equal(ALERT_MEASUREMENT_NAME),
                        Restrictions.tag("uuid").equal(uuid.toString())
                ))
                .sort(new String[]{"_time"}, false)
                .pivot(new String[]{"_time"}, new String[]{"_measurement", "_field"}, "_value")
                .yield();
        List<FluxTable> tables = dbClient.getQueryApi().query(query.toString());
        if (tables.isEmpty() || tables.get(0).getRecords().isEmpty()) {
            return Collections.emptyList();
        }

        return tables.get(0).getRecords()
                .stream()
                .map(this::createAlert)
                .collect(Collectors.toList());
    }

    @NotNull
    private Alert createAlert(FluxRecord fluxRecord) {
        Alert alert = new Alert();
        String uuid = (String) fluxRecord.getValueByKey("uuid");
        alert.setUuid(UUID.fromString(uuid));
        String startTime = (String) fluxRecord.getValueByKey("alert_startTime");
        alert.setStartTime(startTime);
        String endTime = (String) fluxRecord.getValueByKey("alert_endTime");
        alert.setEndTime(endTime);
        Long measurement1 = (Long) fluxRecord.getValueByKey("alert_measurement1");
        alert.setMeasurement1(measurement1);
        Long measurement2 = (Long) fluxRecord.getValueByKey("alert_measurement2");
        alert.setMeasurement2(measurement2);
        Long measurement3 = (Long) fluxRecord.getValueByKey("alert_measurement3");
        alert.setMeasurement3(measurement3);
        return alert;
    }
}
