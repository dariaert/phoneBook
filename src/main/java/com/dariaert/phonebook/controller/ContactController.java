package com.dariaert.phonebook.controller;

import com.dariaert.phonebook.dto.ContactDTO;
import com.dariaert.phonebook.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contacts")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;

    @GetMapping
    public ResponseEntity<List<ContactDTO>> getAllContacts(){
        return ResponseEntity.ok(contactService.getAllContacts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactDTO> getContactById(@PathVariable Long id) {
        ContactDTO contactDTO = contactService.getContactById(id);
        return contactDTO != null ? ResponseEntity.ok(contactDTO) : ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    public List<ContactDTO> searchContacts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String phone) {
        if (name != null) {
            return contactService.filterByName(name);
        }
        if (phone != null) {
            return contactService.filterByPhoneNumber(phone);
        }
        return contactService.getAllContacts();
    }

    @PostMapping
    public ResponseEntity<ContactDTO> createContact(@RequestBody ContactDTO contactDTO) {
        ContactDTO createdContact = contactService.createContact(contactDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdContact);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContactDTO> updateContact(
            @PathVariable Long id, @RequestBody ContactDTO contactDTO) {
        ContactDTO updatedContact = contactService.updateContact(id, contactDTO);
        return updatedContact != null ? ResponseEntity.ok(updatedContact) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
        contactService.deleteContact(id);
        return ResponseEntity.noContent().build();
    }

}
