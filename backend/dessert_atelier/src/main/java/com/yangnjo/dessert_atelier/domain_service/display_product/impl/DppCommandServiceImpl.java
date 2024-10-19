package com.yangnjo.dessert_atelier.domain_service.display_product.impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.yangnjo.dessert_atelier.domain.display_product.DisplayProduct;
import com.yangnjo.dessert_atelier.domain.display_product.DisplayProductPreset;
import com.yangnjo.dessert_atelier.domain_service.display_product.DisplayProductPresetCommandService;
import com.yangnjo.dessert_atelier.domain_service.display_product.dto.DppCreateDto;
import com.yangnjo.dessert_atelier.domain_service.display_product.dto.DppUpdateDto;
import com.yangnjo.dessert_atelier.domain_service.display_product.exception.DisplayProductNotFountException;
import com.yangnjo.dessert_atelier.domain_service.display_product.exception.DisplayProductPresetNotFountException;
import com.yangnjo.dessert_atelier.domain_service.display_product.exception.DppAlreadyExistException;
import com.yangnjo.dessert_atelier.repository.DisplayProductPresetRepository;
import com.yangnjo.dessert_atelier.repository.DisplayProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = false)
public class DppCommandServiceImpl implements DisplayProductPresetCommandService{

  private final DisplayProductPresetRepository displayProductPresetRepository;
  private final DisplayProductRepository displayProductRepository;

  @Override
  public Long createDefaultDPP(final DppCreateDto dto) {
    Long dpId = dto.getDpId();
    String naming = dto.getNaming();

    DisplayProduct dp = findDisplayProductById(dpId);

    checkUniqueNaming(dpId, naming);

    DisplayProductPreset dpp = dto.toDefaultDppEntity(dp);
    DisplayProductPreset savedDPP = displayProductPresetRepository.save(dpp);
    return savedDPP.getId();
  }

  @Override
  public Long createCustomDPP(final DppCreateDto dto) {
    Long dpId = dto.getDpId();
    String naming = dto.getNaming();

    DisplayProduct dp = findDisplayProductById(dpId);

    checkUniqueNaming(dpId, naming);

    DisplayProductPreset dpp = dto.toCustomDppEntity(dp);
    DisplayProductPreset savedDPP = displayProductPresetRepository.save(dpp);
    return savedDPP.getId();
  }

  @Override
  public void updateDPP(final DppUpdateDto dto) {
    Long dppId = dto.getDppId();
    String naming = dto.getNaming();
    String content = dto.getContent();
    Integer percentDiscount = dto.getPercentDiscount();

    DisplayProductPreset dpp = findDppById(dppId);

    if (StringUtils.hasText(naming)) {
      Long dpId = dpp.getDisplayProduct().getId();
      checkUniqueNaming(dpId, naming);
      dpp.setNaming(naming);
    }
    if (StringUtils.hasText(content)) {
      dpp.setContent(content);
    }
    if (percentDiscount != null) {
      dpp.setPercentDiscount(percentDiscount);
    }
  }

  @Override
  public void updateDppDefault(Long dppId) {
    DisplayProductPreset dpp = findDppById(dppId);
    dpp.setDefault(true);
  }

  @Override
  public void updateDppOptionLayer(Long dppId, Integer optionLayer) {
    DisplayProductPreset dpp = findDppById(dppId);
    dpp.setOptionLayer(optionLayer);
  }

  @Override
  public void updateDppDateTime(Long dppId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
    DisplayProductPreset dpp = findDppById(dppId);
    dpp.setStartDateTime(startDateTime);
    dpp.setEndDateTime(endDateTime);
  }

  /*
   * batch 전용 함수
   */
  @Override
  public void deleteDisplayProductPreset(Long dppId) {
    displayProductPresetRepository.deleteById(dppId);
  }

  private void checkUniqueNaming(Long dpId, String naming) {
    boolean isExist = displayProductPresetRepository.existsByDisplayProductIdAndNaming(dpId, naming);
    if (isExist) {
      throw new DppAlreadyExistException("해당 별칭은 이미 프리셋에 존재합니다");
    }
  }

  private DisplayProduct findDisplayProductById(Long dpId) {
    DisplayProduct dp = displayProductRepository.findById(dpId)
        .orElseThrow(() -> new DisplayProductNotFountException());
    return dp;
  }

  private DisplayProductPreset findDppById(Long dppId) {
    DisplayProductPreset dpp = displayProductPresetRepository.findById(dppId)
        .orElseThrow(() -> new DisplayProductPresetNotFountException());
    return dpp;
  }

}
