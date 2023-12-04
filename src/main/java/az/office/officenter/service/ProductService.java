package az.office.officenter.service;

import az.office.officenter.dao.entity.Category;
import az.office.officenter.dao.entity.Product;
import az.office.officenter.dao.entity.User;
import az.office.officenter.dao.repository.CategoryRepository;
import az.office.officenter.dao.repository.ProductRepository;
import az.office.officenter.dao.repository.UserRepository;
import az.office.officenter.dto.ProductDto;
import az.office.officenter.enums.StatusRole;
import az.office.officenter.enums.UserRole;
import az.office.officenter.exception.CategoryNotFoundException;
import az.office.officenter.exception.ProductNotFoundException;
import az.office.officenter.exception.UserNotFoundException;
import az.office.officenter.mapper.ProductMapper;
import az.office.officenter.request.UpdateStatusRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    public ResponseEntity<ProductDto> createProduct(ProductDto productRequest, Long userId) {
        log.info("Inside productRequest {}", productRequest);
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("user not found"));
        if (Objects.nonNull(user) && user.getRole().equals(UserRole.ADMIN)) {
            Category category = categoryRepository.findById(productRequest.getCategoryId()).orElseThrow(
                    () -> new CategoryNotFoundException("category not found"));
            Product product = productMapper.fromDtoToModel(productRequest);
            product.setCategory(category);
            product.setStatus(StatusRole.FALSE);
            log.info("Inside createProduct {}", product);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(productMapper.fromModelToDto(productRepository.save(product)));
        } else
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    }

    public ResponseEntity<ProductDto> updateProduct(ProductDto productRequest, Long userId) {
        log.info("Inside productRequest {}", productRequest);
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("user not found"));
        if (Objects.nonNull(user) && user.getRole().equals(UserRole.ADMIN)) {
            Product product = productRepository.findById(productRequest.getId())
                    .orElseThrow(() -> new ProductNotFoundException("product not found"));
            if (Objects.nonNull(product)) {
                Category category = categoryRepository.findById(productRequest.getCategoryId())
                        .orElseThrow(() -> new CategoryNotFoundException("category not found"));
                Product updateProduct = productMapper.fromDtoToModel(productRequest);
                updateProduct.setCategory(category);
                updateProduct.setStatus(StatusRole.FALSE);
                log.info("Inside updateProduct {}", updateProduct);
                return ResponseEntity.status(HttpStatus.OK)
                        .body(productMapper.fromModelToDto
                                (productRepository.save(updateProduct)));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    public ResponseEntity<ProductDto> getProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("product ont found"));
        if (Objects.nonNull(product)) {
            log.info("Inside getProductById {}", product);
            return ResponseEntity.status(HttpStatus.OK).body(productMapper.fromModelToDto(product));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    public ResponseEntity<List<ProductDto>> getAllProducts() {
        log.info("Inside getAllProducts {}", productRepository.findAll());
        return ResponseEntity.status(HttpStatus.OK).body(productMapper.fromListModelToDListDto(productRepository.findAll()));
    }

    public ResponseEntity<List<ProductDto>> getAllProductsStatusTrue() {
        log.info("Inside getAllProductsStatusTrue {}", productRepository.findAllByStatusTrue());
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.findAllByStatusTrue());
    }


    public void deleteProductById(Long userId, Long productId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("user not found"));
        if (Objects.nonNull(user) && user.getRole().equals(UserRole.ADMIN)) {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new ProductNotFoundException("product not found"));
            productRepository.deleteById(productId);
            log.info("deleteProductById {}", product);
        } else ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }


    public ResponseEntity<ProductDto> updateStatus(Long userId, UpdateStatusRequest statusRequest) {
        log.info("Inside statusRequest {}", statusRequest);
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("user not found"));
        if (Objects.nonNull(user) && user.getRole().equals(UserRole.ADMIN)) {
            Product product = productRepository.findById(statusRequest.getId())
                    .orElseThrow(() -> new ProductNotFoundException("product not found"));
            product.setStatus(statusRequest.getNewRole());
            log.info("Inside updateStatus {}", product);
            productRepository.save(product);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(productMapper.fromModelToDto(productRepository.save(product)));

        } else
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    }

}