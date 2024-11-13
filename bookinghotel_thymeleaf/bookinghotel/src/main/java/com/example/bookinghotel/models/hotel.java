package com.example.bookinghotel.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "hotel")
public class hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int HID;

    @Column(name = "HName", nullable = false)
    @NotEmpty(message = "Hotel's name is required")
    private String HName;

    @Column(name = "HAddress", nullable = false)
    @NotEmpty(message = "Hotel's address is required")
    private String HAddress;

    @Column(name = "HStar", nullable = false)
    @NotEmpty(message = "Hotel's star is required")
    private String HStar;

    @Column(name = "HPhone", nullable = false)
    @NotEmpty(message = "Hotel's phone is required")
    private String HPhone;

    @Column(name = "HImg", nullable = false)
    private String HImg;

    @Column(name = "HDescription")
    private String HDescription;

    public String getHDescription() {
        return HDescription;
    }

    public void setHDescription(String HDescription) {
        this.HDescription = HDescription;
    }

    public int getHID() {
        return HID;
    }

    public void setHID(int HID) {
        this.HID = HID;
    }

    public String getHName() {
        return HName;
    }

    public void setHName(String HName) {
        this.HName = HName;
    }

    public String getHAddress() {
        return HAddress;
    }

    public void setHAddress(String HAddress) {
        this.HAddress = HAddress;
    }

    public String getHStar() {
        return HStar;
    }

    public void setHStar(String HStar) {
        this.HStar = HStar;
    }

    public String getHPhone() {
        return HPhone;
    }

    public void setHPhone(String HPhone) {
        this.HPhone = HPhone;
    }

    public String getHImg() {
        return HImg != null ? HImg : "";
    }

    public void setHImg(String HImg) {
        this.HImg = HImg;
    }
}
