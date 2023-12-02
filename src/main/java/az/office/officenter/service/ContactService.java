package az.office.officenter.service;

import az.office.officenter.dao.entity.Contact;
import az.office.officenter.dao.entity.User;
import az.office.officenter.dao.repository.ContactRepository;
import az.office.officenter.dao.repository.UserRepository;
import az.office.officenter.dto.ContactDto;
import az.office.officenter.exception.ContactNotFoundException;
import az.office.officenter.exception.UserNotFoundException;
import az.office.officenter.mapper.ContactMapper;
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
public class ContactService {

    private final ContactRepository contactRepository;
    private final UserRepository userRepository;
    private final ContactMapper contactMapper;

    public ResponseEntity<ContactDto> addContact(ContactDto contactRequest) {
        log.info("Inside addContact {}", contactRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(contactMapper.fromModelToResponse
                        (contactRepository.save(contactMapper.fromRequestToModel(contactRequest))));
    }

    public ResponseEntity<List<ContactDto>> getAllContacts(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        if (Objects.nonNull(user)) {
            log.info("Inside getAllContacts {}", contactRepository.findAll());
            return ResponseEntity.status(HttpStatus.OK).body(contactMapper.fromListModelToDListDto(contactRepository.findAll()));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    public void deleteContactById(Long userId, Long contactId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("user not found"));
        if (Objects.nonNull(user)) {
            Contact contact = contactRepository.findById(contactId).orElseThrow(
                    () -> new ContactNotFoundException("user not found"));
            contactRepository.deleteById(contact.getId());
        }
    }

}