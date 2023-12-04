package az.office.officenter.mapper;

import az.office.officenter.dao.entity.Collect;
import az.office.officenter.dto.CollectDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CollectMapper {


    Collect fromDtoToModel(CollectDto collectionRequest);

    CollectDto fromModelToDto(Collect collect);

    List<CollectDto> fromListModelToDListDto(List<Collect> contacts);


}