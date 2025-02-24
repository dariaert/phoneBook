package com.dariaert.phonebook.repository;

import com.dariaert.phonebook.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long>, JpaSpecificationExecutor<Contact> {

    List<Contact> findByNameContainingIgnoreCase(String name);
    List<Contact> findByPhoneNumberContaining(String phoneNumber);

}
