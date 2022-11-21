package goraebob.diary.entity.common;


import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;
// member 엔티티 에서 상속받은 클래스임 -> 이 클래스를 활성할려면 스프링 부트 애플리케이션에 @EnableJpaAuditing 추가
@EntityListeners(AuditingEntityListener.class) // @EntityListeners를 등록하고, 각각의 필드에 @CreatedDate와 @LastModifiedDate를 지정해주면,
@MappedSuperclass //엔티티에서 상속 받게되면 필드 두개를 추가 ( createdAt, modifiedAt)
@Getter
public abstract class EntityDate { // EntityDate 자체로 인스턴스가 생성될 이유가 없어서 추상 클래스로 생성
    @CreatedDate //@CreatedDate 엔티티가 생성되거나 업데이트 될 때, 해당 필드의 데이터도 자동으로 업데이트
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate //@LastModifiedDate 엔티티가 생성되거나 업데이트 될 때, 해당 필드의 데이터도 자동으로 업데이트
    @Column(nullable = false, updatable = false)
    private LocalDateTime modifiedAt;
}
//