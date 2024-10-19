package com.yangnjo.dessert_atelier.domain_service.member;

import org.springframework.data.domain.Page;

import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.domain.member.MemberOrigin;
import com.yangnjo.dessert_atelier.domain.member.MemberStatus;
import com.yangnjo.dessert_atelier.repository.dto.MemberDto;
import com.yangnjo.dessert_atelier.repository.dto.MemberSimpleDto;

public interface MemberQueryService {

  MemberDto getMemberByStatusAndOrigin(MemberStatus status, MemberOrigin origin, PageOption pageOption);

  Page<MemberSimpleDto> getSimpleMembersByStatusAndOrigin(MemberStatus status, MemberOrigin origin,
      PageOption pageOption);

}