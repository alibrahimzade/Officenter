package az.office.officenter.mapper;

import az.office.officenter.dao.entity.BlogPost;
import az.office.officenter.dao.entity.Contact;
import az.office.officenter.dto.BlogPostDto;
import az.office.officenter.dto.ContactDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface BlogPostMapper {

    BlogPost fromDtoToModel(BlogPostDto blogPostRequest);

    BlogPostDto fromModelToDto(BlogPost blogPost);

    List<BlogPostDto> fromListModelToDListDto(List<BlogPost> blogPosts);

}