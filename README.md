# 요구사항 
- SCADA 시스템  테스트를  위한  다양한  분석  도구가  필요함

- 고장진단을 위한  분석,  검증  도구가  필요함

![그림8](https://user-images.githubusercontent.com/57141105/101866058-3dff5980-3bbb-11eb-96aa-f3b312285f53.png)

# 필요성
- 원격감시 의 규모와 복잡도는 날로 크게 증가하고 있음.
       
- 원격감시 의 완전한 테스트와 검증은 불가능함.

- 고장 또는 오류 발생 시 진단이 어려움.

![그림7](https://user-images.githubusercontent.com/57141105/101866057-3cce2c80-3bbb-11eb-9ed8-847f148a8666.png)

# 테스트 및 분석 기능 개발
### 다음과 같은 분석 기능을 개발
 정보 종속관계 분석기
- 사용되지 않는 실 태그를 구분하는 분석 
- 논리 태그에 영향을 주는 물리태그를 찾아내는 분석
- 장비의 물리주소와 속성과 관계를 찾아내는 분석
- 객체와 태그의 연결 정보를 화면 단위로 분석
- 이벤트와 관련되는 모든 태그 , 물리주소 등을 분석

 테스트 케이스 생성기
- 테스트를 위한 태그별 트랜드를 생성

## 존재하지 않는 태그 분석 방법
![그림1](https://user-images.githubusercontent.com/57141105/101865397-7ef66e80-3bb9-11eb-80ee-16dfbb392e02.png)
사전 내용 중 사용되지 않는 태그를 구별해 내는 기능


## 가상 태그 종속성 분석 방법
![그림2](https://user-images.githubusercontent.com/57141105/101865641-2c698200-3bba-11eb-9b04-8ff84746c45b.png)
논리태그에 영향을 주는 모든 물리 태그를 찾아내는 기능 


## 물리주소 종속성 분석 방법
![그림3](https://user-images.githubusercontent.com/57141105/101865691-558a1280-3bba-11eb-89d3-de3105e14619.png)
장비의 물리주소와 각종 속성과의 관계를 찾아내는 기능


## 객체효과 양립성 분석 방법
![그림4](https://user-images.githubusercontent.com/57141105/101865745-7a7e8580-3bba-11eb-9205-f67736e4e666.png)
HMI 객체와 태그의 연결 정보를 화면단위로 분석하는 기능


## 이벤트 종속성 분석 방법
![그림5](https://user-images.githubusercontent.com/57141105/101865853-b9144000-3bba-11eb-93dc-6e1014e3cfca.png)
사전 내용 중 사용되지 않는 태그를 구별해 내는 기능

## IO 테스트 케이스 생성 방법
![그림6](https://user-images.githubusercontent.com/57141105/101865896-d517e180-3bba-11eb-9ebd-247b9477f48a.png)
I/O 서버용 테스트를 위한 테스트 케이스 태그값 목록 생성
