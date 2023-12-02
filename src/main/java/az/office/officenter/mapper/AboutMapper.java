package az.office.officenter.mapper;

import az.office.officenter.dao.entity.About;
import az.office.officenter.dto.AboutDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface AboutMapper {

    About fromDtoToModel(AboutDto aboutRequest);
    AboutDto fromModelToDto(About about);
}
