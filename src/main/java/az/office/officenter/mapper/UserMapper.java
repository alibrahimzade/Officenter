package az.office.officenter.mapper;

import az.office.officenter.dao.entity.User;
import az.office.officenter.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    User fromDtoToEntity(UserDto userDto);

    UserDto fromEntityToDto(User user);
}
