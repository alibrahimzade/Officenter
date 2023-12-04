package az.office.officenter.service;

import az.office.officenter.dao.entity.Category;
import az.office.officenter.dao.entity.Collect;
import az.office.officenter.dao.entity.User;
import az.office.officenter.dao.repository.CategoryRepository;
import az.office.officenter.dao.repository.CollectRepository;
import az.office.officenter.dao.repository.UserRepository;
import az.office.officenter.dto.CategoryDto;
import az.office.officenter.enums.UserRole;
import az.office.officenter.exception.CategoryNotFoundException;
import az.office.officenter.exception.CollectNotFoundException;
import az.office.officenter.exception.UserNotFoundException;
import az.office.officenter.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CollectRepository collectionRepository;
    private final UserRepository userRepository;
    private final CategoryMapper categoryMapper;

    public ResponseEntity<CategoryDto> createCategory(CategoryDto categoryRequest, Long userId) {
        log.info("Inside categoryRequest {}", categoryRequest);
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("user not found"));
        if (Objects.nonNull(user) && user.getRole().equals(UserRole.ADMIN)) {
            Collect collection = collectionRepository.findById(categoryRequest.getCollectionId()).orElseThrow(
                    () -> new CollectNotFoundException("collect not found"));
            Category category = categoryMapper.fromDtoToModel(categoryRequest);
            category.setCollect(collection);
            log.info("Inside createCategory {}", category);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(categoryMapper.fromModelToDto(categoryRepository.save(category)));
        } else
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    public ResponseEntity<CategoryDto> updateCategory(CategoryDto categoryRequest, Long userId) {
        log.info("Inside categoryRequest {}", categoryRequest);
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("user not found"));
        if (Objects.nonNull(user) && user.getRole().equals(UserRole.ADMIN)) {
            Category findCategory = categoryRepository
                    .findById(categoryRequest.getId()).orElseThrow(
                            () -> new CategoryNotFoundException("category not found"));
            if (Objects.nonNull(findCategory)) {
                Collect collection = collectionRepository.findById(categoryRequest.getCollectionId()).orElseThrow(
                        () -> new CollectNotFoundException("collection not found"));
                Category category = categoryMapper.fromDtoToModel(categoryRequest);
                category.setCollect(collection);
                log.info("Inside updateCategory {}", category);
                return ResponseEntity.status(HttpStatus.OK)
                        .body(categoryMapper.fromModelToDto(categoryRepository.save(category)));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        return ResponseEntity.status(HttpStatus.OK).body
                ((categoryMapper.fromListModelToDListDto(categoryRepository.findAll())));
    }

    public ResponseEntity<CategoryDto> getCategoryById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new CategoryNotFoundException("category not found"));

        if (Objects.nonNull(category)) {
            log.info("Inside getCategoryById {}", category);
            return ResponseEntity.status(HttpStatus.OK).body(categoryMapper.fromModelToDto(category));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }

    public void deleteById(Long userId, Long categoryId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("user not found"));
        if (Objects.nonNull(user) && user.getRole().equals(UserRole.ADMIN)) {
            Category category = categoryRepository
                    .findById(categoryId).orElseThrow(
                            () -> new CategoryNotFoundException("category not found"));
            categoryRepository.deleteById(categoryId);
            log.info("deleteById {}", category);
        } else ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    public void deleteAllCategories(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("user not found"));
        if (Objects.nonNull(user) && user.getRole().equals(UserRole.ADMIN)) {
            categoryRepository.deleteAll();
            log.info("deleteAllCategories successfully");
        } else ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}