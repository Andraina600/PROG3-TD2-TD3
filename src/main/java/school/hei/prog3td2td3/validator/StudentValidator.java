package school.hei.prog3td2td3.validator;

import org.springframework.stereotype.Component;
import school.hei.prog3td2td3.exception.BadRequestException;
import school.hei.prog3td2td3.model.Student;

import java.util.List;

@Component
public class StudentValidator {
    public void validate(List<Student> students) {
        StringBuilder message = new StringBuilder();
        for (Student s : students) {
            if (s.getFirstName() == null || s.getFirstName().isBlank()) {
                message.append("Le prenom est obligatoire. ");
            }
            if (s.getLastName() == null || s.getLastName().isBlank()) {
                message.append("Le nom est obligatoire. ");
            }
            if (s.getReference() == null || s.getReference().isBlank()) {
                message.append("La référence est obligatoire.");
            }
        }
        if(!message.isEmpty()){
            throw new BadRequestException(message.toString());
        }
    }

}
