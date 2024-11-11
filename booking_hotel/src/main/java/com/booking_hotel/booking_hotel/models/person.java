package com.booking_hotel.booking_hotel.models;

import jakarta.persistence.*;

@Entity
@Table(name="person")
public class person{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int PID;

    @Column(name = "PName", nullable = false)
    private String PName;

    @Column(name = "PAddress",nullable = false)
    private String PAddress;

    @Column(name = "PSex",nullable = false)
    private Boolean PSex;

    @OneToOne
    @JoinColumn(name = "UID",nullable = false)
    private Users users;

    public String getPName() {
        return PName;
    }

    public void setPName(String PName) {
        this.PName = PName;
    }

    public int getPID() {
        return PID;
    }

    public void setPID(int PID) {
        this.PID = PID;
    }

    public String getPAddress() {
        return PAddress;
    }

    public void setPAddress(String PAddress) {
        this.PAddress = PAddress;
    }

    public Boolean getPSex() {
        return PSex;
    }

    public void setPSex(Boolean PSex) {
        this.PSex = PSex;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }
}