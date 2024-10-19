package com.yangnjo.dessert_atelier.domain_service.react;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.yangnjo.dessert_atelier.domain.display_product.DisplayProduct;
import com.yangnjo.dessert_atelier.domain.display_product.DisplayProductPreset;
import com.yangnjo.dessert_atelier.domain.display_product.Option;
import com.yangnjo.dessert_atelier.domain.display_product.SaleStatus;
import com.yangnjo.dessert_atelier.domain.member.Member;
import com.yangnjo.dessert_atelier.domain.react.Review;
import com.yangnjo.dessert_atelier.domain.react.ReviewImage;
import com.yangnjo.dessert_atelier.domain.react.ReviewOrigin;
import com.yangnjo.dessert_atelier.domain.react.ReviewStatus;
import com.yangnjo.dessert_atelier.domain_service.react.dto.ReviewCreateDto;
import com.yangnjo.dessert_atelier.domain_service.react.dto.ReviewUpdateDto;
import com.yangnjo.dessert_atelier.domain_service.react.exception.ReviewNonAuthException;
import com.yangnjo.dessert_atelier.domain_service.react.impl.ReviewCommandServiceImpl;
import com.yangnjo.dessert_atelier.repository.DisplayProductRepository;
import com.yangnjo.dessert_atelier.repository.MemberRepository;
import com.yangnjo.dessert_atelier.repository.OptionRepository;
import com.yangnjo.dessert_atelier.repository.ReviewImageRepository;
import com.yangnjo.dessert_atelier.repository.ReviewRepository;

class ReviewCommandServiceTest {

    @InjectMocks
    private ReviewCommandServiceImpl reviewCommandService;

    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private ReviewImageRepository reviewImageRepository;
    @Mock
    private DisplayProductRepository displayProductRepository;
    @Mock
    private OptionRepository optionRepository;
    @Mock
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createMemberReview_성공() {
        // Given
        Long dpId = 1L;
        Long memberId = 1L;
        Map<String, String> imageUrls = new HashMap<>();
        imageUrls.put("main", "http://example.com/image.jpg");
        List<Long> optionIds = Arrays.asList(1L, 2L);
        ReviewCreateDto dto = new ReviewCreateDto(dpId, memberId, imageUrls, optionIds, 5, "Great product!", ReviewOrigin.THIS);

        DisplayProduct displayProduct = new DisplayProduct("테스트 제품", "설명", "thumb.jpg", SaleStatus.ON_SALE);
        DisplayProductPreset dpp = DisplayProductPreset.createDefaultDPP(displayProduct, null, null, null, 0, 0, null, null);
        Member member = new Member("test@example.com", "password", "Test User", "1234567890", null);
        Option option1 = new Option(dpp, 10, "테스트 옵션1", 100, 1);
        Option option2 = new Option(dpp, 10, "테스트 옵션2", 100, 1);
        List<Option> options = Arrays.asList(option1, option2);
        ReviewImage reviewImage = new ReviewImage(imageUrls);
        Review review = mock(Review.class);

        when(displayProductRepository.findById(dpId)).thenReturn(Optional.of(displayProduct));
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        when(optionRepository.findAllById(optionIds)).thenReturn(options);
        when(reviewImageRepository.save(any(ReviewImage.class))).thenReturn(reviewImage);
        when(reviewRepository.save(any(Review.class))).thenReturn(review);
        when(review.getId()).thenReturn(1L);

        // When
        Long result = reviewCommandService.createMemberReview(dto);

        // Then
        assertEquals(1L, result);
        verify(displayProductRepository).findById(dpId);
        verify(memberRepository).findById(memberId);
        verify(optionRepository).findAllById(optionIds);
        verify(reviewImageRepository).save(any(ReviewImage.class));
        verify(reviewRepository).save(any(Review.class));
    }

    @Test
    void createStoreReview_성공() {
        // Given
        Long dpId = 1L;
        Map<String, String> imageUrls = new HashMap<>();
        imageUrls.put("main", "http://example.com/image.jpg");
        List<Long> optionIds = Arrays.asList(1L, 2L);
        ReviewCreateDto dto = new ReviewCreateDto(dpId, null, imageUrls, optionIds, 5, "Great product!", ReviewOrigin.NAVER_STORE);

        DisplayProduct displayProduct = new DisplayProduct("테스트 제품", "설명", "thumb.jpg", SaleStatus.ON_SALE);
        DisplayProductPreset dpp = DisplayProductPreset.createDefaultDPP(displayProduct, null, null, null, 0, 0, null, null);
        Option option1 = new Option(dpp, 10, "테스트 옵션1", 100, 1);
        Option option2 = new Option(dpp, 10, "테스트 옵션2", 100, 1);
        List<Option> options = Arrays.asList(option1, option2);
        ReviewImage reviewImage = new ReviewImage(imageUrls);
        Review review = mock(Review.class);

        when(displayProductRepository.findById(dpId)).thenReturn(Optional.of(displayProduct));
        when(optionRepository.findAllById(optionIds)).thenReturn(options);
        when(reviewImageRepository.save(any(ReviewImage.class))).thenReturn(reviewImage);
        when(reviewRepository.save(any(Review.class))).thenReturn(review);
        when(review.getId()).thenReturn(1L);

        // When
        Long result = reviewCommandService.createStoreReview(dto);

        // Then
        assertEquals(1L, result);
        verify(displayProductRepository).findById(dpId);
        verify(optionRepository).findAllById(optionIds);
        verify(reviewImageRepository).save(any(ReviewImage.class));
        verify(reviewRepository).save(any(Review.class));
    }

    @Test
    void updateReview_성공() {
        // Given
        Long reviewId = 1L;
        Long memberId = 1L;
        String newComment = "Updated comment";
        ReviewUpdateDto dto = new ReviewUpdateDto(reviewId, memberId, newComment);

        Review review = mock(Review.class);
        Member member = new Member("test@example.com", "password", "Test User", "1234567890", null);
        member.setIdToTest(memberId);

        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));
        when(memberRepository.existsById(memberId)).thenReturn(true);
        when(review.getMember()).thenReturn(member);

        // When
        reviewCommandService.updateReview(dto);

        // Then
        verify(reviewRepository).findById(reviewId);
        verify(memberRepository).existsById(memberId);
        verify(review).setComment(newComment);
    }

    @Test
    void updateReview_권한없음_실패() {
        // Given
        Long reviewId = 1L;
        Long memberId = 1L;
        Long differentMemberId = 2L;
        String newComment = "Updated comment";
        ReviewUpdateDto dto = new ReviewUpdateDto(reviewId, differentMemberId, newComment);

        Review review = mock(Review.class);
        Member member = new Member("test@example.com", "password", "Test User", "1234567890", null);
        member.setIdToTest(memberId);

        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));
        when(memberRepository.existsById(differentMemberId)).thenReturn(true);
        when(review.getMember()).thenReturn(member);

        // When & Then
        assertThrows(ReviewNonAuthException.class, () -> reviewCommandService.updateReview(dto));
    }

    @Test
    void updateReviewStatus_성공() {
        // Given
        Long reviewId = 1L;
        ReviewStatus newStatus = ReviewStatus.HIDE;

        Review review = mock(Review.class);
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));

        // When
        reviewCommandService.updateReviewStatus(reviewId, newStatus);

        // Then
        verify(reviewRepository).findById(reviewId);
        verify(review).setReviewStatus(newStatus);
    }

    @Test
    void deleteReview_성공() {
        // Given
        Long reviewId = 1L;
        Long memberId = 1L;

        Review review = mock(Review.class);
        Member member = new Member("test@example.com", "password", "Test User", "1234567890", null);
        member.setIdToTest(memberId);

        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));
        when(memberRepository.existsById(memberId)).thenReturn(true);
        when(review.getMember()).thenReturn(member);

        // When
        reviewCommandService.deleteReview(reviewId, memberId);

        // Then
        verify(reviewRepository).findById(reviewId);
        verify(memberRepository).existsById(memberId);
        verify(reviewRepository).deleteById(reviewId);
    }

    @Test
    void deleteReview_권한없음_실패() {
        // Given
        Long reviewId = 1L;
        Long memberId = 1L;
        Long differentMemberId = 2L;

        Review review = mock(Review.class);
        Member member = new Member("test@example.com", "password", "Test User", "1234567890", null);
        member.setIdToTest(memberId);

        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));
        when(memberRepository.existsById(differentMemberId)).thenReturn(true);
        when(review.getMember()).thenReturn(member);

        // When & Then
        assertThrows(ReviewNonAuthException.class, () -> reviewCommandService.deleteReview(reviewId, differentMemberId));
    }
}