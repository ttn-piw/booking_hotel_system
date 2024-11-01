package com.example.bookinghotel.models;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

public class hotelDTO {
    @NotEmpty(message = "Hotel's name is required")
    private String HName;

    @NotEmpty(message = "Hotel's address is required")
    private String HAddress;

    @NotEmpty(message = "Hotel's star is required")
    private String HStar;

    @NotEmpty(message = "Hotel's phone is required")
    private String HPhone;

    private MultipartFile HImg;
    private String HImgPath;

    public @NotEmpty(message = "Hotel's address is required") String getHAddress() {
        return HAddress;
    }

    public void setHAddress(@NotEmpty(message = "Hotel's address is required") String HAddress) {
        this.HAddress = HAddress;
    }

    public @NotEmpty(message = "Hotel's name is required") String getHName() {
        return HName;
    }

    public void setHName(@NotEmpty(message = "Hotel's name is required") String HName) {
        this.HName = HName;
    }

    public @NotEmpty(message = "Hotel's star is required") String getHStar() {
        return HStar;
    }

    public void setHStar(@NotEmpty(message = "Hotel's star is required") String HStar) {
        this.HStar = HStar;
    }

    public @NotEmpty(message = "Hotel's phone is required") String getHPhone() {
        return HPhone;
    }

    public void setHPhone(@NotEmpty(message = "Hotel's phone is required") String HPhone) {
        this.HPhone = HPhone;
    }

    public MultipartFile getHImg() {
        return HImg;
    }

    public void setHImg(MultipartFile HImg) {
        this.HImg = HImg;
    }

    public String getHImgPath() {
        return HImgPath;
    }

    public void setHImgPath(String HImgPath) {
        this.HImgPath = HImgPath;
    }
}
