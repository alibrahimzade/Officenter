package az.office.officenter.mapper;

import az.office.officenter.dao.entity.Cart;
import az.office.officenter.dto.CartDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface CartMapper {

    Cart fromDtoToModel(CartDto cartRequest);

    CartDto fromModelToDto(Cart cart);

}