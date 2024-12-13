## 멤버 사이드
R : orderCode를 이용한 단 건 조회(orderCode)
## 어드민 사이드
C : excel을 이용한 주문-배송 매칭 (상태는 PREPARING) (수동) 
R : 
	다 건 조회 (ByOrderCode)
	단 건 조회 (ByOrderCode)
	단 건 조회 (ByDelivery)
U : 
	다 건 상태 변경 
		 배송 접수 -> 배송 완료
D : 
	배송 삭제 
	(주문과 이미 매칭된 배송이 있는 경우, 위 Create method에서 errorMessage를 반환한다. 
	이때 Delivery record를 수동으로 삭제 해줘야한다.) 

## 서버 사이드
C : excel을 이용한 주문-배송 매칭 (상태는 PREPARING) (자동) 
U : 배송을 시작했는지, 도착하였는지 판단 후 다 건 상태 변경

~~~ 
MEMO


~~~
