package com.spring.tutor.student;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping(path = "/student")
public class StudentController {
    Logger logger = LoggerFactory.getLogger(StudentController.class);
    private  final  StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    @GetMapping
    public List<Student> getStudents(){
        return studentService.getStudents();
    }

    @PostMapping
    public  void registerNewStudent(@RequestBody Student student){
        logger.info("Student Save method {}.", student);
        studentService.addNewStudent(student);
    }

    @DeleteMapping(path="/{studentId}")
    public void deleteStudent(@PathVariable("studentId") Long studentId){
        logger.info("delete student with id {}. ", studentId);
        studentService.deleteStudentById(studentId);
    }

    @PutMapping(path = "/{studentId}")
    public  void updateStudent(
            @PathVariable("studentId") Long studentId,
            @PathVariable(required = false) String name,
            @PathVariable(required = false) String email){
        studentService.updateStudent(studentId,name,email);
    }

    @PutMapping
    @ResponseBody
    public ResponseEntity<ResponseMessage> updateStudentRecord(@RequestBody Student student){
        try{
            studentService.update(student.getId(),student.getName(),student.getEmail());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseMessage("Successfully updated"));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseMessage("Error updating a Student Information"));
        }

    }
}

  /*  @PutMapping("/tutorials/{id}")
    public ResponseEntity<Tutorial> updateTutorial(@PathVariable("id") long id, @RequestBody Tutorial tutorial) {
        Optional<Tutorial> tutorialData = tutorialRepository.findById(id);
        if (tutorialData.isPresent()) {
            Tutorial _tutorial = tutorialData.get();
            _tutorial.setTitle(tutorial.getTitle());
            _tutorial.setDescription(tutorial.getDescription());
            _tutorial.setPublished(tutorial.isPublished());
            return new ResponseEntity<>(tutorialRepository.save(_tutorial), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

   */
 /*   @PutMapping("/employees/{id}")
    Employee replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {

        return repository.findById(id)
                .map(employee -> {
                    employee.setName(newEmployee.getName());
                    employee.setRole(newEmployee.getRole());
                    return repository.save(employee);
                })
                .orElseGet(() -> {
                    newEmployee.setId(id);
                    return repository.save(newEmployee);
                });
    }

  */