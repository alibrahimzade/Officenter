package az.office.officenter.service;

import az.office.officenter.dao.entity.Shop;
import az.office.officenter.dao.entity.User;
import az.office.officenter.dao.repository.ShopRepository;
import az.office.officenter.dao.repository.UserRepository;
import az.office.officenter.dto.ShopDto;
import az.office.officenter.enums.UserRole;
import az.office.officenter.exception.ShopNotFoundException;
import az.office.officenter.exception.UserNotFoundException;
import az.office.officenter.mapper.ShopMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static org.springframework.http.HttpStatus.OK;

@Service
@Slf4j
@RequiredArgsConstructor
public class ShopService {

    private final ShopRepository shopRepository;
    private final UserRepository userRepository;
    private final ShopMapper shopMapper;

    public ResponseEntity<ShopDto> createShop(ShopDto shopRequest, Long userId) {
        log.info("Inside shopRequest {}", shopRequest);
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User Not Found"));
        if (Objects.nonNull(user) && user.getRole().equals(UserRole.ADMIN)) {
            Shop shop = shopMapper.fromDtoToModel(shopRequest);
            log.info("Inside createShop {}", shop);
            return ResponseEntity.status(OK)
                    .body(shopMapper.fromModelToDto(shopRepository.save(shop)));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    public ResponseEntity<ShopDto> updateShop(ShopDto shopRequest, Long userId) {
        log.info("Inside shopRequest {}", shopRequest);
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("user not found"));
        if (Objects.nonNull(user) && user.getRole().equals(UserRole.ADMIN)) {
            Shop findShop = shopRepository.findById(shopRequest.getId()).orElseThrow(
                    () -> new ShopNotFoundException("Shop not found"));
            if (Objects.nonNull(findShop)) {
                log.info("Inside updateShop {}", shopMapper.fromDtoToModel(shopRequest));
                return ResponseEntity.status(OK).body(shopMapper.fromModelToDto
                        (shopRepository.save(shopMapper.fromDtoToModel(shopRequest))));
            } else
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    public ResponseEntity<List<ShopDto>> getAllShops() {
        List<Shop> all = shopRepository.findAll();
        return ResponseEntity.status(OK).body(shopMapper.fromListModelToDListDto(all));
    }

    public ResponseEntity<ShopDto> getShopById(Long shopId) {
        Shop shop = shopRepository.findById(shopId).orElseThrow(
                () -> new ShopNotFoundException("Shop not found"));
        log.info("Inside getShopById {}", shopMapper.fromModelToDto(shop));
        return ResponseEntity.status(OK).body(shopMapper.fromModelToDto(shop));
    }

    public void deleteById(Long userId, Long shopId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("Shop not found"));
        if (Objects.nonNull(user) && user.getRole().equals(UserRole.ADMIN)) {
            Shop shop = shopRepository.findById(shopId).orElseThrow(
                    () -> new ShopNotFoundException("Shop not found"));
            shopRepository.deleteById(shopId);
            log.info("deleteShop {}", shop);
        } else ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    public void deleteAllShops(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("Shop not found"));
        if (Objects.nonNull(user) && user.getRole().equals(UserRole.ADMIN)) {
            shopRepository.deleteAll();
            log.info("deleteAllShops successfully");
        } else ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}