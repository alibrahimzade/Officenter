package az.office.officenter.dto;

import az.office.officenter.enums.UserRole;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {
    Long id;
    String name;
    String surname;
    String email;
    String password;
    @Enumerated(EnumType.STRING)
    UserRole role;
}
