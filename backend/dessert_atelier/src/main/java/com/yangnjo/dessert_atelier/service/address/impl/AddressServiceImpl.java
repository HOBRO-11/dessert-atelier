package com.yangnjo.dessert_atelier.service.address.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yangnjo.dessert_atelier.common.privacy_blur_util.PrivacyBlurUtil;
import com.yangnjo.dessert_atelier.domain_service.member.AddressCommandService;
import com.yangnjo.dessert_atelier.domain_service.member.AddressQueryService;
import com.yangnjo.dessert_atelier.domain_service.member.dto.AddressCreateDto;
import com.yangnjo.dessert_atelier.repository.member.dto.AddressDto;
import com.yangnjo.dessert_atelier.service.address.AddressService;
import com.yangnjo.dessert_atelier.service.address.dto.AddressCreateForm;
import com.yangnjo.dessert_atelier.service.address.dto.AddressResponseForm;
import com.yangnjo.dessert_atelier.service.address.dto.AddressUpdateForm;
import com.yangnjo.dessert_atelier.service.address.dto.AddressResponseForm.DestinationDetail;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressCommandService addressCommandService;
    private final AddressQueryService addressQueryService;

    @Override
    public void create(Long memberId, List<AddressCreateForm> forms) {
        long count = forms.stream().filter(form -> form.isDefault()).count();

        if (count > 1) {
            throw new IllegalArgumentException("기본 주소는 하나만 가질 수 있습니다.");
        }

        List<AddressCreateDto> dtos = forms.stream().map(AddressCreateForm::toDto).collect(Collectors.toList());

        addressCommandService.create(memberId, dtos);
    }

    @Override
    public void delete(Long memberId, Long addressId) {
        addressCommandService.delete(addressId, memberId);
    }

    @Override
    public void setDefault(Long memberId, Long addressId) {
        addressCommandService.setDefault(addressId, memberId, true);
    }

    @Override
    public void update(Long memberId, AddressUpdateForm form) {
        addressCommandService.update(form.toDto(memberId));
    }

    @Override
    public AddressResponseForm getAddresses(Long memberId) {
        List<AddressDto> dtos = addressQueryService.getAllByMemberId(memberId);

        if (dtos.isEmpty()) {
            return new AddressResponseForm();
        }

        List<DestinationDetail> dds = dtos.stream()
                .map(dto -> new DestinationDetail(dto.getNaming(),
                        PrivacyBlurUtil.maskDestination(dto.getDestination()), dto.isDefault()))
                .collect(Collectors.toList());

        return new AddressResponseForm(dds);
    }

    @Override
    public AddressResponseForm getDefaultAddress(Long memberId) {
        AddressDto dto = addressQueryService.getDefaultAddressByMemberId(memberId).orElse(null);

        if (dto == null) {
            return new AddressResponseForm();
        }

        return new AddressResponseForm(List.of(new DestinationDetail(dto.getNaming(),
                PrivacyBlurUtil.maskDestination(dto.getDestination()), true)));
    }

}
