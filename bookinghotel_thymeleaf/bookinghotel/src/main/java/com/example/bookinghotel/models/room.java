package com.example.bookinghotel.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "category")
public class room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int CTGID;

    @Column(name = "CTGName", nullable = false)
    @NotEmpty(message = "Category's name is required")
    private String CTGName;

    @Column(name = "CTGStar", nullable = false)
    @NotEmpty(message = "Room star is required")
    private String CTGStar;

    @Column(name = "CTGPrice", nullable = false)
    @NotEmpty(message = "Room price is required")
    private String CTGPrice;

    @Column(name = "CTGQuantity", nullable = true)
    @NotNull(message = "Room quantity is required")
    private Integer CTGQuantity;

    @Column(name = "CTGRemain", nullable = false)
    @NotNull(message = "Room remain is required")
    private Integer CTGRemain;

    @Column(name = "CTGImg", nullable = false)
    @NotEmpty(message = "Room image is required")
    private String CTGImg;

    @Column(name = "CTGDescription", nullable = false)
    @NotEmpty(message = "Room description is required")
    private String CTGDescription;

    @ManyToOne
    @JoinColumn(name = "HID",nullable = false)
    private hotel hotels;

    public @NotEmpty(message = "Room price is required") String getCTGPrice() {
        return CTGPrice;
    }

    public void setCTGPrice(@NotEmpty(message = "Room price is required") String CTGPrice) {
        this.CTGPrice = CTGPrice;
    }

    public int getCTGID() {
        return CTGID;
    }

    public void setCTGID(int CTGID) {
        this.CTGID = CTGID;
    }

    public @NotEmpty(message = "Category's name is required") String getCTGName() {
        return CTGName;
    }

    public void setCTGName(@NotEmpty(message = "Category's name is required") String CTGName) {
        this.CTGName = CTGName;
    }

    public @NotEmpty(message = "Room star is required") String getCTGStar() {
        return CTGStar;
    }

    public void setCTGStar(@NotEmpty(message = "Room star is required") String CTGStar) {
        this.CTGStar = CTGStar;
    }


    public @NotNull(message = "Room quantity is required") Integer getCTGQuantity() {
        return CTGQuantity;
    }

    public void setCTGQuantity(@NotNull(message = "Room quantity is required") Integer CTGQuantity) {
        this.CTGQuantity = CTGQuantity;
    }

    public @NotNull(message = "Room remain is required") Integer getCTGRemain() {
        return CTGRemain;
    }

    public void setCTGRemain(@NotNull(message = "Room remain is required") Integer CTGRemain) {
        this.CTGRemain = CTGRemain;
    }

    public @NotEmpty(message = "Room image is required") String getCTGImg() {
        return CTGImg;
    }

    public void setCTGImg(@NotEmpty(message = "Room image is required") String CTGImg) {
        this.CTGImg = CTGImg;
    }

    public String getCTGDescription() {
        return CTGDescription;
    }

    public void setCTGDescription(@NotEmpty(message = "Room description is required") String CTGDescription) {
        this.CTGDescription = CTGDescription;
    }

    public hotel getHotels() {
        return hotels;
    }

    public void setHotels(hotel hotels) {
        this.hotels = hotels;
    }
}
