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
}
