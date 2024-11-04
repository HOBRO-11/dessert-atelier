package com.yangnjo.dessert_atelier.domain_service.admin;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.yangnjo.dessert_atelier.domain.admin.AdminRole;
import com.yangnjo.dessert_atelier.domain.admin.AdminStatus;
import com.yangnjo.dessert_atelier.domain.admin.StoreAdmin;
import com.yangnjo.dessert_atelier.domain_service.admin.dto.StoreAdminCreateDto;
import com.yangnjo.dessert_atelier.domain_service.admin.exception.StoreAdminMustGeOneException;
import com.yangnjo.dessert_atelier.domain_service.admin.exception.StoreAdminNotFoundException;
import com.yangnjo.dessert_atelier.domain_service.admin.impl.SaCommandServiceImpl;
import com.yangnjo.dessert_atelier.repository.StoreAdminRepository;

class StoreAdminCommandServiceTest {

  @Mock
  private StoreAdminRepository storeAdminRepository;

  @InjectMocks
  private SaCommandServiceImpl storeAdminCommandService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void createStoreAdmin_성공() {
    // Given
    StoreAdminCreateDto dto = new StoreAdminCreateDto("test@example.com", "password", "010-1234-5678");
    StoreAdmin savedStoreAdmin = new StoreAdmin("test@example.com", "password", "010-1234-5678");
    savedStoreAdmin.setAdminStatus(AdminStatus.PENDING);
    savedStoreAdmin.setAdminRole(AdminRole.NONE);
    savedStoreAdmin.setIdToTest(1L);

    when(storeAdminRepository.save(any(StoreAdmin.class))).thenReturn(savedStoreAdmin);

    // When
    Long result = storeAdminCommandService.createStoreAdmin(dto);

    // Then
    assertEquals(1L, result);
    verify(storeAdminRepository, times(1)).save(any(StoreAdmin.class));
  }

  @Test
  void updateStoreAdminPhone_성공() {
    // Given
    Long adminId = 1L;
    String newPhone = "010-9876-5432";
    StoreAdmin storeAdmin = new StoreAdmin("test@example.com", "password", "010-1234-5678");
    storeAdmin.setAdminStatus(AdminStatus.ACTIVE);
    storeAdmin.setAdminRole(AdminRole.STAFF);
    storeAdmin.setIdToTest(adminId);

    when(storeAdminRepository.findById(adminId)).thenReturn(Optional.of(storeAdmin));

    // When
    storeAdminCommandService.updateStoreAdminPhone(adminId, newPhone);

    // Then
    assertEquals(newPhone, storeAdmin.getPhone());
    verify(storeAdminRepository, times(1)).findById(adminId);
  }

  @Test
  void updateStoreAdminPhone_관리자없음_예외발생() {
    // Given
    Long adminId = 1L;
    String newPhone = "010-9876-5432";

    when(storeAdminRepository.findById(adminId)).thenReturn(Optional.empty());

    // When & Then
    assertThrows(StoreAdminNotFoundException.class, () -> {
      storeAdminCommandService.updateStoreAdminPhone(adminId, newPhone);
    });
  }

  @Test
  void deleteStoreAdmin_성공() {
    // Given
    Long adminId = 1L;
    StoreAdmin storeAdmin = new StoreAdmin("test@example.com", "password", "010-1234-5678");
    storeAdmin.setAdminStatus(AdminStatus.ACTIVE);
    storeAdmin.setAdminRole(AdminRole.STAFF);
    storeAdmin.setIdToTest(adminId);

    when(storeAdminRepository.findById(adminId)).thenReturn(Optional.of(storeAdmin));
    when(storeAdminRepository.countByAdminRole(AdminRole.OWNER)).thenReturn(2L);

    // When
    storeAdminCommandService.deleteStoreAdmin(adminId);

    // Then
    verify(storeAdminRepository, times(1)).deleteById(adminId);
  }

  @Test
  void deleteStoreAdmin_마지막소유자_예외발생() {
    // Given
    Long adminId = 1L;
    StoreAdmin storeAdmin = new StoreAdmin("test@example.com", "password", "010-1234-5678");
    storeAdmin.setIdToTest(adminId);
    storeAdmin.setAdminStatus(AdminStatus.ACTIVE);
    storeAdmin.setAdminRole(AdminRole.OWNER);

    when(storeAdminRepository.findById(adminId)).thenReturn(Optional.of(storeAdmin));
    when(storeAdminRepository.countByAdminRole(AdminRole.OWNER)).thenReturn(1L);

    // When & Then
    assertThrows(StoreAdminMustGeOneException.class, () -> {
      storeAdminCommandService.deleteStoreAdmin(adminId);
    });
  }
}
