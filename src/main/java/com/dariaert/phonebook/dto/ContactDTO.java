package com.dariaert.phonebook.dto;

import lombok.Builder;

@Builder
public record ContactDTO(Long id, String name, String phoneNumber, String email, String address) {}
