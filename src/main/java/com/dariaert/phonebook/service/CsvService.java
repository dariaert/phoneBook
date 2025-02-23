package com.dariaert.phonebook.service;

import com.dariaert.phonebook.entity.Contact;
import com.dariaert.phonebook.repository.Contactrepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CsvService {

    private final Contactrepository contactrepository;

    public String exportContacts() {
        List<Contact> contacts = contactrepository.findAll();
        StringWriter writer = new StringWriter();
        try(CSVPrinter csvPrinter = new CSVPrinter(writer,
                CSVFormat.Builder.create()
                .setHeader("ID", "Name", "Phone", "Email", "Address")
                .build())) {
            for (Contact contact : contacts) {
                csvPrinter.printRecord(contact.getId(), contact.getName(), contact.getPhoneNumber(),
                        contact.getEmail(), contact.getAddress());
            }

            csvPrinter.flush();
        } catch(Exception e) {
            throw new RuntimeException("Ошибка при экспорте CSV: " + e.getMessage());
        }
        return writer.toString();
    }

}
