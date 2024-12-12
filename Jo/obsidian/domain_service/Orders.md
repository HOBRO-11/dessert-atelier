## 멤버 사이드
- C : 
	생성 (상태는 PAYMENT_IN_PROGRESS) (member , guest)
- R :
	다 건 간단 조회(ByOrderCode)
	단 건 구체 조회(orderCode)
- U: 
	주문 상태 변경은 Orders 레코드의 현 상태에 맞게 달라진다. (member, guest)
	OrderedOption의 상태 변경 (부분 취소를 위해)

| 설명   | name                | into                      | 추가조건    | 부가기능                    |
| ---- | ------------------- | ------------------------- | ------- | ----------------------- |
| 결제대기 | PAYMENT_IN_PROGRESS |                           |         |                         |
| 결제완료 | PAYMENT_COMPLETED   | REQUEST_CANCEL            | 배송 접수 전 | 취소요청                    |
| 취소요청 | REQUEST_CANCEL      |                           |         |                         |
| 취소   | CANCEL              |                           |         |                         |
| 완료대기 | WATING_COMPLETED    | COMPLETED, REQUEST_REFUND |         | 일주일이 지나면  COMPLETED로 변경 |
| 완료   | COMPLETED           |                           |         |                         |
| 환불요청 | REQUEST_REFUND      | COMPLETED                 |         |                         |
| 환불완료 | REFUND              |                           |         |                         |
_\* 어드민 서버가 OFF 상태에서 REQUEST_CANCEL 에 대해선 자동으로 CANCEL으로 상태 전환, 자동 환불처리한다. 부가적으로 더 많은 환불 정책에 대해선 적용할 예정_

## 어드민 사이드
- R : 
	다 건 간단 조회(ByStatus)
	단 건 구체 조회 (ByOrderCode)
- U : 
	주문 상태 변경은 Orders 레코드의 현 상태에 맞게 달라진다.
	OrderedOption의 상태 변경 (부분 취소를 위해)

| 설명   | name                | into   | 부가 기능          |
| ---- | ------------------- | ------ | -------------- |
| 결제대기 | PAYMENT_IN_PROGRESS |        |                |
| 결제완료 | PAYMENT_COMPLETED   | CANCEL | 배송취소, 취소요청 접수됨 |
| 취소요청 | REQUEST_CANCEL      | CANCEL | 배송취소, 취소요청 접수됨 |
| 취소   | CANCEL              |        |                |
| 완료대기 | WATING_COMPLETED    |        |                |
| 완료   | COMPLETED           | REFUND | 환불요청 접수됨       |
| 환불요청 | REQUEST_REFUND      | REFUND | 환불요청 접수됨       |
| 환불완료 | REFUND              |        |                |

## 서버 사이드
- U : 
	Payment 레코드가 생성되면 aws lambda가 이벤트를 읽고 상태를 변경한다. 