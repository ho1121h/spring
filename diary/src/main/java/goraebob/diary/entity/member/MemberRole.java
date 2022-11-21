package goraebob.diary.entity.member;


import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode // 멤버롤은 멤버에서 set으로 지정되기 때문에 equals 와 hashcode 를 재정의
@IdClass(MemberRoleId.class) // 여러개의 필드를 일반키로 사용하기 위해 idclass 를 사용 /  MemberRoleId 클래스에 정의된 필드와 동일한 필드를, MemberRole에서 @Id로 선언해주면, composite key로 생성가능
public class MemberRole {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;

}
