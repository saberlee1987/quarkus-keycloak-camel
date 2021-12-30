package com.saber.dto;

import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.Data;

@Data
public class ServiceErrorResponse {
    private Integer code;
    private String message;
    @JsonRawValue
    private String originalMessage;
}
