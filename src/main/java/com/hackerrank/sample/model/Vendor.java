package com.hackerrank.sample.model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * .
 */
@Entity
public class Vendor {
    @Id
    private Long vendorId;
    private String vendorName;
    private Long vendorContactNo;
    private String vendorEmail;
    private String vendorUsername;
    private String vendorAddress;

    public Vendor(Long vendorId, String vendorName, Long vendorContactNo, String vendorEmail, String vendorUsername, String vendorAddress) {
        this.vendorId = vendorId;
        this.vendorName = vendorName;
        this.vendorContactNo = vendorContactNo;
        this.vendorEmail = vendorEmail;
        this.vendorUsername = vendorUsername;
        this.vendorAddress = vendorAddress;
    }

    public Vendor() {
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public Long getVendorContactNo() {
        return vendorContactNo;
    }

    public void setVendorContactNo(Long vendorContactNo) {
        this.vendorContactNo = vendorContactNo;
    }

    public String getVendorEmail() {
        return vendorEmail;
    }

    public void setVendorEmail(String vendorEmail) {
        this.vendorEmail = vendorEmail;
    }

    public String getVendorUsername() {
        return vendorUsername;
    }

    public void setVendorUsername(String vendorUsername) {
        this.vendorUsername = vendorUsername;
    }

    public String getVendorAddress() {
        return vendorAddress;
    }

    public void setVendorAddress(String vendorAddress) {
        this.vendorAddress = vendorAddress;
    }

    @Override
    public String toString() {
        return "Vendor{" +
                "vendorId=" + vendorId +
                ", vendorName='" + vendorName + '\'' +
                ", vendorContactNo=" + vendorContactNo +
                ", vendorEmail='" + vendorEmail + '\'' +
                ", vendorUsername='" + vendorUsername + '\'' +
                ", vendorAddress='" + vendorAddress + '\'' +
                '}';
    }
}
