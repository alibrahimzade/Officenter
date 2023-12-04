package az.office.officenter.mapper;

import az.office.officenter.dao.entity.Category;
import az.office.officenter.dto.CategoryDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {


    Category fromDtoToModel(CategoryDto categoryDto);

    CategoryDto fromModelToDto(Category category);

    List<CategoryDto> fromListModelToDListDto(List<Category> categories);


}