package school.hei.prog3td2td3.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.hei.prog3td2td3.model.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class StudentController {
    private static final Logger log = LoggerFactory.getLogger(StudentController.class);
    private final List<Student> students = new ArrayList<>();

    @GetMapping("/welcome")
    public ResponseEntity<String> welcome(@RequestParam(required = false) String name) {
        if(name == null ||name.isEmpty()){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Le paramètre 'name' est obligatoire");
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Welcome " + name);
    }

    @PostMapping("/students")
    public ResponseEntity<String> addStudents(@RequestBody List<Student> newStudents) {

       try{
           students.addAll(newStudents);

           String name = students.stream()
                   .map(s -> s.getFirstName() + " " + s.getLastName())
                   .collect(Collectors.joining(", "));

           return ResponseEntity
                   .status(HttpStatus.CREATED)
                   .body(name);
       }catch(Exception e){
           return ResponseEntity
                   .status(HttpStatus.INTERNAL_SERVER_ERROR)
                   .body("Erreur interne du serveur");
       }
    }

    @GetMapping("/students")
    public ResponseEntity<?> getStudents(
            @RequestHeader(value = "Accept", defaultValue = "text/plain") String accept) {

        try{
            if(accept == null || accept.isEmpty()){
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("L'en-tête 'Accept' est obligatoire");
            }

            if (accept.equals("text/plain")) {
                String names = students.stream()
                        .map(s -> s.getFirstName() + " " + s.getLastName())
                        .collect(Collectors.joining(", "));
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(names);
            }

            if(accept.equals("application/json")) {
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(students);
            }

            return ResponseEntity
                    .status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                    .body("Format non supporté");

        }catch(Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur interne du serveur");
        }


    }
}
