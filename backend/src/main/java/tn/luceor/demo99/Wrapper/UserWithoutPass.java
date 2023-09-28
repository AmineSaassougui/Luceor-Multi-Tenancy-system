package tn.luceor.demo99.Wrapper;


import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import tn.luceor.demo99.entities.Role;


import java.time.LocalDate;

@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserWithoutPass {
    Long id;
    String name;
    String email;
    String contactNumber;
    Role role;
    String status;

    public UserWithoutPass(Long id, String name, String email, String contactNumber, Role role, String status) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.contactNumber = contactNumber;
        this.role = role;
        this.status = status;
    }
}
