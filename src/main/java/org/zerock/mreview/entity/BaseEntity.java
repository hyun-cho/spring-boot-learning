package org.zerock.mreview.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/*
 * @MappedSuperclass 어노테이션은 테이블로 생성되지 않는다.
 *
 * Entity에는 영속성을 위한 Listener가 존재하고, AuditingEntityListener가 JPA내부에서 엔티티 객체가 생성/변경 되는것을 감지한다.
 * 이 옵션을 사용하기 위해서는 프로젝트의 Application 클래스에 @EnableJpaAuditing 어노테이션을 추가해야한다
 *
 * @CreatedDate는 JPA에서 엔티티의 생성 시간을 처리
 *
 * @LocalModifiedDatesms 최종 수정 시간을 자동으로 처리하는 용도
 * */
@MappedSuperclass
@EntityListeners(value = { AuditingEntityListener.class })
@Getter
public abstract class BaseEntity {

    @CreatedDate
    @Column(name = "regdate", updatable = false)
    private LocalDateTime regDate;

    @LastModifiedDate
    @Column(name = "moddate")
    private LocalDateTime modDate;

}