package com.example.bookinghotel.models;

import jakarta.persistence.*;

import javax.xml.crypto.Data;
import java.util.Date;

@Entity
@IdClass(compositeKey_WL.class)
public class wishlist {
    @Id
    private Integer PID;

    @Id
    private Integer CTGID;

    private Date WDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PID", insertable = false, updatable = false)
    private person persons;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CTGID", insertable = false, updatable = false)
    private room rooms;

    @PrePersist
    public void prePersist() {
        this.WDate = new Date();
    }

    public Integer getPID() {
        return PID;
    }

    public void setPID(Integer PID) {
        this.PID = PID;
    }

    public Integer getCTGID() {
        return CTGID;
    }

    public void setCTGID(Integer CTGID) {
        this.CTGID = CTGID;
    }

    public Date getWDate() {
        return WDate;
    }

    public void setWDate(Date WDate) {
        this.WDate = WDate;
    }

    public person getPersons() {
        return persons;
    }

    public void setPersons(person persons) {
        this.persons = persons;
    }

    public room getRooms() {
        return rooms;
    }

    public void setRooms(room rooms) {
        this.rooms = rooms;
    }
}
