package org.zerock.ex2.entity;

import lombok.*;

import javax.persistence.*;

/*
* @Entity Spring Data JPA 에서는 반드시 이 어노테이션을 추가해야 한다.
* @Entity 어노테이션은 해당 클래스가 엔티티를 위한 클래스이며, 해당 클래스의 인스턴스들이 JPA로 관리되는 엔티티 객체라는 것을 의미
* 또한 옵션에 따라서 자동으로 테이블을 생성할 수도 있다., 칼럼도 자동으로 생성
*
* @Table
* 데이터베이상에서 엔티티 클래스를 어떠한 테이블로 생성할 것인지에 대한 정보를 담음
*
* @Id : PK를 지정
* @GeneratedValue : PK를 자동으로 생성하고자 할 때 사용, (키 생성 전략) auto increment 자동으로 등록
*                   AUTO, IDENTITY, SEQUENCE, TABLE등 이 있다.
*
* @Column
* 추가적인 필드가 필요한 경우 어노테이션을 활용, 다양한 속성 지저가능
* nullable, name, length등을 이용해서 정보를 제공
* columnDefinition을 사용하면 기본값을 지정할 수 도 있다.
*
* @Getter
* getter 메소드 자동생성
*
* @Builder
* builder패턴을 사용한 객체 생성 지원
* builder를 사용할 때는, AllArgsConstructor와 NoArgsConstructor를 항상 같이 사용해야 컴파일 에러 xs
* */
@Entity
@Table(name = "tbl_memo")
@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Memo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mno;

    @Column(length = 200, nullable = false)
    private String memoText;
}
