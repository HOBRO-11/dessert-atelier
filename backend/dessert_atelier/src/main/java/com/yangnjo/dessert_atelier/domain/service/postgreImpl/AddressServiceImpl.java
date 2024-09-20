package com.yangnjo.dessert_atelier.domain.service.postgreImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yangnjo.dessert_atelier.common.dto.address.AddressDeleteDto;
import com.yangnjo.dessert_atelier.common.dto.address.AddressDto;
import com.yangnjo.dessert_atelier.common.dto.address.AddressSaveDto;
import com.yangnjo.dessert_atelier.common.dto.address.AddressSetDefaultDto;
import com.yangnjo.dessert_atelier.db.entity.Address;
import com.yangnjo.dessert_atelier.db.entity.Users;
import com.yangnjo.dessert_atelier.db.repository.AddressRepository;
import com.yangnjo.dessert_atelier.domain.service.AddressService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository; // JPA Repository

    @Override
    public AddressDto getDefaultAddress(Users user) {
        // TODO 기본 주소가 없는 경우
        return addressRepository.findDefaultAddress(user).orElseThrow(() -> new RuntimeException());
    }

    @Override
    public List<AddressDto> getAddresses(Users user) {
        return user.getAddresses().stream().map(AddressDto::toDto).collect(Collectors.toList());
    }

    @Override
    public AddressDto saveAddress(AddressSaveDto dto) {
        if (dto.getUsers().getAddresses().size() > 20) {
            // TODO 주소는 유저당 최대 20개까지 저장할 수 있음
            throw new RuntimeException();
        }

        checkExistNaming(dto.getUsers(), dto.getNaming());

        Address address = dto.toEntity();
        return AddressDto.toDto(addressRepository.save(address));
    }

    /*
     * // TODO 아래 런타임 예외 정의하기
     * throw runtimeException, if this naming is already exist in the
     * users.addresses
     */
    private void checkExistNaming(Users user, final String naming) {
        user.getAddresses()
                .forEach(t -> {
                    if (t.getNaming().equals(naming)) {
                        // TODO 이런 naming을 가진 주소를 해당 유저가 가지고 있음
                        throw new RuntimeException();
                    }
                });
    }

    @Override
    public void deleteAddress(AddressDeleteDto dto) {
        if (dto.isDefault()) {
            // TODO 기본 주소는 지울 수 없음
            throw new RuntimeException();
        }
        if (dto.getUsers().getAddresses().size() == 1) {
            // TODO 주소는 1개 이상이어야함
            throw new RuntimeException();
        }

        checkUsersAddress(dto.getUsers(), dto.getAddressId());

        addressRepository.deleteById(dto.getAddressId());
    }

    private void checkUsersAddress(Users users, Long addressId) {
        boolean isNotExist = users.getAddresses().stream().noneMatch(t -> t.getId() == addressId);
        if (isNotExist) {
            // TODO 이 주소는 해당 유저의 것이 아님
            throw new RuntimeException();
        }
    }

    @Override
    @Transactional
    public void setDefaultAddress(AddressSetDefaultDto dto) {
        List<Address> addresses = dto.getUsers().getAddresses();

        for (Address address : addresses) {
            if (address.getId() == dto.getAddressId()) {
                address.setDefaultAddress();
            } else {
                address.releaseDefaultAddress();
            }
        }
    }
}