package com.example.bookinghotel.models;

import java.io.Serializable;
import java.util.Objects;

public class compositeKey_WL implements Serializable {
    private Integer PID;
    private Integer CTGID;

    public compositeKey_WL() {};
    public compositeKey_WL(Integer PID, Integer CTGID) {
        this.PID = PID;
        this.CTGID = CTGID;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        compositeKey_WL that = (compositeKey_WL) o;
        return Objects.equals(CTGID, that.CTGID) && Objects.equals(PID, that.PID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(CTGID, PID);
    }
}
