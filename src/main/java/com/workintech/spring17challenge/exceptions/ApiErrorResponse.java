package com.workintech.spring17challenge.exceptions;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Component

public class ApiErrorResponse {
    int status;
    private String message;
    long timestamp;
}
