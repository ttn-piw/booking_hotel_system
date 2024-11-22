package com.example.bookinghotel.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "booking")
public class booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int BID;

    @ManyToOne
    @JoinColumn(name = "PID", nullable = false)
    private person person;

    @ManyToOne
    @JoinColumn(name = "HID",nullable = false)
    private hotel hotel;

    @ManyToOne
    @JoinColumn(name = "CTGID",nullable = false)
    private room room;

    @Column(name = "bcheck_in", nullable = true)
    private LocalDate BCheckIn;

    @Column(name = "bcheck_out", nullable = true)
    private LocalDate BCheckOut;

    @Column(name = "BDate", nullable = false, updatable = false)
    private LocalDateTime BDate;

    @PrePersist
    public void prePersist() {
        this.BDate = LocalDateTime.now();
    }

    @Column(name = "BMoney", nullable = false)
    private String BMoney;

    @Column(name = "BPay", nullable = false)
    private Boolean BPay;

    public int getBID() {
        return BID;
    }

    public void setBID(int BID) {
        this.BID = BID;
    }

    public com.example.bookinghotel.models.person getPerson() {
        return person;
    }

    public void setPerson(com.example.bookinghotel.models.person person) {
        this.person = person;
    }

    public com.example.bookinghotel.models.hotel getHotel() {
        return hotel;
    }

    public void setHotel(com.example.bookinghotel.models.hotel hotel) {
        this.hotel = hotel;
    }

    public com.example.bookinghotel.models.room getRoom() {
        return room;
    }

    public void setRoom(com.example.bookinghotel.models.room room) {
        this.room = room;
    }

    public LocalDate getBCheckIn() {
        return BCheckIn;
    }

    public void setBCheckIn(LocalDate BCheckIn) {
        this.BCheckIn = BCheckIn;
    }

    public LocalDate getBCheckOut() {
        return BCheckOut;
    }

    public void setBCheckOut(LocalDate BCheckOut) {
        this.BCheckOut = BCheckOut;
    }

    public LocalDateTime getBDate() {
        return BDate;
    }

    public void setBDate(LocalDateTime BDate) {
        this.BDate = BDate;
    }

    public String getBMoney() {
        return BMoney;
    }

    public void setBMoney(String BMoney) {
        this.BMoney = BMoney;
    }

    public Boolean getBPay() {
        return BPay;
    }

    public void setBPay(Boolean BPay) {
        this.BPay = BPay;
    }
}
