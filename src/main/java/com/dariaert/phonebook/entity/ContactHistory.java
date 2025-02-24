package com.dariaert.phonebook.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "history_table")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContactHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_id")
    private Long id;

    @Column(name = "history_contactId")
    private Long contactId;

    @Column(name = "history_fieldName")
    private String fieldName;

    @Column(name = "history_oldValue")
    private String oldValue;

    @Column(name = "history_newValue")
    private String newValue;

    @Column(name = "history_changeTime")
    private LocalDateTime changeTime;

}
