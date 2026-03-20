package school.hei.prog3td2td3.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.hei.prog3td2td3.model.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class StudentController {
    private final List<Student> students = new ArrayList<>();

    @GetMapping("/welcome")
    public String welcome(@RequestParam String name) {
        return "Welcome " + name;
    }

    @PostMapping("/students")
    public String addStudents(@RequestBody List<Student> newStudents) {

        students.addAll(newStudents);

        return students.stream()
                .map(s -> s.getFirstName() + " " + s.getLastName())
                .collect(Collectors.joining(", "));
    }

    @GetMapping("/students")
    public ResponseEntity<String> getStudents(
            @RequestHeader(value = "Accept", defaultValue = "text/plain") String accept) {

        if (accept.equals("text/plain")) {
            String names = students.stream()
                    .map(s -> s.getFirstName() + " " + s.getLastName())
                    .collect(Collectors.joining(", "));
            return ResponseEntity.ok(names);
        }

        return ResponseEntity
                .status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                .body("Format non supporté");
    }
}
