# Airbnb_backend_9
골드킹 에어비앤비 클론코딩 백엔드입니다



## 커밋메시지 컨벤션
- feat : 기능 추가
- refactor : 기능 수정
- fix : 버그 수정
- test : 테스트 코드, 리펙토링 테스트 코드 추가

## git관리 규칙
```java
src/
|-- main/
|   |-- java/
|   |   |-- com/
|   |       |-- yourapp/
|   |           |-- repository
|   |           |-- domain  
|   |           |-- user/
|   |           |   |-- controller/
|   |           |   |-- service/
|   |           |   |-- dto/
|   |           |
|   |           |-- reservation/
|   |           |   |-- controller/
|   |           |   |-- service/
|   |           |   |-- dto/
|   |           |
|   |           |-- accommodation/
|   |           |   |-- controller/
|   |           |   |-- service/
|   |           |   |-- dto/
|   |           |
|   |           |-- review/
|   |               |-- controller/
|   |               |-- service/
|   |               |-- dto/
|   |
|   |-- resources/
|       |-- application.yml
|
|-- test/
    |-- java/
        |-- com/
            |-- yourapp/
                |-- repository/
                |   |-- UserRepositoryest.java
                |   |-- ReservationRepositoryest.java
                |   |-- AccommodationRepositoryest.java
                |   |-- ReviewRepositoryest.java
                |
                |-- user/
                |   |-- UserControllerTest.java
                |   |-- UserServiceTest.java
                |
                |-- reservation/
                |   |-- ReservationControllerTest.java
                |   |-- ReservationServiceTest.java 
                |
                |-- accommodation/
                |   |-- AccommodationControllerTest.java
                |   |-- AccommodationServiceTest.java
                |
                |-- review/
                    |-- ReviewControllerTest.java
                    |-- ReviewServiceTest.java
```
