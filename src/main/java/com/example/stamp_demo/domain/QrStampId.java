package com.example.stamp_demo.domain;

import java.io.Serializable;
import java.util.Objects;
import jakarta.persistence.Embeddable;

@Embeddable
public class QrStampId implements Serializable {
    private Long qrId;
    private Long usr;

    // Getters, setters, equals, hashCode 메서드들...

    public Long getQrId() {
        return qrId;
    }

    public void setQrId(Long qrId) {
        this.qrId = qrId;
    }

    public Long getUsr() {
        return usr;
    }

    public void setUsr(Long usr) {
        this.usr = usr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QrStampId that = (QrStampId) o;
        return Objects.equals(qrId, that.qrId) && Objects.equals(usr, that.usr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(qrId, usr);
    }
}
