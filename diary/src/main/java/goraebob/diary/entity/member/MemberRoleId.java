package goraebob.diary.entity.member;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor //모든 필드 값을 파라미터로 받는 생성자를 추가한다.
public class MemberRoleId implements Serializable {

    private Member member;
    private Role role;
}
// @IdClass 로 사용될 클래스
// 복합키가 만들어지는데 기본적으로 알파벳 순으로 만들어지게 된다.
// 이 클래스에서 정의한 것 처럼 member, role 순으로 키가 만들어짐
// 복합키에서는 key 들의 순서가 중요함
// @IdClass 를 사용하는 이유는 key 필드에 접근할 때 불필요하게 getter 를 연속해서 사용할 필요가 없다.
