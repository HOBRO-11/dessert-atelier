package com.yangnjo.dessert_atelier.db.entity;

import java.time.LocalDateTime;

import com.yangnjo.dessert_atelier.db.model.QnAStatus;

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
    @JoinColumn(name = "display_product_id")
    private DisplayProducts displayProducts;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users users;

    private String password;

    @Column(nullable = false)
    private String comment;

    private LocalDateTime commentUpdatedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private QnAStatus status;

    private String answer;

    private LocalDateTime answerUpdatedAt;

    /*
     * this case return null value, if users and password is null value
     */
    public QnAs createQnA(DisplayProducts dp, Users users, String password, String comment) {
        if (users == null && password == null) {
            return null;
        }
        QnAs qnAs = new QnAs();
        if (users != null) {
            qnAs.users = users;
            users.addQnA(this);
        }
        if (users == null) {
            qnAs.password = password;
        }
        qnAs.displayProducts = dp;
        dp.addQna(qnAs);
        qnAs.comment = comment;
        qnAs.status = QnAStatus.WAITING;
        setCommentUpdateAt();
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
        this.answer = answer;
        setAnswerUpdateAt();
    }

    private void setCommentUpdateAt() {
        this.commentUpdatedAt = LocalDateTime.now();
    }

    private void setAnswerUpdateAt() {
        this.answerUpdatedAt = LocalDateTime.now();
    }

}
