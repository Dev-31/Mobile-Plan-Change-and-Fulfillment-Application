package com.prodapt.mobileplan.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ai_memory")
public class AIMemory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 🔗 Who asked the question
    @Column(nullable = false)
    private Long userId;

    // ❓ User question
    @Column(nullable = false, length = 1000)
    private String question;

    // 🤖 AI answer
    @Column(nullable = false, length = 4000)
    private String answer;

    // 🧠 Intent detected (EXPIRY / USAGE / BENEFITS / etc.)
    @Column(nullable = false)
    private String intent;

    // ⏱ Timestamp
    @Column(nullable = false)
    private LocalDateTime createdAt;

    /* =====================
       CONSTRUCTOR
       ===================== */
    public AIMemory() {
        this.createdAt = LocalDateTime.now();
    }

    /* =====================
       GETTERS
       ===================== */
    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public String getIntent() {
        return intent;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /* =====================
       SETTERS
       ===================== */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setIntent(String intent) {
        this.intent = intent;
    }
}
