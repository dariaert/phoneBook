package com.dariaert.phonebook.mapper;

import com.dariaert.phonebook.dto.ContactDTO;
import com.dariaert.phonebook.entity.Contact;
import org.springframework.stereotype.Component;

public class ContactMapper {

    public static ContactDTO toDto(Contact contact) {
        if (contact == null) {
            return null;
        }
        return ContactDTO.builder()
                .id(contact.getId())
                .name(contact.getName())
                .phoneNumber(contact.getPhoneNumber())
                .email(contact.getEmail())
                .address(contact.getAddress())
                .build();
    }

    public static Contact toEntity(ContactDTO contactDTO) {
        if (contactDTO == null) {
            return null;
        }
        return Contact.builder()
                .id(contactDTO.id())
                .name(contactDTO.name())
                .phoneNumber(contactDTO.phoneNumber())
                .email(contactDTO.email())
                .address(contactDTO.address())
                .build();
    }

}
