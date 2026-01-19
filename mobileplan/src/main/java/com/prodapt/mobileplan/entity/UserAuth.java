package com.prodapt.mobileplan.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user_auth")
public class UserAuth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    @Column(unique = true, nullable = false)
    private String mobile;

    @Column(nullable = false)
    private String passwordHash;

    @Column(nullable = false)
    private boolean verified;

    @Column(nullable = false)
    private String role; // USER, ADMIN

    public UserAuth() {
        this.verified = false;
        this.role = "USER";
    }

    // getters & setters
    public Long getId() { return id; }
    public String getEmail() { return email; }
    public String getMobile() { return mobile; }
    public String getPasswordHash() { return passwordHash; }
    public boolean isVerified() { return verified; }
    public String getRole() { return role; }

    public void setEmail(String email) { this.email = email; }
    public void setMobile(String mobile) { this.mobile = mobile; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public void setVerified(boolean verified) { this.verified = verified; }
}
