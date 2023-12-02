package az.office.officenter.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ContactDto {
    private Long id;
    private String email;
    private String content;
}