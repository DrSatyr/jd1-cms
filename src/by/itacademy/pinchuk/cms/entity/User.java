package by.itacademy.pinchuk.cms.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements Entity {

    private Integer id;
    private String username;
    private String email;
    private String phone;
    private String password;
    private Boolean active;
    private String role;
    private LocalDate registerDate;
    private LocalDate birthDate;
    private String name;
    private String surname;
}
