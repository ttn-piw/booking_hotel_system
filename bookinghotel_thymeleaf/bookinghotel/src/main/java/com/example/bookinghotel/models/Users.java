package com.example.bookinghotel.models;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int UID;

    @Column(name = "UEmail", nullable = false)
    private String UEmail;

    @Column(name = "UPassword", nullable = false)
    private String UPassword;

    @Column(name = "URole", nullable = false)
    private String URole;

    // Getters and setters

    public String getUEmail() {
        return UEmail;
    }

    public void setUEmail(String UEmail) {
        this.UEmail = UEmail;
    }

    public int getUID() {
        return UID;
    }

    public void setUID(int UID) {
        this.UID = UID;
    }

    public String getUPassword() {
        return UPassword;
    }

    public void setUPassword(String UPassword) {
        this.UPassword = UPassword;
    }

    public String getURole() {
        return URole;
    }

    public void setURole(String URole) {
        this.URole = URole;
    }
}

