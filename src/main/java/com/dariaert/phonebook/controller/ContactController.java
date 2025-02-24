package com.dariaert.phonebook.controller;

import com.dariaert.phonebook.dto.ContactDTO;
import com.dariaert.phonebook.entity.ContactHistory;
import com.dariaert.phonebook.service.ContactService;
import com.dariaert.phonebook.service.CsvService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("/contacts")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;
    private final CsvService csvService;

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

    @GetMapping("/export")
    public ResponseEntity<byte[]> exportContactsToCsv() {
        String csvData = csvService.exportContacts();
        byte[] csvBytes = csvData.getBytes();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=contacts.csv")
                .contentType(MediaType.TEXT_PLAIN)
                .body(csvBytes);
    }

    @PostMapping("/import")
    public ResponseEntity<String> importContactsToCsv(@RequestParam("file") MultipartFile file) {
        if(file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty");
        }

        csvService.importContacts(file);
        return ResponseEntity.status(HttpStatus.CREATED).body("Imported contacts successfully");
    }

    @GetMapping("/{id}/history")
    public ResponseEntity<List<ContactHistory>> getContactHistory(@PathVariable Long id) {
        List<ContactHistory> history = contactService.getContactHistory(id);
        return ResponseEntity.ok(history);
    }

}
