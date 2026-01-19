package com.prodapt.mobileplan.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "promotions")
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String code;

    private double discountPercent;

    private double minAmount; // eligibility condition

    private boolean active;

    public Long getId() { return id; }
    public String getCode() { return code; }
    public double getDiscountPercent() { return discountPercent; }
    public double getMinAmount() { return minAmount; }
    public boolean isActive() { return active; }

    public void setCode(String code) { this.code = code; }
    public void setDiscountPercent(double discountPercent) {
        this.discountPercent = discountPercent;
    }
    public void setMinAmount(double minAmount) { this.minAmount = minAmount; }
    public void setActive(boolean active) { this.active = active; }
}
