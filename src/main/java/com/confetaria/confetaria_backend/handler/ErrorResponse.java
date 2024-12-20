package com.confetaria.confetaria_backend.handler;

import lombok.Data;

@Data
public class ErrorResponse {
        private String title;
        private int status;
        private String details;

    public ErrorResponse(String title, int status, String detail) {
        this.title = title;
        this.status = status;
        this.details = detail;
    }
}
