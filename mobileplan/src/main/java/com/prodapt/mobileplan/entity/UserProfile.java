package com.prodapt.mobileplan.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user_profiles")
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String email;
    private String address;

    // Preferences
    private boolean smsEnabled;
    private boolean emailEnabled;
    private boolean pushEnabled;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public UserProfile() {
        this.smsEnabled = true;
        this.emailEnabled = true;
        this.pushEnabled = false;
    }

    // getters & setters
    public Long getId() { return id; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getAddress() { return address; }
    public boolean isSmsEnabled() { return smsEnabled; }
    public boolean isEmailEnabled() { return emailEnabled; }
    public boolean isPushEnabled() { return pushEnabled; }
    public User getUser() { return user; }

    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setEmail(String email) { this.email = email; }
    public void setAddress(String address) { this.address = address; }
    public void setSmsEnabled(boolean smsEnabled) { this.smsEnabled = smsEnabled; }
    public void setEmailEnabled(boolean emailEnabled) { this.emailEnabled = emailEnabled; }
    public void setPushEnabled(boolean pushEnabled) { this.pushEnabled = pushEnabled; }
    public void setUser(User user) { this.user = user; }
}
