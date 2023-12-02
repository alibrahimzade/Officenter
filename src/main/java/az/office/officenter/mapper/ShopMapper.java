package az.office.officenter.mapper;

import az.office.officenter.dao.entity.Shop;
import az.office.officenter.dto.ShopDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ShopMapper {

    Shop fromDtoToModel(ShopDto shopDto);

    ShopDto fromModelToDto(Shop shop);

    List<ShopDto> fromListModelToDListDto(List<Shop> shops);

}
