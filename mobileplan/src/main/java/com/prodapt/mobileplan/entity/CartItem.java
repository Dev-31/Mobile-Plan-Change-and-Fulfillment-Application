package com.prodapt.mobileplan.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "cart_items")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "plan_id", nullable = false)
    private Plan plan;

    // ✅ NEW
    @Column(nullable = false)
    private int validityInDays;

    @Column(nullable = false)
    private double calculatedPrice;

    public Long getId() { return id; }
    public Cart getCart() { return cart; }
    public Plan getPlan() { return plan; }
    public int getValidityInDays() { return validityInDays; }
    public double getCalculatedPrice() { return calculatedPrice; }

    public void setCart(Cart cart) { this.cart = cart; }
    public void setPlan(Plan plan) { this.plan = plan; }
    public void setValidityInDays(int validityInDays) {
        this.validityInDays = validityInDays;
    }
    public void setCalculatedPrice(double calculatedPrice) {
        this.calculatedPrice = calculatedPrice;
    }
}
