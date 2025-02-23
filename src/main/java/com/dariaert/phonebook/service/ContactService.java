package com.dariaert.phonebook.service;

import com.dariaert.phonebook.dto.ContactDTO;
import com.dariaert.phonebook.entity.Contact;
import com.dariaert.phonebook.mapper.ContactMapper;
import com.dariaert.phonebook.repository.Contactrepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ContactService {

    private final Contactrepository contactrepository;

    public List<ContactDTO> getAllContacts() {
        return contactrepository.findAll()
                .stream()
                .map(ContactMapper::toDto)
                .toList();
    }

    public ContactDTO getContactById(Long id) {
        return contactrepository. findById(id)
                .map(ContactMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Contact not found"));
    }

    public ContactDTO createContact(ContactDTO contactDTO) {
        Contact contact = ContactMapper.toEntity(contactDTO);
        return ContactMapper.toDto(contactrepository.save(contact));
    }

    public ContactDTO updateContact(Long id, ContactDTO contactDTO) {
        Contact existingContact = contactrepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contact not found"));

        existingContact.setName(contactDTO.name());
        existingContact.setPhoneNumber(contactDTO.phoneNumber());
        existingContact.setEmail(contactDTO.email());
        existingContact.setAddress(contactDTO.address());

        return ContactMapper.toDto(contactrepository.save(existingContact));
    }

    public void deleteContact(Long id) {
        contactrepository.deleteById(id);
    }

    public List<ContactDTO> filterByName(String name) {
        return contactrepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(ContactMapper::toDto)
                .toList();
    }

    public List<ContactDTO> filterByPhoneNumber(String phoneNumber) {
        return contactrepository.findByPhoneNumberContaining(phoneNumber)
                .stream()
                .map(ContactMapper::toDto)
                .toList();
    }

}
