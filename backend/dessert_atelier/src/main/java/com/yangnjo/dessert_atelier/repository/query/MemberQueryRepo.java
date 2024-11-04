package com.yangnjo.dessert_atelier.repository.query;

import java.util.List;

import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.domain.member.MemberOrigin;
import com.yangnjo.dessert_atelier.domain.member.MemberStatus;
import com.yangnjo.dessert_atelier.repository.dto.MemberDto;
import com.yangnjo.dessert_atelier.repository.dto.MemberSimpleDto;

public interface MemberQueryRepo {

	MemberSimpleDto findMemberById(Long id);

	MemberDto findByMemberStatusAndMemberOrigin(MemberStatus memberStatus, MemberOrigin memberOrigin,
			PageOption pageOption);

	List<MemberSimpleDto> findSimplesByMemberStatusAndMemberOrigin(MemberStatus memberStatus,
			MemberOrigin memberOrigin, PageOption pageOption);

	Long countSimplesByMemberStatusAndMemberOrigin(MemberStatus memberStatus, MemberOrigin memberOrigin);

}