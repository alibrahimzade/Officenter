package az.office.officenter.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopDto {
    private Long id;
    private String name;
    private String address;
    private String email;
    private String workTime;
    private String phone;
}
