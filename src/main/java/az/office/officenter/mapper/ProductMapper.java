package az.office.officenter.mapper;

import az.office.officenter.dao.entity.Product;
import az.office.officenter.dto.ProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {


    Product fromDtoToModel(ProductDto productDto);

    ProductDto fromModelToDto(Product product);

    List<ProductDto> fromListModelToDListDto(List<Product> products);


}