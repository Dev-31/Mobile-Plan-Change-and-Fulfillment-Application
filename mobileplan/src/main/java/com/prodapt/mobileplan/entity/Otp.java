package com.prodapt.mobileplan.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "otp")
public class Otp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String destination; // email or mobile
    private String code;

    @Column(nullable = false)
    private String purpose; // REGISTER, LOGIN, RESET

    private LocalDateTime expiresAt;

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresAt);
    }

    // getters & setters
    public Long getId() { return id; }
    public String getDestination() { return destination; }
    public String getCode() { return code; }
    public String getPurpose() { return purpose; }
    public LocalDateTime getExpiresAt() { return expiresAt; }

    public void setDestination(String destination) { this.destination = destination; }
    public void setCode(String code) { this.code = code; }
    public void setPurpose(String purpose) { this.purpose = purpose; }
    public void setExpiresAt(LocalDateTime expiresAt) { this.expiresAt = expiresAt; }
}
