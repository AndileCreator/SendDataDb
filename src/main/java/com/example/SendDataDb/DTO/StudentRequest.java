package com.example.SendDataDb.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record StudentRequest(
        @NotBlank(message = "First name is Required!")
        @Size(max = 100, message = "First name must not exceed 100 characters")
        String fistName,

        @NotBlank(message = "Last name is required!")
        @Size(max = 100, message = "Last name must not exceed 100 characters")
        String lastName,

        @NotBlank(message = "Email is required!")
        @Email(message = "Enter a valid email address")
        @Size(max = 225 , message = "Email must not 255 characters")
        String email

) {
}
