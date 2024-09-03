package com.haleema.trial_task_application.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "user_profile")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "email", nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "created_at", nullable = false)
    private Date createdAt = new Date();

    @Column(name = "last_login")
    private Date lastLogin;

    @Column(name = "is_deactivated", nullable = false)
    private Boolean isDeactivated = false;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @PrePersist
    @PreUpdate
    public void validate() {
        if (email == null || email.trim().isEmpty() || email.length() > 150) {
            throw new IllegalArgumentException("Email is invalid");
        }
        if (name == null || name.trim().isEmpty() || name.length() > 100) {
            throw new IllegalArgumentException("Name is invalid");
        }
        if (password == null || password.trim().isEmpty() || password.length() > 255 || password.length() < 4) {
            throw new IllegalArgumentException("Password is invalid");
        }
        if (dateOfBirth != null && dateOfBirth.after(new Date())) {
            throw new IllegalArgumentException("Date of birth cannot be in the future.");
        }
        if (createdAt == null) {
            createdAt = new Date();
        }
        if (lastLogin != null && lastLogin.after(new Date())) {
            throw new IllegalArgumentException("Last login date cannot be in the future.");
        }
    }
}

