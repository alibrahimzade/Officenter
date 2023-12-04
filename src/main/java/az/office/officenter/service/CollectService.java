package az.office.officenter.service;

import az.office.officenter.dao.entity.Collect;
import az.office.officenter.dao.entity.User;
import az.office.officenter.dao.repository.CollectRepository;
import az.office.officenter.dao.repository.UserRepository;
import az.office.officenter.dto.CollectDto;
import az.office.officenter.enums.UserRole;
import az.office.officenter.exception.CollectNotFoundException;
import az.office.officenter.exception.UserNotFoundException;
import az.office.officenter.mapper.CollectMapper;
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
public class CollectService {

    private final CollectRepository collectRepository;
    private final UserRepository userRepository;
    private final CollectMapper collectMapper;

    public ResponseEntity<CollectDto> createCollection(CollectDto collectionRequest, Long userId) {
        log.info("Inside collectionRequest {}", collectionRequest);
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User not found"));
        if (Objects.nonNull(user) && user.getRole().equals(UserRole.ADMIN)) {
            Collect collect = collectMapper.fromDtoToModel(collectionRequest);
            log.info("Inside createCollection {}", collect);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(collectMapper.fromModelToDto(collectRepository.save(collect)));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    public ResponseEntity<CollectDto> updateCollection(CollectDto collectionRequest, Long userId) {
        log.info("Inside collectionRequest {}", collectionRequest);
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("user not found"));
        if (Objects.nonNull(user) && user.getRole().equals(UserRole.ADMIN)) {
            Collect findCollect = collectRepository.findById(collectionRequest.getId()).orElseThrow(
                    () -> new CollectNotFoundException("Collection not found"));
            if (Objects.nonNull(findCollect)) {
                log.info("Inside updateCollection {}", collectionRequest);
                return ResponseEntity.status(HttpStatus.OK)
                        .body(collectMapper.fromModelToDto
                                (collectRepository.save(collectMapper.fromDtoToModel(collectionRequest))));
            } else
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    public ResponseEntity<List<CollectDto>> getAllCollection() {
        return ResponseEntity.status(HttpStatus.OK).body(collectMapper.fromListModelToDListDto(collectRepository.findAll()));
    }

    public ResponseEntity<CollectDto> getCollectionById(Long collectionId) {
        Collect collect = collectRepository.findById(collectionId).orElseThrow(
                () -> new CollectNotFoundException("Collection not found"));
        log.info("Inside getCollectionById {}", collect);
        return ResponseEntity.status(HttpStatus.OK).body(collectMapper.fromModelToDto(collect));
    }

    public void deleteById(Long userId, Long collectionId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("user not found"));
        if (Objects.nonNull(user) && user.getRole().equals(UserRole.ADMIN)) {
            Collect collect = collectRepository.findById(collectionId).orElseThrow(
                    () -> new CollectNotFoundException("collect not found"));
            collectRepository.deleteById(collectionId);
            log.info("deleteCollection {}", collect);
        } else ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    public void deleteAllCollections(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("user not found"));
        if (Objects.nonNull(user) && user.getRole().equals(UserRole.ADMIN)) {
            collectRepository.deleteAll();
            log.info("deleteAllCollections successfully");
        } else ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}