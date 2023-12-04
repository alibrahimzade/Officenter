package az.office.officenter.mapper;

import az.office.officenter.dao.entity.Cart;
import az.office.officenter.dao.entity.Customer;
import az.office.officenter.dto.CartDto;
import az.office.officenter.dto.CustomerDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface CustomerMapper {

    Customer fromDtoToModel(CustomerDto customerDto);

    CustomerDto fromModelToDto(Customer customer);

}