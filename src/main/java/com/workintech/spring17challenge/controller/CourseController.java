package com.workintech.spring17challenge.controller;

import com.workintech.spring17challenge.entity.CourseResponse;
import com.workintech.spring17challenge.exceptions.ApiException;
import com.workintech.spring17challenge.model.Course;
import com.workintech.spring17challenge.model.HighCourseGpa;
import com.workintech.spring17challenge.model.LowCourseGpa;
import com.workintech.spring17challenge.model.MediumCourseGpa;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
public class CourseController {
    List<Course> courses;
    LowCourseGpa lowCourseGpa;
    MediumCourseGpa mediumCourseGpa;
    HighCourseGpa highCourseGpa;

    @Autowired
    public CourseController(List<Course> courses, LowCourseGpa lowCourseGpa, MediumCourseGpa mediumCourseGpa, HighCourseGpa highCourseGpa) {
        this.courses = courses;
        this.lowCourseGpa = lowCourseGpa;
        this.mediumCourseGpa = mediumCourseGpa;
        this.highCourseGpa = highCourseGpa;
    }

    @GetMapping("/courses")
    public List<Course> getCourseList() {
        return courses;
    }

    @GetMapping("/courses/{name}")
    public Course getCourseByName(@PathVariable String name) {
        Course course = courses.stream()
                .filter(x -> x.getName() != null && name.equalsIgnoreCase(x.getName()))
                .findFirst()
                .orElse(null);

        if (course == null) {
            throw new ApiException("Given name couldn't be found", HttpStatus.NOT_FOUND);
        }

        return course;
    }

    @PostMapping("/courses")
    public ResponseEntity<CourseResponse> addCourse(@RequestBody Course course) {
        if (course.getId() == null || course.getName() == null || course.getGrade() == null || course.getCredit() == null)
            throw new ApiException("A given value is null please enter valid data", HttpStatus.BAD_REQUEST);
        courses.add(course);
        int courseGradeCoefficient = course.getGrade().getCoefficient();
        int credit = course.getCredit();
        int totalGpa;
        totalGpa = getTotalGpa(credit, courseGradeCoefficient);
        return new ResponseEntity<>(new CourseResponse(course, totalGpa), HttpStatus.CREATED);
    }

    private int getTotalGpa(int credit, int courseGradeCoefficient) {
        int totalGpa;
        switch (credit) {
            case 1, 2:
                totalGpa = credit * courseGradeCoefficient * lowCourseGpa.getGpa();
                break;
            case 3:
                totalGpa = credit * courseGradeCoefficient * mediumCourseGpa.getGpa();
                break;
            case 4:
                totalGpa = credit * courseGradeCoefficient * highCourseGpa.getGpa();
                break;
            default:
                totalGpa = 1;
                System.out.println("Error occurred");
        }
        return totalGpa;
    }

    @PutMapping("/courses/{id}")
    public CourseResponse updateCourse(@PathVariable int id, @RequestBody Course updateData) {
        Course course = courses.get(id);
        BeanUtils.copyProperties(updateData, course, "id");
        int courseGradeCoefficient = course.getGrade().getCoefficient();
        int credit = course.getCredit();
        int totalGpa;
        totalGpa = getTotalGpa(credit, courseGradeCoefficient);
        return new CourseResponse(course, totalGpa);
    }

    @DeleteMapping("courses/{id}")
    public Course deleteCourse(@PathVariable int id) {
        Course course = courses.get(id);
        courses.remove(course);
        return course;
    }


    @Bean
    public int x() {
        return 0;
    }

    @Bean
    public String y() {
        return "*";
    }
}
