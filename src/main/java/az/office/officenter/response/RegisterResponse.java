package az.office.officenter.response;

import az.office.officenter.dao.entity.User;
import az.office.officenter.enums.UserRole;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterResponse {
    String name;
    String surname;
    String email;
    String password;
    UserRole role;
    public static RegisterResponse buildRegisterDto(User userEntity) {
        return RegisterResponse.builder()
                .name(userEntity.getName())
                .surname(userEntity.getSurname())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .role(userEntity.getRole())
                .build();
    }
}
