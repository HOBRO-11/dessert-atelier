package com.yangnjo.dessert_atelier.domain.service;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.yangnjo.dessert_atelier.common.dto.address.AddressDeleteDto;
import com.yangnjo.dessert_atelier.common.dto.address.AddressDto;
import com.yangnjo.dessert_atelier.common.dto.address.AddressSaveDto;
import com.yangnjo.dessert_atelier.common.dto.address.AddressSetDefaultDto;
import com.yangnjo.dessert_atelier.db.entity.Addresses;
import com.yangnjo.dessert_atelier.db.entity.Users;
import com.yangnjo.dessert_atelier.db.repository.AddressRepository;
import com.yangnjo.dessert_atelier.db.repository.UserRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class AddressServiceImplTest {

    @Autowired
    private AddressService addressService;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository usersRepository;

    @PersistenceContext
    private EntityManager em;

    private Users testUser;

    @BeforeEach
    void setUp() {
        em.clear();
        // Given: 테스트 유저 생성 및 저장
        testUser = Users.createUser("testuser@example.com", "password123", "Test User", 123456789);
        usersRepository.save(testUser);

        // Given: 테스트 주소 저장 (최대 20개 이하)
        Addresses address1 = Addresses.createAddress(testUser, "Home", "12345", "123 Main St", "John Doe", 12345678, true);
        Addresses address2 = Addresses.createAddress(testUser, "Office", "67890", "456 Elm St", "Jane Doe", 98765432,
                false);
        addressRepository.saveAll(List.of(address1, address2));

        em.flush();
    }

    @Test
    void testGetDefaultAddress() {
        // When: 기본 주소 조회
        AddressDto defaultAddress = addressService.getDefaultAddress(testUser);

        // Then: 기본 주소가 존재하는지 확인
        assertThat(defaultAddress.getNaming()).isEqualTo("Home");
    }

    @Test
    void testGetAddresses() {
        // given
        em.clear();
        JPAManagedTestUser();

        // When: 유저의 주소 목록 조회
        List<AddressDto> addresses = addressService.getAddresses(testUser);

        // Then: 주소 목록 확인
        assertThat(addresses).hasSize(2);
        assertThat(addresses.get(0).getNaming()).isEqualTo("Home");
        assertThat(addresses.get(1).getNaming()).isEqualTo("Office");
    }

    @Test
    void testSaveAddress() {
        // Given: 새 주소 DTO 생성
        AddressSaveDto saveDto = new AddressSaveDto(testUser, "Vacation Home", "33333", "789 Oak St", "John Smith",
                55556666, false);

        // When: 새 주소 저장
        AddressDto savedAddress = addressService.saveAddress(saveDto);

        em.flush();
        em.clear();

        // Then: 저장된 주소 확인
        JPAManagedTestUser();
        List<AddressDto> addresses = addressService.getAddresses(testUser);
        assertThat(addresses).hasSize(3);
        assertThat(savedAddress.getNaming()).isEqualTo("Vacation Home");
    }

    @Test
    void testDeleteAddress() {
        // Given: 삭제할 주소 조회
        AddressDto addressToDelete = addressService.getAddresses(testUser).get(1); // Office 주소

        // When: 주소 삭제
        AddressDeleteDto deleteDto = new AddressDeleteDto(testUser, addressToDelete.getId(), false);
        addressService.deleteAddress(deleteDto);

        em.flush();
        em.clear();

        // Then: 주소가 삭제되었는지 확인
        JPAManagedTestUser();
        List<AddressDto> addresses = addressService.getAddresses(testUser);
        assertThat(addresses).hasSize(1);
        assertThat(addresses.get(0).getNaming()).isEqualTo("Home");
    }

    @Test
    void testSetDefaultAddress() {
        // Given: 기본 주소 변경 DTO 생성
        AddressDto newDefaultAddress = addressService.getAddresses(testUser).get(1); // Office 주소
        String newDefaultAddressNaming = newDefaultAddress.getNaming();
        AddressSetDefaultDto setDefaultDto = new AddressSetDefaultDto(testUser, newDefaultAddress.getId());

        // When: 기본 주소 설정
        addressService.setDefaultAddress(setDefaultDto);

        em.flush();
        em.clear();

        // Then: 기본 주소가 변경되었는지 확인
        AddressDto defaultAddress = addressService.getDefaultAddress(testUser);
        assertThat(defaultAddress.getNaming()).isEqualTo(newDefaultAddressNaming);
    }

    private void JPAManagedTestUser() {
        testUser = usersRepository.findByEmail("testuser@example.com").get();
    }

}