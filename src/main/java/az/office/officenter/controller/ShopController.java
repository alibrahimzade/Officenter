package az.office.officenter.controller;

import az.office.officenter.dto.ShopDto;
import az.office.officenter.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shops")
@RequiredArgsConstructor
public class ShopController {

    private final ShopService shopService;

    @PostMapping("/add/{userId}")
    public ResponseEntity<ShopDto> createShop(@RequestBody ShopDto shopRequest,
                                              @PathVariable(name = "userId") Long userId) {
        return shopService.createShop(shopRequest, userId);
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<ShopDto> updateShop(@RequestBody ShopDto shopRequest,
                                                   @PathVariable(name = "userId") Long userId) {
        return shopService.updateShop(shopRequest, userId);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ShopDto>> getAllShops() {
        return shopService.getAllShops();
    }

    @GetMapping("/get/{shopId}")
    public ResponseEntity<ShopDto> getShopById(@PathVariable(name = "shopId") Long shopId) {
        return shopService.getShopById(shopId);
    }

    @DeleteMapping("/{userId}/delete/{shopId}")
    public void deleteShop(@PathVariable(name = "userId") Long userId,
                           @PathVariable(name = "shopId") Long shopId) {
        shopService.deleteById(userId, shopId);
    }

    @DeleteMapping("/{userId}/deleteAll")
    public void deleteAllShops(@PathVariable(name = "userId") Long userId) {
        shopService.deleteAllShops(userId);
    }

}