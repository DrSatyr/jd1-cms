package by.itacademy.pinchuk.cms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto implements Dto {

    private Integer id;
    private String username;
    private String email;
    private String phone;
    private String password;
    private boolean active;
    private String role;
    // TODO: 09.04.2019 Преобразовать даты в стриг используя DateFormatter
    private LocalDate registerDate;
    private LocalDate birthDate;
    private String name;
    private String surname;
}
