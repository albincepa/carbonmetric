package com.example.carbonmetric.dto;

import com.example.carbonmetric.model.Status;

public class StatusDTO {

    Status status;

    public StatusDTO(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
