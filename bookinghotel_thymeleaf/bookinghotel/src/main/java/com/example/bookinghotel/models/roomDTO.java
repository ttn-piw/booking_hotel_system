package com.example.bookinghotel.models;

import jakarta.validation.constraints.Min;
import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class roomDTO {
    @NotEmpty(message = "Category's name is required")
    private String CTGName;

    @NotEmpty(message = "Room star is required")
    private String CTGStar;

    @NotNull(message = "Room quantity is required")
    @Min(value = 1, message = "Room quantity must be at least 1")
    private Integer CTGQuantity;

    private Integer CTGRemain;

    private MultipartFile CTGImg;

    private String CTGImgPath;

    @NotNull(message = "Hotel ID is required")
    private Integer HID;

    public @NotEmpty(message = "Room star is required") String getCTGStar() {
        return CTGStar;
    }

    public void setCTGStar(@NotEmpty(message = "Room star is required") String CTGStar) {
        this.CTGStar = CTGStar;
    }

    public @NotEmpty(message = "Category's name is required") String getCTGName() {
        return CTGName;
    }

    public void setCTGName(@NotEmpty(message = "Category's name is required") String CTGName) {
        this.CTGName = CTGName;
    }

    public @NotNull(message = "Room quantity is required") @Min(value = 1, message = "Room quantity must be at least 1") Integer getCTGQuantity() {
        return CTGQuantity;
    }

    public void setCTGQuantity(@NotNull(message = "Room quantity is required") @Min(value = 1, message = "Room quantity must be at least 1") Integer CTGQuantity) {
        this.CTGQuantity = CTGQuantity;
    }

    public Integer getCTGRemain() {
        return CTGRemain;
    }

    public void setCTGRemain(Integer CTGRemain) {
        this.CTGRemain = CTGRemain;
    }

    public MultipartFile getCTGImg() {
        return CTGImg;
    }

    public void setCTGImg (MultipartFile CTGImg) {
        this.CTGImg = CTGImg;
    }

    public @NotNull(message = "Hotel ID is required") Integer getHID() {
        return HID;
    }

    public void setHID(@NotNull(message = "Hotel ID is required") Integer HID) {
        this.HID = HID;
    }

    public String getCTGImgPath() {
        return CTGImgPath;
    }

    public void setCTGImgPath(String CTGImgPath) {
        this.CTGImgPath = CTGImgPath;
    }
}

