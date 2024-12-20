package com.confetaria.confetaria_backend.handler;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ErrorValidation extends ErrorResponse {

    private List<ValidationObject> erros;

    @Builder(builderMethodName = "errorValidationBuilder")
    ErrorValidation(String title, int status, String detail, List<ValidationObject> erros) {
        super(title, status, detail);
        this.erros = erros;
    }
}