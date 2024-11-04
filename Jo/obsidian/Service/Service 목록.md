회원 / 비회원 노출 API Service 목록
### Member
- [[obsidian/Service/Member/Join.canvas|Join]] : 회원가입 - /join Get, Post
- [[obsidian/Service/Member/Login.canvas|Login]] : 로그인 - /store/login Get
- [[obsidian/Service/Member/OauthLogin.canvas|OauthLogin]] : 로그인 - /{originName}/login Get
- Info  : 회원정보 - /members Get
- Update : 정보변경 - /members Patch

### Address
- List : 주소록정보 - /addresses Get
- Create : 주소생성 - /addresses Post
- Update : 정보변경 - /addresses Patch
- SetDefault : 기본 주소 변경 - /addresses/set-default Patch

### Basket
- List : 장바구니 목록 - /baskets Get
- Add : 장바구니 추가 - /baskets Post
- Remove : 장바구니 삭제 - /basket Delete

### Orders
- [[obsidian/Service/Orders/MemberOrder.canvas|MemberOrder]] : 주문하기 - /orders Post
- [[obsidian/Service/Orders/GusetOrder.canvas|GuestOrder]] : 주문하기 - /orders/Guest Post
- List : 주문 목록 - /orders?p=&s=&d= Get
- MemberDetail : 회원 주문조회 - /orders/{주문번호} Get
- [[obsidian/Service/Orders/GuestDetail.canvas|GuestDetail.canvas]] : 비회원 주문조회 - /orders/Guest/{주문번호} Get
- ReqMemberRefund : 회원 환불요청 - /orders/refund Patch
- [[obsidian/Service/Orders/ReqGuestRefund.canvas|ReqGuestRefund]] : 비회원 환불요청 - /orders/guest/refund Patch

### Payment
- [[obsidian/Service/Payment/Pay.canvas|Pay]] : 결제 - /payments Get, Post

### Delivery
- Delivery : 회원 배송조회 - /deliveries/{운송장 번호} Get

### Display_Product
- [[obsidian/Service/Display_Product/List.canvas|List]] : 디피 상품 목록 - /display-products Get
- Detail : 디피 상품 상세 - /display-products/{dp-Id} Get

### Review
- List : 작성리뷰 조회
- Create : 리뷰 작성
- Update : 리뷰 수정
- Delete : 리뷰 삭제

### QnA
- LIst : 작성 질문 조회
- Create : 회원 질문 작성
- [[obsidian/Service/QnA/GuestCreate.canvas|GuestCreate]] : 비회원 질문 작성
- Update : 회원질문 수정
- [[obsidian/Service/QnA/GuestUpdate.canvas|GuestUpdate]] : 비회원 질문 수정
- Delete : 질문 삭제
- [[obsidian/Service/QnA/GuestDelete.canvas|GuestDelete]] : 질문 삭제
