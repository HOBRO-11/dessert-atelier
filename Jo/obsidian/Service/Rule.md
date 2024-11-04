## CRUD class naming rule
- 읽기 전용 interface : XxxQueryService(Xxx 는 엔티티의 이름이나 약어를 사용)
- 읽기 전용 class : XxxQueryServiceVn (n 은 버전에 맞는 숫자)
- 생성, 수정, 삭제 전용 interface :  XxxCommandService
- 생성, 수정 삭제 전용 class : XxxCommandServiceVn

## CRUD method naming rule
- 공통
	- 포멧 : Command_Xxx_\[By_Condition]
	- Command : CRUD를 나타내며 save, find, change, modify, bulkChange, delete가 있다.
	- Xxx : DTO, 엔티티 그리고 약어가 들어가며 단 건으로 명령을 수행하는 경우 이를 생략할 수 있다. 
	- Condition : 뒤에 붙는 조건으로 옵션이다.
	- 페이징과 관련된 것은 메소드명에 추가하지 않는다.

- 조건을 활용하는 경우 : CommandXxxs==ByCondition== (Xxx는 DTO 그리고 약어를 사용한다.)
	- 조건의 기준이 구체적인 경우 By뒤에 기준을 기입하여 메소드명으로 정의한다.
		- ex) CreatedDate의 기준으로 특정 엔티티를 조회 -> CommandXxxs==ByCreatedDate==
	- 조건의 기준이 명확하며, 길이가 30자를 넘지 않는경우 그대로 메소드 명으로 정의 한다.
		- ex) Status와 CreatedDate 두 조건을 적용 -> CommandXxxs==ByStatusAndCreatedDate==
	- 조건의 기준이 명확하나 길이가 30를 넘어가는 경우 메소드 명은 CommandXxxs==ByCondition==으로 정의 하고 메소드 안 파라미터를 처리하는 다른 메소드를 통해 해당 메소드의 의도를 전달한다.
		- ex) OrderStatus, CreatedDate 그리고 UpdatedDate 세 조건을 적용 (30자가 넘는 가정)
	- By, Like, LT, 등등 여러가지 사용할 수 있다.
~~~ java
		public List<Xxx> commandXxxsByConditon(XxxFindDto dto){
			isOrderStatus(dto);
			isCreatedDate(dto);
			isUpdatedDate(dto);
			...
		}
~~~

- 조회 : Find
	- 단 건 ==조회== : getXxx 
	- 다 건 ==조회== : getXxxs

- 생성 : save
	- 단 건 ==생성== : createXxx
	- 다 건 ==생성== : createXxxs

- 수정 : update
	- 특정 필드 하나를 ==수정== : updateField
	- 특정 필드 하나를 ==수정==하며 그 의도가 명확한경우 그 의도를 바로 메소드에 반영한다. 
		- ex) 직원의 직위를 매니저로 바꾸고 싶다면 -> changeRoleToManager
	- Bulk_Update의 경우 (단일 필드에 대해서만 ==벌트 수정==을 지원한다.): bulkChangeField
	- 필드 다수를 ==수정==하는 경우 : modify (DTO)
~~~ java
	public void modify(XxxModifyDto dto, Xxx xxx){
		if(dto.getOrderStatus() != null){
			xxx.setOrderStatus(dto.getOrderStatus())
		}
		...
	}
~~~

- 삭제 : delete
	- 단 건 ==삭제== : deleteXxx
	- 다 건 ==삭제== : deleteXxxs

- 의도가 확실한 경우 그 의도를 바로 메소드명으로 반영하여도 된다. 
	- ex) addXxx
## Exception naming Rule
