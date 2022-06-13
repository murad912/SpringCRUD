package com.spring.tutor.student;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.SQLOutput;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public class StudentService {

    Logger logger = LoggerFactory.getLogger(StudentService.class);

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents(){
        return  studentRepository.findAll();
    }

    public void  addNewStudent(Student student) {
        Optional<Student> studentOptional = studentRepository.findStudentByEmail(student.getEmail());
          if(studentOptional.isPresent())  {
              logger.error("email already exist");
              throw new IllegalStateException("Email taken");
          }
          studentRepository.save(student);
    }

    public void deleteStudentById(Long studentId) {
       boolean exists =  studentRepository.existsById(studentId);
      if(!exists){
          throw new IllegalStateException("student with id " + studentId + " does not exists");
      }
      studentRepository.deleteById(studentId);
    }

    @Transactional
    public void updateStudent(Long studentId, String name, String email){
        Student student = studentRepository.findById(studentId).orElseThrow(()-> new IllegalStateException(
                "student with id " + studentId + " does not exist"
        ));
        System.out.println("student.name------------------" + student.getName());
        System.out.println("name------------------" + name);
        if(name != null && name.length()> 0 && !Objects.equals(student.getName(), name)){
            student.setName(name);
        }
        if(email != null && email.length()> 0 && !Objects.equals(student.getEmail(), email)){
            Optional<Student> studentOptional = studentRepository.findStudentByEmail(email);
            if(studentOptional.isPresent()){
                throw new IllegalStateException("email taken");
            }
            student.setEmail(email);
        }
    }
    @Transactional
    public void update(Long studentId, String name, String email) {
        try {
            Student student = studentRepository.findStudentById(studentId);
            System.out.println("student -------> " + student);
            System.out.println("student.getId-------> " + student.getId());
            System.out.println(" student Id-------> " + studentId);
            System.out.println("name-------> " + name);
            System.out.println("email-------> " + email);

                if (student != null) {
                    if (Objects.equals(student.getId(), studentId)){
                        student.setName(name);
                        student.setEmail(email);
                        logger.info("updating student information");
                        studentRepository.save(student);
                    } else {
                        System.out.println("Student ID "+ studentId + " is not Exist in the table");
                        logger.error("Student with id " + studentId + " not exist");
                    }

                }
            } catch (Exception e) {
                logger.error("Id is not exist " + studentId);
                e.printStackTrace();
            }
        }
}


/*return List.of(
                new Student(
                        1L,
                        "Murad",
                        "murad@gmail.com",
                        LocalDate.of(200, Month.APRIL, 5),
                        21
                )
         );

 */