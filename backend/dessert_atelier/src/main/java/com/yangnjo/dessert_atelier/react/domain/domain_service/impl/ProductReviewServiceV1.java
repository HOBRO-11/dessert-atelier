package com.yangnjo.dessert_atelier.react.domain.domain_service.impl;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yangnjo.dessert_atelier.commons.aop.AccessCheckable;
import com.yangnjo.dessert_atelier.commons.aop.AccessChecker;
import com.yangnjo.dessert_atelier.react.domain.domain_service.ProductReviewService;
import com.yangnjo.dessert_atelier.react.domain.entity.ProductReact;
import com.yangnjo.dessert_atelier.react.domain.entity.ProductReview;
import com.yangnjo.dessert_atelier.react.domain.entity.ProductReviewStatus;
import com.yangnjo.dessert_atelier.react.domain.repository.ProductReactRepository;
import com.yangnjo.dessert_atelier.react.domain.repository.ReviewRepository;
import com.yangnjo.dessert_atelier.react.dto.ProductReviewCreateDto;
import com.yangnjo.dessert_atelier.react.exception.ProductReactNotFoundException;
import com.yangnjo.dessert_atelier.react.exception.ProductReviewNotFoundException;
import com.yangnjo.dessert_atelier.react.properties.ReactProperties;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = false)
public class ProductReviewServiceV1 implements ProductReviewService, AccessCheckable {

    private final ProductReactRepository productReactRepository;
    private final ReviewRepository reviewRepository;

    @Override
    public void check(Long memberId, Long entityId) throws AccessDeniedException {
        ProductReview productReview = findProductReviewById(entityId);
        if (productReview.getMemberId() != memberId) {
            throw new AccessDeniedException("작성자가 아닙니다.");
        }
    }

    @Override
    public Long createByMember(final ProductReviewCreateDto dto) {
        ProductReact productReact = findProductReactById(dto.getProductReactId());

        ProductReview review = dto.toMemberReviewEntity(productReact);
        ProductReview savedReview = reviewRepository.save(review);
        return savedReview.getId();
    }

    @Override
    public Long createByStore(final ProductReviewCreateDto dto) {
        ProductReact productReact = findProductReactById(dto.getProductReactId());

        ProductReview review = dto.toStoreReviewEntity(productReact);
        ProductReview savedReview = reviewRepository.save(review);
        return savedReview.getId();
    }

    @Override
    public void updateStatus(Long memberId, Long reviewId, ProductReviewStatus status) {
        ProductReview productReview = findProductReviewById(reviewId);

        if (productReview.getMemberId() != memberId) {
            throw new AccessDeniedException(ReactProperties.PRODUCT_REVIEW);
        }

        productReview.setStatus(status);
    }

    @Override
    @AccessChecker(entityId = "reviewId")
    public void updateComment(Long memberId, Long reviewId, String comment) {
        ProductReview productReview = findProductReviewById(reviewId);
        productReview.setComment(comment);

    }

    @Override
    public void delete(Long reviewId) {
        findProductReviewById(reviewId);
        reviewRepository.deleteById(reviewId);
    }

    private ProductReview findProductReviewById(final Long reviewId) {
        return reviewRepository.findById(reviewId).orElseThrow(ProductReviewNotFoundException::new);
    }

    private ProductReact findProductReactById(final Long productReactId) {
        return productReactRepository.findById(productReactId).orElseThrow(() -> new ProductReactNotFoundException());
    }

}
