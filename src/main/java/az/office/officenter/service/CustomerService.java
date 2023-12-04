package az.office.officenter.service;


import az.office.officenter.dao.entity.Customer;
import az.office.officenter.exception.IncorrectPasswordException;
import az.office.officenter.request.ChangePasswordRequest;
import az.office.officenter.dao.repository.CustomerRepository;
import az.office.officenter.dto.CustomerDto;
import az.office.officenter.exception.CustomerNotFoundException;
import az.office.officenter.mapper.CustomerMapper;
import az.office.officenter.request.AuthenticationRequest;
import az.office.officenter.request.ForgotPasswordRequest;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final EmailService emailService;

    public ResponseEntity<?> signup(CustomerDto customerRequest) {
        if (validationSignUp(customerRequest)) {
            Optional<Customer> customer = customerRepository.findByEmailEqualsIgnoreCase(customerRequest.getEmail());
            if (customer.isEmpty()) {
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(customerMapper.fromModelToDto
                                (customerRepository.save(customerMapper.fromDtoToModel(customerRequest))));
            } else
                log.error("customerRequest {}", customerRequest);
            return ResponseEntity.status(BAD_REQUEST).body("User already exist");
        }
        return ResponseEntity.status(BAD_REQUEST).build();
    }

    public ResponseEntity<CustomerDto> updateCustomer(CustomerDto customerRequest) {
        Customer customer = customerRepository.findById(customerRequest.getId())
                .orElseThrow(() -> new CustomerNotFoundException("customer not found"));
        if (Objects.nonNull(customer)) {
            return ResponseEntity.status(OK)
                    .body(customerMapper.fromModelToDto
                            (customerRepository.save(customerMapper.fromDtoToModel(customerRequest))));
        }
        return ResponseEntity.status(BAD_REQUEST).build();
    }

    public ResponseEntity<CustomerDto> changePassword(ChangePasswordRequest changePasswordRequest, Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(
                () -> new CustomerNotFoundException("customer not exist in this "+customerId+" id"));
        if (!customer.getPassword().equals(changePasswordRequest.getOldPassword())) {
            throw new IncorrectPasswordException(BAD_REQUEST.name());
        }
        if (!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmPassword())) {
            throw new IncorrectPasswordException(BAD_REQUEST.name());
        }
        customer.setPassword(changePasswordRequest.getNewPassword());
        return ResponseEntity.status(OK)
                .body(customerMapper.fromModelToDto(customerRepository.save(customer)));
    }

    public ResponseEntity<String> forgotPassword(ForgotPasswordRequest forgotPasswordRequest) throws MessagingException {
        Optional<Customer> optionalCustomer = customerRepository.findByEmailEqualsIgnoreCase(forgotPasswordRequest.getEmail());
        if (optionalCustomer.isPresent()) {
            emailService.forgetMail(optionalCustomer.get().getEmail(),"Credentials by OfiCenter", optionalCustomer.get().getPassword());
            return ResponseEntity.status(OK).body("CHECK_EMAIL");
        } else
            return ResponseEntity.status(BAD_REQUEST).body("User not found");
    }

    private boolean validationSignUp(CustomerDto customerRequest) {
        return customerRequest.getUsername() != null && customerRequest.getEmail() != null
                && customerRequest.getPhone() != null && customerRequest.getPassword() != null;
    }

}