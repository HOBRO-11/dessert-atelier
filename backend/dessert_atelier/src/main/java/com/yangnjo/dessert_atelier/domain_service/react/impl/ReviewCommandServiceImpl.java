package com.yangnjo.dessert_atelier.domain_service.react.impl;


import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yangnjo.dessert_atelier.domain_model.member.Member;
import com.yangnjo.dessert_atelier.domain_model.product.DisplayProduct;
import com.yangnjo.dessert_atelier.domain_model.react.Review;
import com.yangnjo.dessert_atelier.domain_model.react.ReviewStatus;
import com.yangnjo.dessert_atelier.domain_service.member.exception.MemberNotFoundException;
import com.yangnjo.dessert_atelier.domain_service.product.exception.DisplayProductNotFountException;
import com.yangnjo.dessert_atelier.domain_service.react.ReviewCommandService;
import com.yangnjo.dessert_atelier.domain_service.react.dto.ReviewCreateDto;
import com.yangnjo.dessert_atelier.domain_service.react.dto.ReviewUpdateDto;
import com.yangnjo.dessert_atelier.domain_service.react.exception.ReviewNotFoundException;
import com.yangnjo.dessert_atelier.repository.member.MemberRepository;
import com.yangnjo.dessert_atelier.repository.product.DisplayProductRepository;
import com.yangnjo.dessert_atelier.repository.react.ReviewRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = false)
public class ReviewCommandServiceImpl implements ReviewCommandService {

    private final ReviewRepository reviewRepository;
    private final DisplayProductRepository displayProductRepository;
    private final MemberRepository memberRepository;

    @Override
    public Long createMemberReview(final ReviewCreateDto dto) {
        DisplayProduct displayProduct = findDpById(dto.getDpId());
        Member member = findMemberById(dto.getMemberId());

        Review review = dto.toMemberReviewEntity(displayProduct, member);
        Review savedReview = reviewRepository.save(review);
        return savedReview.getId();
    }

    @Override
    public Long createStoreReview(final ReviewCreateDto dto) {
        DisplayProduct displayProduct = findDpById(dto.getDpId());

        Review review = dto.toStoreReviewEntity(displayProduct);
        Review savedReview = reviewRepository.save(review);
        return savedReview.getId();
    }

    @Override
    public void updateReview(final ReviewUpdateDto dto) {
        Long reviewId = dto.getReviewId();
        String comment = dto.getComment();
        Long memberId = dto.getMemberId();

        Review review = findReviewById(reviewId);

        checkExistMember(memberId);
        checkAuthMember(memberId, review);

        review.setComment(comment);
    }

    @Override
    public void updateReviewStatus(Long reviewId, ReviewStatus status) {
        Review review = findReviewById(reviewId);
        review.setReviewStatus(status);
    }

    @Override
    public void deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }

    private DisplayProduct findDpById(Long dpId) {
        DisplayProduct displayProduct = displayProductRepository.findById(dpId)
                .orElseThrow(() -> new DisplayProductNotFountException());
        return displayProduct;
    }

    private Review findReviewById(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException());
        return review;
    }

    private Member findMemberById(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException());
        return member;
    }

    private void checkExistMember(Long memberId) {
        boolean isExist = memberRepository.existsById(memberId);
        if (isExist == false) {
            throw new MemberNotFoundException();
        }
    }

    private void checkAuthMember(Long memberId, Review review) {
        if (review.getMember().getId() != memberId) {
            throw new AccessDeniedException("review");
        }
    }
}
