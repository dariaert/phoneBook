package com.dariaert.phonebook.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "contacts_table")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contact_id")
    private Long id;

    @Column(name = "contact_name", nullable = false)
    private String name;

    @Column(name = "contact_phone", nullable = false, unique = true)
    private String phoneNumber;

    @Column(name = "contact_email")
    private String email;

    @Column(name = "contact_address")
    private String address;

}
