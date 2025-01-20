package com.yangnjo.dessert_atelier.react.domain.domain_service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yangnjo.dessert_atelier.react.domain.domain_service.ProductQnaService;
import com.yangnjo.dessert_atelier.react.domain.entity.ProductQnAStatus;
import com.yangnjo.dessert_atelier.react.domain.entity.ProductQna;
import com.yangnjo.dessert_atelier.react.domain.entity.ProductReact;
import com.yangnjo.dessert_atelier.react.domain.repository.ProductQnaRepository;
import com.yangnjo.dessert_atelier.react.domain.repository.ProductReactRepository;
import com.yangnjo.dessert_atelier.react.dto.ProductQnaCreateDto;
import com.yangnjo.dessert_atelier.react.exception.ProductQnaNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = false)
public class ProductQnaServiceV1 implements ProductQnaService {

    private final ProductReactRepository productReactRepository;
    private final ProductQnaRepository productQnaRepo;

    @Override
    public Long createByMember(final ProductQnaCreateDto dto) {
        ProductReact productReact = findProductReactById(dto.getProductReactId());

        ProductQna qna = dto.toMemberQnAEntity(productReact);
        ProductQna savedQnA = productQnaRepo.save(qna);
        return savedQnA.getId();
    }

    @Override
    public Long createByGuest(final ProductQnaCreateDto dto) {
        ProductReact productReact = findProductReactById(dto.getProductReactId());

        ProductQna qna = dto.toGuestQnAEntity(productReact);
        ProductQna savedQnA = productQnaRepo.save(qna);
        return savedQnA.getId();
    }

    @Override
    public void answer(Long qnaId, String answer) {
        ProductQna qna = findQnaById(qnaId);
        qna.setAnswer(answer);
    }

    @Override
    public void removeAnswer(Long memberId, Long qnaId) {
        ProductQna qna = findQnaById(qnaId);
        qna.setAnswer(null);
    }

    @Override
    public void updateStatus(Long memberId, Long qnaId, ProductQnAStatus status) {
        ProductQna qna = findQnaById(qnaId);
        qna.setStatus(status);
    }

    @Override
    public void delete(Long qnaId) {
        findQnaById(qnaId);
        productQnaRepo.deleteById(qnaId);
    }

    private ProductQna findQnaById(Long qnaId) {
        return productQnaRepo.findById(qnaId).orElseThrow(() -> new ProductQnaNotFoundException());
    }

    private ProductReact findProductReactById(final Long qnaId) {
        return productReactRepository.findById(qnaId).orElseThrow(() -> new ProductQnaNotFoundException());
    }

}
