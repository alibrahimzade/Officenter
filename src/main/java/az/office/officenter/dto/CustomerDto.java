package az.office.officenter.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CustomerDto {

    private Long id;
    private String username;
    private String password;
    private String email;
    private String address;
    private String phone;

}