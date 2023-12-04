package az.office.officenter.request;

import az.office.officenter.enums.StatusRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateStatusRequest {
    Long id;
    StatusRole currentRole;
    StatusRole newRole;
}
