package com.example.bookinghotel.models;

public class wishlistDTO {
    private Integer pid;
    private Integer ctgid;
    private String PName;
    private String RName;
    private String RImage;
    private String HName;

    public wishlistDTO(Integer pid, Integer ctgid, String PName, String RName, String RImage, String HName) {
        this.pid = pid;
        this.ctgid = ctgid;
        this.PName = PName;
        this.RName = RName;
        this.HName = HName;
        this.RImage = RImage;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getCtgid() {
        return ctgid;
    }

    public void setCtgid(Integer ctgid) {
        this.ctgid = ctgid;
    }

    public String getPName() {
        return PName;
    }

    public void setPName(String PName) {
        this.PName = PName;
    }

    public String getRName() {
        return RName;
    }

    public void setRName(String RName) {
        this.RName = RName;
    }

    public String getHName() {
        return HName;
    }

    public void setHName(String HName) {
        this.HName = HName;
    }

    public String getRImage() {
        return RImage;
    }

    public void setRImage(String RImage) {
        this.RImage = RImage;
    }
}
