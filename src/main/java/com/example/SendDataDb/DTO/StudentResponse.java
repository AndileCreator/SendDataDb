package com.example.SendDataDb.DTO;

public record StudentResponse(
        Long id,
        String firstName,
        String lastName,
        String email
) {
}
