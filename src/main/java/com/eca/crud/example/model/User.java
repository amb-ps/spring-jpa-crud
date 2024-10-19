package com.eca.crud.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {

    private String username;
    private String password; // Store as plain text for simplicity in development
    private String role;

}
