package com.workintech.spring17challenge.entity;

import com.workintech.spring17challenge.model.Course;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CourseResponse {
    private Course course;
    private double totalGpa;
}