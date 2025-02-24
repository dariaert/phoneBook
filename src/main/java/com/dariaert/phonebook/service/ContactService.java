package com.dariaert.phonebook.service;

import com.dariaert.phonebook.dto.ContactDTO;
import com.dariaert.phonebook.entity.Contact;
import com.dariaert.phonebook.entity.ContactHistory;
import com.dariaert.phonebook.mapper.ContactMapper;
import com.dariaert.phonebook.repository.ContactHistoryRepository;
import com.dariaert.phonebook.repository.ContactRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ContactService {

    private final ContactRepository contactRepository;
    private final ContactHistoryRepository contactHistoryRepository;

    public List<ContactDTO> getAllContacts() {
        return contactRepository.findAll()
                .stream()
                .map(ContactMapper::toDto)
                .toList();
    }

    public ContactDTO getContactById(Long id) {
        return contactRepository. findById(id)
                .map(ContactMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Contact not found"));
    }

    public ContactDTO createContact(ContactDTO contactDTO) {
        Contact contact = ContactMapper.toEntity(contactDTO);
        return ContactMapper.toDto(contactRepository.save(contact));
    }

    public ContactDTO updateContact(Long id, ContactDTO contactDTO) {
        Contact existingContact = contactRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contact not found"));

        checkAndSaveHistory(id, "name", existingContact.getName(), contactDTO.name());
        checkAndSaveHistory(id, "phoneNumber", existingContact.getPhoneNumber(), contactDTO.phoneNumber());
        checkAndSaveHistory(id, "email", existingContact.getEmail(), contactDTO.email());
        checkAndSaveHistory(id, "address", existingContact.getAddress(), contactDTO.address());

        existingContact.setName(contactDTO.name());
        existingContact.setPhoneNumber(contactDTO.phoneNumber());
        existingContact.setEmail(contactDTO.email());
        existingContact.setAddress(contactDTO.address());

        return ContactMapper.toDto(contactRepository.save(existingContact));
    }

    public void deleteContact(Long id) {
        contactRepository.deleteById(id);
    }

    public List<ContactDTO> filterByName(String name) {
        return contactRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(ContactMapper::toDto)
                .toList();
    }

    public List<ContactDTO> filterByPhoneNumber(String phoneNumber) {
        return contactRepository.findByPhoneNumberContaining(phoneNumber)
                .stream()
                .map(ContactMapper::toDto)
                .toList();
    }

    private void checkAndSaveHistory(Long contactId, String fieldName, String oldValue, String newValue){
        if (oldValue != null && !oldValue.equals(newValue)){
            ContactHistory contactHistory = ContactHistory.builder()
                    .contactId(contactId)
                    .fieldName(fieldName)
                    .oldValue(oldValue)
                    .newValue(newValue)
                    .changeTime(LocalDateTime.now())
                    .build();
            contactHistoryRepository.save(contactHistory);
        }
    }

    public List<ContactHistory> getContactHistory(Long contactId) {
        return contactHistoryRepository.findByContactId(contactId);
    }

}
