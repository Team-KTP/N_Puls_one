# N + 1 해결 방안

### N + 1 이란?

- **특정 객체**를 조회하는 상황에서 그 객체가 가지고 있는 연관관계가 설정된 객체에 접근할 때 발생하는 성능 문제
- 즉, 특정 객체를 조회하는 1번의 쿼리 이후, 해당 객체와 연관된 데이터를 가져오기 위해 객체의 개수(N)만큼 추가 쿼리가 발생하는 현상
- 예를 들어, 게시글과 댓글이 `1:N` 관계일 때, 게시글 목록을 한 번에 조회(1번 쿼리)한 뒤, 각 게시글의 댓글을 가져오기 위해 N번의 쿼리가 추가로 실행되는 상황입니다.
  이처럼 단일 쿼리로 해결할 수 있는 작업이 불필요하게 여러 쿼리로 분산되면서 성능 저하가 발생함.

~~~ sql 
-- 게시글 전체 조회 (1번 쿼리)
SELECT * FROM post;

-- 첫 번째 게시글의 댓글
SELECT * FROM comment WHERE post_id = 1;

-- 두 번째 게시글의 댓글
SELECT * FROM comment WHERE post_id = 2;

-- 세 번째 게시글의 댓글
SELECT * FROM comment WHERE post_id = 3;
~~~

### ORM(Object-Relational Mapping)

- 객체지향 언어와 RDB 간의 패러다임 불일치를 해결하기 위해 나온 기술로 객체와 RDB 테이블을 매핑해주는 기술이다.
- 구조와 개념이 달라 불일치 문제가 발생함. 객체지향적으로 RDB를 다룰 수 있게 해줌
    - 뭐 객체지향은 상속, 참조 등이 있지만 RDB는 없음

### JPA(Java Persistence API)

- 자바에서 사용하는 ORM 기술 표준.

### JPQL

- JPA 에서 SQL을 추상화하여 사용되는 객체지향 쿼리 언어로서, 테이블이 아닌` 엔티티` 를 대상으로 쿼리를 작성한다.
- JPQL은 JPA의 구현체인 **Hibernate**가 SQL로 변한하고 **JDBC**를 통해서 실제 DB에 접근한다.

### 왜 발생할까?

- JPQL은 기본적으로 루트 엔티티만 조회하고 연관관계와 패치 전략 모두 무시한다. 따라서 연관관계에 접근할 떄 추가적인 쿼리가 발생함

### 해결 방법

1. ### **_fetch Join_**
    - *JPQL에서 성능 최적화를 위해 제공하는 기능*으로 **join fetch**로 한 번에 모두 조회한다.
    - 패치 조인을 사용하면 엔티티의 **fetch 전략(지연/즉시 로딩) 설정**과 상관없이, 쿼리에서 지정한 연관 엔티티를 _**한 번에 모두 즉시 로딩**_
    - `일반 JOIN`(fetch 전략이 LAZY일 떄)
        - JPQL에서 단순히 join을 사용하는 경우 또는 LAZY일 때 실제로는 루트 엔티티만 영속성 컨텍스트에 저장되고, 연관 엔티티는 프록시 객체로 남아 있는다.
          따라서 연관 엔티티에 접근할 때마다 추가 쿼리가 발생
    - `fetch Join`
        - join fetch 구문을 사용하면, 루트 엔티티와 연관 엔티티를 모두 실제 객체로 **한 번에 영속성 컨텍스트에 저장**합니다.
          이로써 추가 쿼리 없이 연관 데이터를 바로 사용
    - 즉시 로딩 전략도 한번에 다 가져오긴 하지만 쿼리에 `join fetch`를 명시 하지 않음으로 N+1 발생
    - 기본적으로 inner Join
    - **OneToOne, ManyToOne ("ToOne")** 관계에 대해선 몇개든 사용 가능하다.
    - **ManyToMany, OneToMany ("ToMany")** 는 단 1개만 사용 가능하다. 
      - JPQL 에서 지원 X
    - 
2. ### **_@EntityGraph_**
    - Spring Data JPA에서 제공하는 기능으로, JPQL로 fetch join을 직접 작성하지 않고 fetch join을 어노테이션을 사용하여 편리하게 사용할 수 있도록 해준다.
    - @EntityGraph는 **left outer join** 만 지원함.
    - 런타임에 fetchType.LAZY를 fetchType.EAGER로 전환하여 데이터를 가져온다
4. Batch Size
    - IN 쿼리로 묶어서 쿼리 수를 줄임
4. 일반 join후 Projection하여 특정 컬럼만 Dto로 조회

fetch join과 EntityGraph는 공통적으로 카테시안 곱(Cartesian Product)이 발생 하여 중복이 생길 수 있다.
※ 카테시안 곱 : 두 테이블 사이에 유효 join 조건을 적지 않았을 때 해당 테이블에 대한 모든 데이터를 전부 결합하여 테이블에 존재하는 행 갯수를 곱한만큼의 결과 값이 반환되는 것