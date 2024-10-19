package com.yangnjo.dessert_atelier.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.yangnjo.dessert_atelier.domain.display_product.DisplayProduct;
import com.yangnjo.dessert_atelier.domain.display_product.DisplayProductPreset;
import com.yangnjo.dessert_atelier.domain.display_product.SaleStatus;
import com.yangnjo.dessert_atelier.domain_service.display_product.exception.DppAlreadyExistException;
import com.yangnjo.dessert_atelier.repository.DisplayProductPresetRepository;
import com.yangnjo.dessert_atelier.repository.DisplayProductRepository;

@SpringBootTest
@ActiveProfiles("postgresql-test")
@Transactional
public class DisplayProductPresetCommandServiceIntegrationTest {

  @Autowired
  private DisplayProductRepository displayProductRepository;

  @Autowired
  private DisplayProductPresetRepository displayProductPresetRepository;

  @Test
  void checkUniqueNaming_중복되지_않은_이름() {
    // Given
    DisplayProduct dp = new DisplayProduct("테스트 제품", "설명", "thumb.jpg", SaleStatus.ON_SALE);
    displayProductRepository.save(dp);

    String uniqueNaming = "유니크한 이름";

    // When & Then
    assertDoesNotThrow(() -> {
      checkUniqueNaming(dp.getId(), uniqueNaming);
    });
  }

  @Test
  void checkUniqueNaming_중복된_이름() {
    // Given
    DisplayProduct dp = new DisplayProduct("테스트 제품", "설명", "thumb.jpg", SaleStatus.ON_SALE);
    displayProductRepository.save(dp);

    String duplicateNaming = "중복된 이름";
    DisplayProductPreset dpp = DisplayProductPreset.createDefaultDPP(dp, "중복된 이름", "thumb.jpg", "제목", 1000, 1, "내용",
        null);
    displayProductPresetRepository.save(dpp);

    // When & Then
    assertThrows(DppAlreadyExistException.class, () -> {
      checkUniqueNaming(dp.getId(), duplicateNaming);
    });
  }

  private void checkUniqueNaming(Long dpId, String naming) {
    boolean isExist = displayProductPresetRepository.existsByDisplayProductIdAndNaming(dpId, naming);
    if (isExist) {
      throw new DppAlreadyExistException("해당 별칭은 이미 프리셋에 존재합니다");
    }
  }
}