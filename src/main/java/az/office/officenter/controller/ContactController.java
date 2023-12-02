package az.office.officenter.controller;

import az.office.officenter.dto.ContactDto;
import az.office.officenter.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contacts")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;

    @PostMapping("/add")
    public ResponseEntity<ContactDto> addContact(@RequestBody ContactDto contactRequest) {
        return contactService.addContact(contactRequest);
    }

    @GetMapping("/getAll/{userId}")
    public ResponseEntity<List<ContactDto>> getAllContacts(@PathVariable(name = "userId") Long userId) {
        return contactService.getAllContacts(userId);
    }

    @DeleteMapping("/{userId}/delete/{contactId}")
    public void deleteContactById(@PathVariable(name = "userId") Long userId,
                                  @PathVariable(name = "contactId") Long contactId) {
        contactService.deleteContactById(userId, contactId);
    }
}