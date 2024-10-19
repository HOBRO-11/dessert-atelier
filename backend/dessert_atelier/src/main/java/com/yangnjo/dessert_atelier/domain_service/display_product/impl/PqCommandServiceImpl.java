package com.yangnjo.dessert_atelier.domain_service.display_product.impl;

import org.springframework.stereotype.Service;

import com.yangnjo.dessert_atelier.domain.display_product.Option;
import com.yangnjo.dessert_atelier.domain.display_product.ProductQuantity;
import com.yangnjo.dessert_atelier.domain.product.Product;
import com.yangnjo.dessert_atelier.domain_service.display_product.ProductQuantityCommandService;
import com.yangnjo.dessert_atelier.domain_service.display_product.dto.ProductQuantityCreateDto;
import com.yangnjo.dessert_atelier.domain_service.display_product.exception.OptionNotFoundException;
import com.yangnjo.dessert_atelier.domain_service.product.exception.ProductNotFoundException;
import com.yangnjo.dessert_atelier.repository.OptionRepository;
import com.yangnjo.dessert_atelier.repository.ProductQuantityRepository;
import com.yangnjo.dessert_atelier.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PqCommandServiceImpl implements ProductQuantityCommandService {

  private final ProductQuantityRepository productQuantityRepository;
  private final ProductRepository productRepository;
  private final OptionRepository optionRepository;

  @Override
  public Long createProductQuantity(final ProductQuantityCreateDto dto) {
    Product product = findProductById(dto.getProductId());
    Option option = findOptionById(dto.getOptionId());
    ProductQuantity pq = dto.toEntity(product, option);
    ProductQuantity save = productQuantityRepository.save(pq);
    return save.getId();
  }

  /*
   * batch 전용 함수
   */
  @Override
  public void deleteProductQuantity(Long productQuantityId) {
    productQuantityRepository.deleteById(productQuantityId);
  }

  private Option findOptionById(Long optionId) {
    return optionRepository.findById(optionId).orElseThrow(OptionNotFoundException::new);
  }

  private Product findProductById(Long productId) {
    return productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException());
  }

}
