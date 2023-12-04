package az.office.officenter.service;

import az.office.officenter.dao.entity.Cart;
import az.office.officenter.dao.entity.CartItem;
import az.office.officenter.dao.entity.Customer;
import az.office.officenter.dao.entity.Product;
import az.office.officenter.dao.repository.CartItemRepository;
import az.office.officenter.dao.repository.CartRepository;
import az.office.officenter.dao.repository.CustomerRepository;
import az.office.officenter.dao.repository.ProductRepository;
import az.office.officenter.dto.CartDto;
import az.office.officenter.exception.CustomerNotFoundException;
import az.office.officenter.exception.ProductNotFoundException;
import az.office.officenter.mapper.CartMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final CartItemRepository cartItemRepository;
    private final CartMapper cartMapper;

    @Transactional
    public ResponseEntity<CartDto> addItemToCart(Long productId, Integer quantity, String email) {
        Customer customer = customerRepository.findByEmailEqualsIgnoreCase(email).orElseThrow(
                () -> new CustomerNotFoundException("customer not found"));
        Cart cart = customer.getCart();
        if (cart == null) {
            cart = new Cart();
        }
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new ProductNotFoundException("product not found"));
        Set<CartItem> cartItemList = cart.getCartItems();
        CartItem cartItem = find(cartItemList, productId);

        double unitPrice = product.getPrice();

        int itemQuantity = 0;

        if (cartItemList == null) {
            cartItemList = new HashSet<>();
            if (cartItem == null) {
                cartItem = new CartItem();
                cartItem.setProduct(product);
                cartItem.setCart(cart);
                cartItem.setQuantity(quantity);
                cartItem.setPrice(unitPrice);
                cartItemList.add(cartItem);
                log.info("Inside addItemToCart {}", cartItem);
                cartItemRepository.save(cartItem);
            } else {
                itemQuantity = cartItem.getQuantity() + quantity;
                cartItem.setQuantity(itemQuantity);
                cartItemRepository.save(cartItem);
            }
        } else {
            if (cartItem == null) {
                cartItem = new CartItem();
                cartItem.setProduct(product);
                cartItem.setCart(cart);
                cartItem.setQuantity(quantity);
                cartItem.setPrice(unitPrice);
                cartItemList.add(cartItem);
                cartItemRepository.save(cartItem);
            } else {
                itemQuantity = cartItem.getQuantity() + quantity;
                cartItem.setQuantity(itemQuantity);
                cartItemRepository.save(cartItem);
            }
        }
        cart.setCartItems(cartItemList);

        double totalPrice = totalPrice(cart.getCartItems());
        int totalItem = totalItem(cart.getCartItems());

        cart.setTotalPrice(totalPrice);
        cart.setTotalItems(totalItem);
        cart.setCustomer(customer);

        return ResponseEntity.status(HttpStatus.OK)
                .body(cartMapper.fromModelToDto(cartRepository.save(cart)));
    }

    @Transactional
    public ResponseEntity<CartDto> updateCart(Long productId, Integer quantity, String email) {
        Customer customer = customerRepository.findByEmailEqualsIgnoreCase(email)
                .orElseThrow(() -> new CustomerNotFoundException("customer not found"));
        if (Objects.nonNull(customer)) {
            Cart cart = customer.getCart();
            Set<CartItem> cartItemList = cart.getCartItems();

            CartItem cartItem = find(cartItemList, productId);
            int itemQuantity = quantity;

            cartItem.setQuantity(itemQuantity);
            cartItemRepository.save(cartItem);
            cart.setCartItems(cartItemList);
            int totalItem = totalItem(cartItemList);
            double totalPrice = totalPrice(cartItemList);
            cart.setTotalPrice(totalPrice);
            cart.setTotalItems(totalItem);
            log.info("Inside updateCart {}", cart);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(cartMapper.fromModelToDto(cartRepository.save(cart)));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Transactional
    public ResponseEntity<CartDto> getCartByCustomerEmail(String customerEmail) {
        Customer customer = customerRepository.findByEmailEqualsIgnoreCase(customerEmail).orElseThrow(
                () -> new CustomerNotFoundException("customer not found"));
        Cart cart = customer.getCart();
        log.info("Inside getCartByCustomerEmail {}", cart);
        return ResponseEntity.status(HttpStatus.OK).body(cartMapper.fromModelToDto(cart));
    }

    private CartItem find(Set<CartItem> cartItems, long productId) {
        if (cartItems == null) {
            return null;
        }
        CartItem cartItem = null;
        for (CartItem item : cartItems) {
            if (item.getProduct().getId() == productId) {
                cartItem = item;
            }
        }
        return cartItem;
    }

    private int totalItem(Set<CartItem> cartItemList) {
        int totalItem = 0;
        for (CartItem item : cartItemList) {
            totalItem += item.getQuantity();
        }
        return totalItem;
    }

    private double totalPrice(Set<CartItem> cartItemList) {
        double totalPrice = 0.0;
        for (CartItem item : cartItemList) {
            totalPrice += item.getPrice() * item.getQuantity();
        }
        return totalPrice;
    }

}