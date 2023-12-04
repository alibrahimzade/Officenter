package az.office.officenter.service;

import az.office.officenter.dao.entity.BlogPost;
import az.office.officenter.dao.entity.User;
import az.office.officenter.dao.repository.BlogPostRepository;
import az.office.officenter.dao.repository.UserRepository;
import az.office.officenter.dto.BlogPostDto;
import az.office.officenter.enums.UserRole;
import az.office.officenter.exception.BlogPostNotFoundException;
import az.office.officenter.exception.UserNotFoundException;
import az.office.officenter.mapper.BlogPostMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class BlogPostService {

    private final BlogPostRepository blogPostRepository;
    private final UserRepository userRepository;
    private final BlogPostMapper blogPostMapper;

    public ResponseEntity<BlogPostDto> createBlog(BlogPostDto blogPostRequest, Long userId) {
        log.info("Inside blogPostRequest {}", blogPostRequest);
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("user not found"));
        if (Objects.nonNull(user) && user.getRole().equals(UserRole.ADMIN)) {
            BlogPost blogPost = blogPostMapper.fromDtoToModel(blogPostRequest);
            blogPost.setCreationDate(LocalDateTime.now());
            log.info("Inside createBlog {}", blogPost);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(blogPostMapper.fromModelToDto(blogPostRepository.save(blogPost)));
        } else
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    public ResponseEntity<BlogPostDto> updateBlog(BlogPostDto blogPostRequest, Long userId) {
        log.info("Inside blogPostRequest {}", blogPostRequest);
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("user not found"));
        if (Objects.nonNull(user) && user.getRole().equals(UserRole.ADMIN)) {
            BlogPost blogPost = blogPostRepository.findById(blogPostRequest.getId()).orElseThrow(
                    () -> new BlogPostNotFoundException("BlogPost not found"));
            if (Objects.nonNull(blogPost)) {
                BlogPost updatedBlog = blogPostMapper.fromDtoToModel(blogPostRequest);
                updatedBlog.setCreationDate(LocalDateTime.now());
                log.info("Inside updateBlog {}", updatedBlog);
                return ResponseEntity.status(HttpStatus.OK)
                        .body(blogPostMapper.fromModelToDto(blogPostRepository.save(updatedBlog)));
            } else
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    public ResponseEntity<List<BlogPostDto>> getAllBlogs() {
        return ResponseEntity.status(HttpStatus.OK).body(blogPostMapper.fromListModelToDListDto(blogPostRepository.findAll()));
    }

    public ResponseEntity<BlogPostDto> getBlogById(Long blogId) {
        BlogPost blogPost = blogPostRepository.findById(blogId).orElseThrow(
                () -> new BlogPostNotFoundException("BlogPOst not found"));
        if (Objects.nonNull(blogPost)) {
            log.info("Inside getBlogById {}", blogPost);
            return ResponseEntity.status(HttpStatus.OK).body(blogPostMapper.fromModelToDto(blogPost));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }

    public void deleteBlogPost(Long userId, Long blogPostId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("user not found"));
        if (Objects.nonNull(user) && user.getRole().equals(UserRole.ADMIN)) {
            BlogPost blogPost = blogPostRepository.findById(blogPostId).orElseThrow(
                    () -> new BlogPostNotFoundException("BlogPost not found"));
            blogPostRepository.deleteById(blogPostId);
            log.info("deleteBlogPost {}", blogPost);
        } else ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    }

}