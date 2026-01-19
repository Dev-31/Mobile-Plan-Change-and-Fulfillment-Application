package com.prodapt.mobileplan.dto.request;

public class ProfileUpdateRequest {

    private String fullName;
    private String email;
    private String address;

    private boolean smsEnabled;
    private boolean emailEnabled;
    private boolean pushEnabled;

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public boolean isSmsEnabled() {
        return smsEnabled;
    }

    public boolean isEmailEnabled() {
        return emailEnabled;
    }

    public boolean isPushEnabled() {
        return pushEnabled;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setSmsEnabled(boolean smsEnabled) {
        this.smsEnabled = smsEnabled;
    }

    public void setEmailEnabled(boolean emailEnabled) {
        this.emailEnabled = emailEnabled;
    }

    public void setPushEnabled(boolean pushEnabled) {
        this.pushEnabled = pushEnabled;
    }
}
