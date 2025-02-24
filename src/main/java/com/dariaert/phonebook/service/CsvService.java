package com.dariaert.phonebook.service;

import com.dariaert.phonebook.entity.Contact;
import com.dariaert.phonebook.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CsvService {

    private final ContactRepository contactrepository;

    public String exportContacts() {
        List<Contact> contacts = contactrepository.findAll();
        StringWriter writer = new StringWriter();
        try(CSVPrinter csvPrinter = new CSVPrinter(writer,
                CSVFormat.Builder.create()
                .setHeader("ID", "Name", "PhoneNumber", "Email", "Address")
                .build())) {
            for (Contact contact : contacts) {
                csvPrinter.printRecord(contact.getId(), contact.getName(), contact.getPhoneNumber(),
                        contact.getEmail(), contact.getAddress());
            }

            csvPrinter.flush();
        } catch(IOException e) {
            throw new RuntimeException("Ошибка при экспорте CSV: " + e.getMessage());
        }
        return writer.toString();
    }

    public void importContacts(MultipartFile file){
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.Builder.create()
                     .setHeader()
                     .setSkipHeaderRecord(true)
                     .setIgnoreHeaderCase(true)
                     .setTrim(true)
                     .build())){

            List<Contact> contacts = csvParser.getRecords().stream()
                    .map(record -> Contact.builder()
                            .name(record.get("Name"))
                            .phoneNumber(record.get("PhoneNumber"))
                            .email(record.get("Email"))
                            .address(record.get("Address"))
                            .build())
                    .collect(Collectors.toList());

            contactrepository.saveAll(contacts);

        } catch (IOException e) {
            throw new RuntimeException("Ошибка при импорте CSV: " + e.getMessage());
        }
    }

}
