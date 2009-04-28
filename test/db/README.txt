테스트 데이터 넣기
1. 이클립스 Data Source Explore에 Derby 커넥션을 생성.
2. TestDataGenerator를 이용하여, testDB.data 파일에 테스트 데이터 생성.(생략 가능)
3. Data Source Explore에서 Derby에 연결하고, App 스키마의 Table 항목에 Member 테이블 확인.
	3-1. Member 테이블이 없을 경우 애플리케이션을 한 번 실행한 다음 다시 시도.(하이버네이트가 만들어 줌)
4. Member 테이블을 우클릭하고 Load를 선택한 다음 testDB.data 파일 경로를 지정해 줌.
5. Derby 커넥션 끊기.(커넥션을 끊지 않을 경우 애플리케이션이 동작하지 않음)