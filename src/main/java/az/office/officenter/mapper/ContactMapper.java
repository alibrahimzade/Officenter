package az.office.officenter.mapper;

import az.office.officenter.dao.entity.Contact;
import az.office.officenter.dto.ContactDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ContactMapper {
    Contact fromRequestToModel(ContactDto contactRequest);

    ContactDto fromModelToResponse(Contact contact);

    List<ContactDto> fromListModelToDListDto(List<Contact> contacts);
}