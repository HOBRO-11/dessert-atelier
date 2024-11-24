package com.yangnjo.dessert_atelier.domain_service.react.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yangnjo.dessert_atelier.domain.display_product.DisplayProduct;
import com.yangnjo.dessert_atelier.domain.member.Member;
import com.yangnjo.dessert_atelier.domain.react.Review;
import com.yangnjo.dessert_atelier.domain.react.ReviewImage;
import com.yangnjo.dessert_atelier.domain.react.ReviewStatus;
import com.yangnjo.dessert_atelier.domain_service.display_product.exception.DisplayProductNotFountException;
import com.yangnjo.dessert_atelier.domain_service.member.exception.MemberNotFoundException;
import com.yangnjo.dessert_atelier.domain_service.react.ReviewCommandService;
import com.yangnjo.dessert_atelier.domain_service.react.dto.ReviewCreateDto;
import com.yangnjo.dessert_atelier.domain_service.react.dto.ReviewUpdateDto;
import com.yangnjo.dessert_atelier.domain_service.react.exception.ReviewNonAuthException;
import com.yangnjo.dessert_atelier.domain_service.react.exception.ReviewNotFoundException;
import com.yangnjo.dessert_atelier.repository.DisplayProductRepository;
import com.yangnjo.dessert_atelier.repository.MemberRepository;
import com.yangnjo.dessert_atelier.repository.ReviewImageRepository;
import com.yangnjo.dessert_atelier.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = false)
public class ReviewCommandServiceImpl implements ReviewCommandService {

    private final ReviewRepository reviewRepository;
    private final ReviewImageRepository reviewImageRepository;
    private final DisplayProductRepository displayProductRepository;
    private final MemberRepository memberRepository;

    @Override
    public Long createMemberReview(final ReviewCreateDto dto) {
        DisplayProduct displayProduct = findDpById(dto.getDpId());
        Member member = findMemberById(dto.getMemberId());
        ReviewImage reviewImage = new ReviewImage(dto.getImageUrls());
        ReviewImage savedReviewImage = reviewImageRepository.save(reviewImage);

        Review review = dto.toMemberReviewEntity(displayProduct, member, savedReviewImage);
        Review savedReview = reviewRepository.save(review);
        return savedReview.getId();
    }

    @Override
    public Long createStoreReview(final ReviewCreateDto dto) {
        DisplayProduct displayProduct = findDpById(dto.getDpId());
        ReviewImage reviewImage = new ReviewImage(dto.getImageUrls());
        ReviewImage savedReviewImage = reviewImageRepository.save(reviewImage);

        Review review = dto.toStoreReviewEntity(displayProduct, savedReviewImage);
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
    public void deleteReview(Long reviewId, Long memberId) {
        Review review = findReviewById(reviewId);

        checkExistMember(memberId);
        checkAuthMember(memberId, review);

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
            throw new ReviewNonAuthException();
        }
    }
}
