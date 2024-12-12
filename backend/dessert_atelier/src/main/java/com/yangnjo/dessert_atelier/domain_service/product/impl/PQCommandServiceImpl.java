package com.yangnjo.dessert_atelier.domain_service.product.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.yangnjo.dessert_atelier.domain_model.product.Option;
import com.yangnjo.dessert_atelier.domain_model.product.Product;
import com.yangnjo.dessert_atelier.domain_model.product.ProductQuantity;
import com.yangnjo.dessert_atelier.domain_service.product.ProductQuantityCommandService;
import com.yangnjo.dessert_atelier.domain_service.product.dto.PQCreateResult;
import com.yangnjo.dessert_atelier.domain_service.product.dto.PQCreateResult.PQCreateErrorMessage;
import com.yangnjo.dessert_atelier.domain_service.product.dto.ProductQuantityCreateDto;
import com.yangnjo.dessert_atelier.repository.product.OptionRepository;
import com.yangnjo.dessert_atelier.repository.product.ProductQuantityRepository;
import com.yangnjo.dessert_atelier.repository.product.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PQCommandServiceImpl implements ProductQuantityCommandService {

    private final ProductQuantityRepository pqRepository;
    private final ProductRepository productRepository;
    private final OptionRepository optionRepository;

    @Override
    public PQCreateResult create(List<ProductQuantityCreateDto> dtos) {
        List<PQCreateErrorMessage> errorMessages = new ArrayList<>();
        int totalCount = dtos.size();
        int successCount = 0;
        int errorCount = 0;

        for (ProductQuantityCreateDto dto : dtos) {
            Long optionId = dto.getOptionId();
            Long productId = dto.getProductId();
            Integer quantity = dto.getQuantity();

            if (optionId == null && productId == null) {
                errorMessages
                        .add(new PQCreateErrorMessage(optionId, productId, "optionId와 productId 가 존재하지 않습니다않습니다."));
                errorCount++;
                continue;
            }

            if (optionId == null) {
                errorMessages.add(new PQCreateErrorMessage(optionId, productId, "optionId가 존재하지 않습니다않습니다."));
                errorCount++;
                continue;
            }

            if (productId == null) {
                errorMessages.add(new PQCreateErrorMessage(optionId, productId, "productId가 존재하지 않습니다않습니다."));
                errorCount++;
                continue;
            }

            Optional<Option> optionalOption = optionRepository.findById(optionId);
            if (optionalOption.isEmpty()) {
                errorMessages.add(new PQCreateErrorMessage(optionId, productId, "option 존재하지 않습니다않습니다."));
                errorCount++;
                continue;
            }

            Optional<Product> optionalProduct = productRepository.findById(productId);
            if (optionalProduct.isEmpty()) {
                errorMessages.add(new PQCreateErrorMessage(optionId, productId, "product 존재하지 않습니다않습니다."));
                errorCount++;
                continue;
            }

            Option option = optionalOption.get();
            Product product = optionalProduct.get();
            pqRepository.save(new ProductQuantity(product, option, quantity));

            successCount++;
        }

        return new PQCreateResult(totalCount, successCount, errorCount, errorMessages);
    }

}
