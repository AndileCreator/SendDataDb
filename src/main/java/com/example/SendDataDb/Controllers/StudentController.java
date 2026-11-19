package com.example.SendDataDb.Controllers;

import com.example.SendDataDb.DTO.StudentRequest;
import com.example.SendDataDb.DTO.StudentResponse;
import com.example.SendDataDb.Service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    //Create
    @PostMapping
    public ResponseEntity<StudentResponse> createStudent(
            @Valid @RequestBody StudentRequest request) {

        StudentResponse response =
                studentService.createStudent(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    //Read All
    @GetMapping
    public ResponseEntity<List<StudentResponse>> getAllStudents(){
        return ResponseEntity.ok(
                studentService.getAllStudents()
        );
    }

    //Read by Email
//    @GetMapping("/email/{email}")
//    public ResponseEntity<StudentResponse> getStudentByEmail(
//            @PathVariable String email){
//        return ResponseEntity.ok(
//                studentService.getStudentByEmail(email)
//        );
//    }

    //Read One
    @GetMapping("/{id}")
    public ResponseEntity<StudentResponse> getStudentById(@PathVariable long id) {

        return ResponseEntity.ok(
                studentService.getStudentById(id)
        );
    }

    //UPDATE
    @PostMapping("/{id}")
    public ResponseEntity<StudentResponse> updateStudent(
            @PathVariable long id,
            @Valid @RequestBody StudentRequest request){
        return ResponseEntity.ok(
                studentService.getStudentById(id)
        );
    }

    //DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<StudentResponse> deleteStudent(
            @PathVariable long id){

        studentService.deleteStudentById(id);
        return ResponseEntity.noContent().build();
    }
}
