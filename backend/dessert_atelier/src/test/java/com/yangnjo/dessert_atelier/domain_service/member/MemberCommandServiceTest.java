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

import com.yangnjo.dessert_atelier.domain.member.Member;
import com.yangnjo.dessert_atelier.domain.member.MemberOrigin;
import com.yangnjo.dessert_atelier.domain.member.MemberStatus;
import com.yangnjo.dessert_atelier.domain_service.member.dto.MemberCreateDto;
import com.yangnjo.dessert_atelier.domain_service.member.dto.MemberUpdateDto;
import com.yangnjo.dessert_atelier.domain_service.member.exception.MemberAlreadyExistException;
import com.yangnjo.dessert_atelier.domain_service.member.exception.MemberNotFoundException;
import com.yangnjo.dessert_atelier.domain_service.member.impl.MemberCommandServiceImpl;
import com.yangnjo.dessert_atelier.repository.MemberRepository;

public class MemberCommandServiceTest {

    @InjectMocks
    private MemberCommandServiceImpl memberCommandService;

    @Mock
    private MemberRepository memberRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createMember_ShouldReturnMemberId_WhenValidInput() {
        // Given
        MemberCreateDto dto = new MemberCreateDto("test@example.com", "password", "Test User", "1234567890", MemberOrigin.STORE);
        Member member = dto.toEntity();
        member.setIdToTest(1L);
        
        when(memberRepository.existsByEmail(dto.getEmail())).thenReturn(false);
        when(memberRepository.save(any(Member.class))).thenReturn(member);

        // When
        Long memberId = memberCommandService.createMember(dto);

        // Then
        assertThat(memberId).isEqualTo(1L);
        verify(memberRepository).existsByEmail(dto.getEmail());
        verify(memberRepository).save(any(Member.class));
    }

    @Test
    public void createMember_ShouldThrowException_WhenEmailAlreadyExists() {
        // Given
        MemberCreateDto dto = new MemberCreateDto("existing@example.com", "password", "Test User", "1234567890", MemberOrigin.STORE);
        when(memberRepository.existsByEmail(dto.getEmail())).thenReturn(true);

        // When & Then
        assertThatThrownBy(() -> memberCommandService.createMember(dto))
            .isInstanceOf(MemberAlreadyExistException.class);
        verify(memberRepository).existsByEmail(dto.getEmail());
        verify(memberRepository, never()).save(any(Member.class));
    }

    @Test
    public void updateMemberInfo_ShouldUpdateNameAndPhone_WhenBothProvided() {
        // Given
        Long memberId = 1L;
        MemberUpdateDto dto = new MemberUpdateDto(memberId, "New Name", "9876543210");
        Member existingMember = new Member("test@example.com", "password", "Old Name", "1234567890", MemberOrigin.STORE);
        existingMember.setIdToTest(memberId);

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(existingMember));

        // When
        memberCommandService.updateMemberInfo(dto);

        // Then
        assertThat(existingMember.getName()).isEqualTo("New Name");
        assertThat(existingMember.getPhone()).isEqualTo("9876543210");
        verify(memberRepository).findById(memberId);
    }

    @Test
    public void updateMemberInfo_ShouldUpdateOnlyName_WhenOnlyNameProvided() {
        // Given
        Long memberId = 1L;
        MemberUpdateDto dto = new MemberUpdateDto(memberId, "New Name", null);
        Member existingMember = new Member("test@example.com", "password", "Old Name", "1234567890", MemberOrigin.STORE);
        existingMember.setIdToTest(memberId);

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(existingMember));

        // When
        memberCommandService.updateMemberInfo(dto);

        // Then
        assertThat(existingMember.getName()).isEqualTo("New Name");
        assertThat(existingMember.getPhone()).isEqualTo("1234567890");
        verify(memberRepository).findById(memberId);
    }

    @Test
    public void updateMemberInfo_ShouldThrowException_WhenMemberNotFound() {
        // Given
        Long memberId = 1L;
        MemberUpdateDto dto = new MemberUpdateDto(memberId, "New Name", "9876543210");
        when(memberRepository.findById(memberId)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> memberCommandService.updateMemberInfo(dto))
            .isInstanceOf(MemberNotFoundException.class);
        verify(memberRepository).findById(memberId);
    }

    @Test
    public void updateMemberPassword_ShouldUpdatePassword_WhenValidInput() {
        // Given
        Long memberId = 1L;
        String newPassword = "newPassword";
        Member existingMember = new Member("test@example.com", "oldPassword", "Test User", "1234567890", MemberOrigin.STORE);
        existingMember.setIdToTest(memberId);

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(existingMember));

        // When
        memberCommandService.updateMemberPassword(memberId, newPassword);

        // Then
        assertThat(existingMember.getPassword()).isEqualTo(newPassword);
        verify(memberRepository).findById(memberId);
    }

    @Test
    public void updateMemberPassword_ShouldThrowException_WhenMemberNotFound() {
        // Given
        Long memberId = 1L;
        String newPassword = "newPassword";
        when(memberRepository.findById(memberId)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> memberCommandService.updateMemberPassword(memberId, newPassword))
            .isInstanceOf(MemberNotFoundException.class);
        verify(memberRepository).findById(memberId);
    }

    @Test
    public void banMember_ShouldChangeMemberStatusToBan_WhenValidInput() {
        // Given
        Long memberId = 1L;
        Member existingMember = new Member("test@example.com", "password", "Test User", "1234567890", MemberOrigin.STORE);
        existingMember.setIdToTest(memberId);

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(existingMember));

        // When
        memberCommandService.banMember(memberId);

        // Then
        assertThat(existingMember.getMemberStatus()).isEqualTo(MemberStatus.BAN);
        verify(memberRepository).findById(memberId);
    }

    @Test
    public void banMember_ShouldThrowException_WhenMemberNotFound() {
        // Given
        Long memberId = 1L;
        when(memberRepository.findById(memberId)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> memberCommandService.banMember(memberId))
            .isInstanceOf(MemberNotFoundException.class);
        verify(memberRepository).findById(memberId);
    }
}