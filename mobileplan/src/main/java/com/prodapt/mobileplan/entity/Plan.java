package com.prodapt.mobileplan.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "plans")
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PlanType type;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private int dataInGb;

    @Column(nullable = false)
    private int validityInDays;

    @Column(nullable = false)
    private boolean active;

    public Plan() {
        this.active = true;
    }

    // getters and setters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public PlanType getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public int getDataInGb() {
        return dataInGb;
    }

    public int getValidityInDays() {
        return validityInDays;
    }

    public boolean isActive() {
        return active;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(PlanType type) {
        this.type = type;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setDataInGb(int dataInGb) {
        this.dataInGb = dataInGb;
    }

    public void setValidityInDays(int validityInDays) {
        this.validityInDays = validityInDays;
    }
}
