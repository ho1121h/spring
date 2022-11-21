package goraebob.diary.entity.member;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;

    @Enumerated(EnumType.STRING) // Role Type 이 어떤 권한 등급이 있는지 나타내는 클래스
    @Column(nullable = false, unique = true) // 중복되는 Role 타입이 생성되는 것을 방지 하기 위해, unique 제약 조건 추가
    private RoleType roleType;

    public Role(RoleType roleType) {
        this.roleType = roleType;
    }
}
