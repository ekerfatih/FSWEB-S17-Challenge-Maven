package com.workintech.spring17challenge.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data

@AllArgsConstructor
@Component
public class Grade {
    private int coefficient;
    private String note;
}
