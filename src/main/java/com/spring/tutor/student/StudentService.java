package com.spring.tutor.student;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Service
public class StudentService {

    public List<Student> getStudents(){
        return List.of(
                new Student(
                        1L,
                        "Murad",
                        "murad@gmail.com",
                        LocalDate.of(200, Month.APRIL, 5),
                        21
                )
        );
    }
}
