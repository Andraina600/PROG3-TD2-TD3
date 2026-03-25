package school.hei.prog3td2td3.validator;

import school.hei.prog3td2td3.exception.BadRequestException;
import school.hei.prog3td2td3.model.Student;

import java.util.List;

public class StudentValidator {
    public void validate(List<Student> students) {
        for (Student s : students) {
            if (s.getFirstName() == null || s.getFirstName().isBlank()) {
                throw new BadRequestException("Le prénom est obligatoire");
            }
            if (s.getLastName() == null || s.getLastName().isBlank()) {
                throw new BadRequestException("Le nom est obligatoire");
            }
            if (s.getReference() == null || s.getReference().isBlank()) {
                throw new BadRequestException("La référence est obligatoire");
            }
        }
    }

}
