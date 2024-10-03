package com.booking_hotel.booking_hotel.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class Users {
    @Id
    private int UID;
    private String UEmail;
    private String UPassword;
    private String URole;

    public int getUID() {
        return UID;
    }

    public void setUID(int UID) {
        this.UID = UID;
    }

    public String getUEmail() {
        return UEmail;
    }

    public void setUEmail(String UEmail) {
        this.UEmail = UEmail;
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
