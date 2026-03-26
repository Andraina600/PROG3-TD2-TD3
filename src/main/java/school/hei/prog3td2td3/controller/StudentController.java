package school.hei.prog3td2td3.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.hei.prog3td2td3.exception.BadRequestException;
import school.hei.prog3td2td3.model.Student;
import school.hei.prog3td2td3.service.StudentService;
import school.hei.prog3td2td3.validator.StudentValidator;

import java.util.List;

@RestController
public class StudentController {

    private final StudentService studentService;
    private final StudentValidator studentValidator;

    public StudentController(StudentService studentService, StudentValidator studentValidator) {
        this.studentService = studentService;
        this.studentValidator = studentValidator;
    }

    @GetMapping("/welcome")
    public ResponseEntity<String> welcome(@RequestParam(required = false) String name) {
        if (name == null || name.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Le paramètre 'name' est obligatoire");
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Welcome " + name);
    }

    @PostMapping("/students")
    public ResponseEntity<Object> addStudents(@RequestBody List<Student> newStudents) {
        try {
            studentValidator.validate(newStudents);
            studentService.addAll(newStudents);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(studentService.getAll());
        } catch (BadRequestException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/students")
    public ResponseEntity<?> getStudents(
            @RequestHeader(value = "Accept")
            String accept) {
        try {
            if (accept == null || accept.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("L'en-tête 'Accept' est obligatoire");
            }
            if (accept.equals("text/plain")) {
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(studentService.getAllAsString());
            }
            if (accept.equals("application/json")) {
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(studentService.getAll());
            }
            return ResponseEntity
                    .status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                    .body("Format non supporté");
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur interne du serveur");
        }
    }
}
