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
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "qnas")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QnAs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "display_product_id", nullable = false)
    private DisplayProducts displayProducts;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users users;

    private String password;

    @Column(nullable = false)
    private String comment;

    private LocalDateTime commentCreatedAt;

    private LocalDateTime commentUpdatedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private QnAStatus status;

    private String answer;

    private LocalDateTime answerCreatedAt;

    private LocalDateTime answerUpdatedAt;

    /*
     * this case return null value, if users and password is null value
     */
    public static QnAs createGuestQnA(DisplayProducts displayProducts, String password, String comment) {
        QnAs qnAs = new QnAs();
        qnAs.displayProducts = displayProducts;
        displayProducts.addQna(qnAs);
        qnAs.password = password;
        qnAs.comment = comment;
        qnAs.status = QnAStatus.WAITING;
        qnAs.setCommentCreatedAt();
        qnAs.setCommentUpdateAt();
        return qnAs;
    }

    public static QnAs createUserQnA(DisplayProducts displayProducts, Users users, String comment) {
        QnAs qnAs = new QnAs();
        qnAs.displayProducts = displayProducts;
        displayProducts.addQna(qnAs);
        users.addQnA(qnAs);
        qnAs.users = users;
        qnAs.comment = comment;
        qnAs.status = QnAStatus.WAITING;
        qnAs.setCommentCreatedAt();
        qnAs.setCommentUpdateAt();
        return qnAs;
    }

    public void changeComment(String newComment) {
        this.comment = newComment;
        setCommentUpdateAt();
    }

    public void hide() {
        this.status = QnAStatus.HIDE;
    }

    public void qnaPublic() {
        this.status = QnAStatus.PUB;
    }

    public void waiting() {
        this.status = QnAStatus.WAITING;
    }

    public void setAnswer(String answer) {
        if(this.answer == null) {
            setAnswerCreatedAt();
        }
        this.answer = answer;
        this.status = QnAStatus.ANSWERED;
        setAnswerUpdateAt();
    }

    public void removeAnswer() {
        this.answer = null;
        this.status = QnAStatus.WAITING;
        setAnswerUpdateAt();
    }

    private void setCommentCreatedAt() {
        this.commentCreatedAt = LocalDateTime.now();
    }

    private void setCommentUpdateAt() {
        this.commentUpdatedAt = LocalDateTime.now();
    }

    private void setAnswerCreatedAt() {
        this.answerCreatedAt = LocalDateTime.now();
    }

    private void setAnswerUpdateAt() {
        this.answerUpdatedAt = LocalDateTime.now();
    }

}
