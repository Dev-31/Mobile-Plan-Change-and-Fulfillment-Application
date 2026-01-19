package com.prodapt.mobileplan.entity;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "subscriptions")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "plan_id", nullable = false)
    private Plan plan;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SubscriptionStatus status;

    @Column(nullable = false)
    private LocalDate startDate;

    private LocalDate endDate;

    public Subscription() {
        this.startDate = LocalDate.now();
        this.status = SubscriptionStatus.ACTIVE;
    }

    // getters and setters
    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Plan getPlan() {
        return plan;
    }

    public SubscriptionStatus getStatus() {
        return status;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public void setStatus(SubscriptionStatus status) {
        this.status = status;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

}
