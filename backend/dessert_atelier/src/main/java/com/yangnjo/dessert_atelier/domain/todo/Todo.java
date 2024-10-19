package com.yangnjo.dessert_atelier.domain.todo;

import java.time.LocalDateTime;

import com.yangnjo.dessert_atelier.domain.order.Orders;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_code", nullable = false)
    private Orders orders;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TodoStatus todoStatus;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime completeAt;

    public Todo(Orders orders) {
        this.orders = orders;
        this.todoStatus = TodoStatus.PENDING;
        this.createdAt = LocalDateTime.now();
    };

    public void inProgress() {
        this.todoStatus = TodoStatus.IN_PROGRESS;
    }

    public void pending() {
        this.todoStatus = TodoStatus.PENDING;
    }

    public void completed() {
        this.todoStatus = TodoStatus.COMPLETED;
        this.completeAt = LocalDateTime.now();
    }

    public void setIdToTest(Long id) {
        this.id = id;
    }

}
