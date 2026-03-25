package school.hei.prog3td2td3.service;

import org.springframework.stereotype.Service;
import school.hei.prog3td2td3.model.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final List<Student> students = new ArrayList<>();

    public void addAll(List<Student> newStudents) {
        students.addAll(newStudents);
    }

    public List<Student> getAll() {
        return students;
    }

    public String getAllAsString() {
        return students.stream()
                .map(s -> s.getFirstName() + " " + s.getLastName())
                .collect(Collectors.joining(", "));
    }
}