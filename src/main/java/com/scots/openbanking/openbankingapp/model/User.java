package com.scots.openbanking.openbankingapp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

// User entity representing a user document in MongoDB
@Document(collection = "users")
public class User {
    @Id
    private String id; // Unique identifier for the user

    // User profile fields
    private String email;
    private String firstName;
    private String lastName;
    private String preferredName;
    private String title;
    private String dateOfBirth;
    private boolean googleUser; // True if registered via Google OAuth
    private String password;
    private boolean registered; // True if registration is complete

    // Default constructor
    public User() {}

    // Parameterized constructor for creating a user with all fields
    public User(String email, String firstName, String lastName, String preferredName, String title, String dateOfBirth, boolean googleUser, String password, boolean registered) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.preferredName = preferredName;
        this.title = title;
        this.dateOfBirth = dateOfBirth;
        this.googleUser = googleUser;
        this.password = password;
        this.registered = registered;
    }

    // Getters and setters for all fields
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getPreferredName() { return preferredName; }
    public void setPreferredName(String preferredName) { this.preferredName = preferredName; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(String dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public boolean isGoogleUser() { return googleUser; }
    public void setGoogleUser(boolean googleUser) { this.googleUser = googleUser; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public boolean isRegistered() { return registered; }
    public void setRegistered(boolean registered) { this.registered = registered; }
}
