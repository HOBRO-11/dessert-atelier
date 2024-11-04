package com.yangnjo.dessert_atelier.domain_service.member;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.yangnjo.dessert_atelier.domain.member.Address;
import com.yangnjo.dessert_atelier.domain.member.Member;
import com.yangnjo.dessert_atelier.domain.member.MemberRole;
import com.yangnjo.dessert_atelier.domain_service.member.dto.AddressCreateDto;
import com.yangnjo.dessert_atelier.domain_service.member.dto.AddressUpdateDto;
import com.yangnjo.dessert_atelier.domain_service.member.exception.AddressCountMaxException;
import com.yangnjo.dessert_atelier.domain_service.member.impl.AddressCommandServiceImpl;
import com.yangnjo.dessert_atelier.repository.AddressRepository;
import com.yangnjo.dessert_atelier.repository.MemberRepository;

public class AddressCommandServiceTest {

  @InjectMocks
  private AddressCommandServiceImpl addressCommandService;

  @Mock
  private AddressRepository addressRepository;

  @Mock
  private MemberRepository memberRepository;

  private Member member;
  private Address address;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    member = new Member("test@example.com", "password", "Test User", "1234567890", MemberRole.MEMBER, null);
    address = new Address("Home", "12345", "Detail Address", "Receiver", "1234567890", false);
    member.addAddress(address);
  }

  @Test
  public void createAddress_ShouldReturnAddressId() {
    AddressCreateDto dto = new AddressCreateDto(1L, "Home", "12345", "Detail Address", "Receiver", "1234567890", false);
    when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
    when(addressRepository.save(any(Address.class))).thenReturn(address);

    Long addressId = addressCommandService.createAddress(dto);

    verify(memberRepository).findById(1L);
    verify(addressRepository).save(any(Address.class));
    assertThat(addressId).isEqualTo(address.getId());
  }

  @Test
  public void updateAddress_ShouldUpdateAddress() {
    AddressUpdateDto dto = new AddressUpdateDto(address.getId(), member.getId(), "New Home", null);
    when(addressRepository.findById(address.getId())).thenReturn(Optional.of(address));
    when(memberRepository.existsById(member.getId())).thenReturn(true);

    addressCommandService.updateAddress(dto);

    assertThat("New Home").isEqualTo(address.getNaming());
  }

  @Test
  public void setDefaultAddress_ShouldSetDefaultAddress() {
    when(memberRepository.findById(member.getId())).thenReturn(Optional.of(member));
    when(addressRepository.findById(address.getId())).thenReturn(Optional.of(address));

    addressCommandService.setDefaultAddress(address.getId(), member.getId(), true);

    assertThat(address.isDefault()).isTrue();
  }

  @Test
  public void deleteAddress_ShouldDeleteAddress() {
    when(addressRepository.findById(address.getId())).thenReturn(Optional.of(address));
    when(memberRepository.existsById(member.getId())).thenReturn(true);

    addressCommandService.deleteAddress(address.getId(), member.getId());

    verify(addressRepository).deleteById(address.getId());
  }

  @Test
  public void checkAddressCountLtMax_ShouldThrowException_WhenCountExceedsMax() {
    when(memberRepository.findById(member.getId())).thenReturn(Optional.of(member));
    member.addAddress(new Address("Another Address", "54321", "Detail", "Receiver", "0987654321", false));
    member.addAddress(new Address("Another Address", "54321", "Detail", "Receiver", "0987654321", false));
    // Add more addresses to exceed the limit
    for (int i = 0; i < 18; i++) {
      member.addAddress(new Address("Address " + i, "54321", "Detail", "Receiver", "0987654321", false));
    }

    assertThatExceptionOfType(AddressCountMaxException.class).isThrownBy(() -> {
      addressCommandService.checkAddressCountLtMax(member.getId());
    });

  }

}
