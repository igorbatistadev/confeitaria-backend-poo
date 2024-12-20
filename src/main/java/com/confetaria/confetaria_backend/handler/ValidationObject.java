package com.confetaria.confetaria_backend.handler;

public record ValidationObject(
        String message,
        String field,
        Object parameter
) {
}
