package com.yangnjo.dessert_atelier.domain_model.react;

import java.time.LocalDateTime;

import com.yangnjo.dessert_atelier.domain_model.member.Member;
import com.yangnjo.dessert_atelier.domain_model.product.DisplayProduct;

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
import lombok.Setter;

@Getter
@Entity
@Table(name = "qna")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QnA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "display_product_id", nullable = false, updatable = false)
    private DisplayProduct displayProduct;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String password;

    @Column(nullable = false)
    private String comment;

    private LocalDateTime commentCreatedAt;

    private LocalDateTime commentUpdatedAt;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private QnAStatus qnaStatus;

    private String answer;

    private LocalDateTime answerCreatedAt;

    private LocalDateTime answerUpdatedAt;

    public static QnA createGuestQnA(DisplayProduct displayProduct, String password, String comment) {
        QnA qnAs = new QnA();
        qnAs.displayProduct = displayProduct;
        displayProduct.addQna(qnAs);
        qnAs.password = password;
        qnAs.comment = comment;
        qnAs.qnaStatus = QnAStatus.WAITING;
        qnAs.setCommentCreatedAt();
        qnAs.setCommentUpdateAt();
        return qnAs;
    }

    public static QnA createMemberQnA(DisplayProduct displayProduct, Member member, String comment) {
        QnA qnAs = new QnA();
        qnAs.displayProduct = displayProduct;
        displayProduct.addQna(qnAs);
        member.addQnA(qnAs);
        qnAs.member = member;
        qnAs.comment = comment;
        qnAs.qnaStatus = QnAStatus.WAITING;
        qnAs.setCommentCreatedAt();
        qnAs.setCommentUpdateAt();
        return qnAs;
    }

    public void changeComment(String newComment) {
        this.comment = newComment;
        setCommentUpdateAt();
    }

    public void setAnswer(String answer) {
        if(this.answer == null) {
            setAnswerCreatedAt();
        }
        this.answer = answer;
        this.qnaStatus = QnAStatus.ANSWERED;
        setAnswerUpdateAt();
    }

    public void removeAnswer() {
        this.answer = null;
        this.qnaStatus = QnAStatus.WAITING;
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

    public void setIdToTest(Long id) {
        this.id = id;
    }

}
