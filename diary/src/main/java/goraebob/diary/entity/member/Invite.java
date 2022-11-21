package goraebob.diary.entity.member;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Invite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "member_id")
    private Long id;

    @Enumerated(EnumType.STRING) // Role Type 이 어떤 권한 등급이 있는지 나타내는 클래스
    @Column(nullable = false, unique = true)
    private InviteType inviteType;

}
