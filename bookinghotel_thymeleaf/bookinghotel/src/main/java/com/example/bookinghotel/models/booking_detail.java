package com.example.bookinghotel.models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "booking_detail")
public class booking_detail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int BKDID;

    @ManyToOne
    @JoinColumn(name = "BID", nullable = false)
    private booking booking;

    @ManyToOne
    @JoinColumn(name = "CTGID", nullable = false)
    private room room;

    @Column(name = "BKDQuantity", nullable = false)
    private String BKDQuantity;

    public int getBKDID() {
        return BKDID;
    }

    public void setBKDID(int BKDID) {
        this.BKDID = BKDID;
    }

    public com.example.bookinghotel.models.booking getBooking() {
        return booking;
    }

    public void setBooking(com.example.bookinghotel.models.booking booking) {
        this.booking = booking;
    }

    public com.example.bookinghotel.models.room getRoom() {
        return room;
    }

    public void setRoom(com.example.bookinghotel.models.room room) {
        this.room = room;
    }

    public String getBKDQuantity() {
        return BKDQuantity;
    }

    public void setBKDQuantity(String BKDQuantity) {
        this.BKDQuantity = BKDQuantity;
    }
}
