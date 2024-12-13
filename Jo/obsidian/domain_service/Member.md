## 멤버 사이드
C : join
R : 
	단 건 구체 조회 (ByMemberEmail)
	단 건 구체 조회 (ByMemberId)
	단 건 간단 조회 (ByMemberId)
U :
	상태 변경 (탈퇴)
	정보 변경

## 어드민 사이드
C : join
R : 
	단 건 구체 조회 (ByMemberEmail)
	단 건 구체 조회 (ByMemberId)
	단 건 간단 조회 (ByMemberId)
	다 건 간단 조회 (ByStatus)
U :
	멤버 상태 변경
	상태 변경 (탈퇴)
	정보 변경

## 서버 사이드 
R : 다 건 간단 조회 (ByStatus)
==D: 삭제 (탈퇴 처리)==

~~~ 
MEMO

서버 사이드에서 주기적으로 멤버의 비활성화 된 날짜를 파악하여 돌면서 삭제(탈퇴) 처리한다.
~~~
