package com.dariaert.phonebook.repository;

import com.dariaert.phonebook.entity.ContactHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactHistoryRepository extends JpaRepository<ContactHistory, Long> {

    List<ContactHistory> findByContactId(Long contactId);

}
