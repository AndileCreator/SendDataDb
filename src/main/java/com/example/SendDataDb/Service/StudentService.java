package com.example.SendDataDb.Service;

import com.example.SendDataDb.DTO.StudentRequest;
import com.example.SendDataDb.DTO.StudentResponse;
import com.example.SendDataDb.Entity.Students;
import com.example.SendDataDb.Exceptions.DuplicateResourceException;
import com.example.SendDataDb.Exceptions.ResourceNotFoundException;
import com.example.SendDataDb.Repository.StudentRepository;

import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    //CREATE
    @Transactional
    public StudentResponse createStudent(StudentRequest request) {
        if (studentRepository.existsByEmail(request.email())){
            throw new DuplicateResourceException(
                    "A student with this email already exists"
            );
        }

        Students students = new Students();
        students.setFirstName(request.firstName());
        students.setLastName(request.lastName());
        students.setEmail(request.email());

        Students savedStudent = studentRepository.save(students);
        return mapToResponse(savedStudent);
    }

    //READ ALL
    @Transactional(readOnly = true)
    public List<StudentResponse> getAllStudents(){

        return studentRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    //READ ONE
    @Transactional(readOnly = true)
    public StudentResponse getStudentById(Long id){
        Students students = findStudentOrThrow(id);
        return mapToResponse(students);
    }

    //UPDATE
    @Transactional
    public StudentResponse updateStudentById(Long id, StudentRequest request) {
        Students students = findStudentOrThrow(id);

        boolean emailUsedByAnotherStudent =
                studentRepository.existsByEmailAndIdNot(
                        request.email(),
                        id
                );

        if (emailUsedByAnotherStudent){
            throw new DuplicateResourceException(
                    "Another student is already using this email"
            );
        }

        students.setFirstName(request.firstName());
        students.setLastName(request.lastName());
        students.setEmail(request.email());

        Students updatedStudent = studentRepository.save(students);
        return mapToResponse(updatedStudent);
    }

    //DELETE
    @Transactional
    public void deleteStudentById(Long id){

        Students student = findStudentOrThrow(id);
        studentRepository.delete(student);
    }

    private Students findStudentOrThrow(Long id){
        return  studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Student not found with ID: " + id
        ));
    }

    private StudentResponse mapToResponse(Students students){
        return new StudentResponse(
                students.getId(),
                students.getFirstName(),
                students.getLastName(),
                students.getEmail()
        );
    }
}
