package com.blog.reactive.controller;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class CreateUserRequest {

    @NotBlank(message ="First name is required")
    private String firstName;
    @NotBlank(message ="Last name is required")
    private String lastName;
    @NotBlank(message ="Email is required")
    @Email(message="Please enter a valid email address")
    private String email;
    @NotBlank(message ="Password is required")
    private String password;


    public CreateUserRequest() {
    }

    public CreateUserRequest(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
