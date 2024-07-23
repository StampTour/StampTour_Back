# StampTour_Back

### 7월 15일 (사랑)
- 깃허브 업로드 해결
  - push가 되지 않는 오류
  - git push https://<토큰>@github.com/경로/리포지토리.git
  - ex) git push https://<token>@github.com/StampTour/StampTour_Back.git
- mysql 연동
  - jdbc 연동
  - jpa 연동
  - mysql 연동 및 데이터베이스 연동
- import javax.persistence.Entity;가 연동되지 않는 오류
  - javax : spring boot 3 이전 버전에서 사용
  - jakarta로 연동해 해결
### 7월 16일 (사랑)
- db 연동 및 데이터 삽입 확인
  - db 속 데이터 삽입 확인
  - api 테스트 완료
  - db 속 데이터에 입력한 데이터가 일치하는지 파악해 연결시킴
    - 단, 다른 사람이 입력한 것에서 db와 일치하는 경우 로그인 됨.
    - 수정 필요
    - 세션에 데이터가 있는지 파악과 그 세션에 일치하는 정보를 가져오는 것 필요
  - userid가 된것으로 예상되나 확실하진 않음.
  - 구글 클라우드와 연동 시켜둠.
    - 외부 ip : 35.216.73.103
    - 근데 실행 안됨. 뭐가 문제인지 파악필요.
### 7월 23일 (사랑)
- usercontroller 수정