package com.yangnjo.dessert_atelier.domain;

import java.time.LocalDateTime;

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
public class Todos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_code", nullable = false)
    private Orders orders;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TodoStatus status;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime completeAt;

    public static Todos createTodo(Orders orders) {
        Todos todos = new Todos();
        todos.orders = orders;
        todos.status = TodoStatus.PENDING;
        todos.createdAt = LocalDateTime.now();
        return todos;
    };

    public void inProgress() {
        this.status = TodoStatus.IN_PROGRESS;
    }

    public void pending() {
        this.status = TodoStatus.PENDING;
    }

    public void completed() {
        this.status = TodoStatus.COMPLETED;
        this.completeAt = LocalDateTime.now();
    }

}
