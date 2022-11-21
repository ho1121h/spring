package goraebob.diary.entity.member;


import goraebob.diary.entity.common.EntityDate;
import lombok.AccessLevel; // getter, setter, toString 등의 메서드 작성 코드를 줄여주는 코드 다이어트 라이브러리
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
import static java.util.stream.Collectors.toSet;

@Entity //@Entity 애너테이션을 적용해야 JPA가 엔티티로 인식한다
@Getter // Class 내 모든 필드의 Getter method를 자동 생성한다.
@NoArgsConstructor(access = AccessLevel.PROTECTED) //기본생성자를 자동으로 추가한다 JPA에서 Entity 클래스를 생성하는것은 허용하기 위해 추가한다.
// 접근 제어 레벨 : PROTECTED * 참고 : https://www.daleseo.com/lombok-popular-annotations/
@NamedEntityGraph(
        name = "Member.roles",
        attributeNodes = @NamedAttributeNode(value = "roles", subgraph = "Member.roles.role"),
        subgraphs = @NamedSubgraph(name = "Member.roles.role", attributeNodes = @NamedAttributeNode("role"))
)

public class Member extends EntityDate { //
    @Id //고유 번호 id 속성에 적용한 @Id 애너테이션은 id 속성을 기본 키로 지정한다. 기본 키로 지정하면 이제 id 속성의 값은 데이터베이스에 저장할 때 동일한 값으로 저장할 수 없다. 고유 번호를 기본 키로 한 이유는 고유 번호는 엔티티에서 각 데이터를 구분하는 유효한 값으로 중복되면 안 되기 때문이다.
    @GeneratedValue(strategy = GenerationType.IDENTITY) //PK의 생성 규칙을 나타낸다. @GeneratedValue 애너테이션을 적용하면 데이터를 저장할 때 해당 속성에 값을 따로 세팅하지 않아도 1씩 자동으로 증가하여 저장된다
    @Column(name = "member_id") // 컬럼명
    private Long id;

    @Column(nullable = false, length = 30, unique = true) // 1
    private String email;// unique 로 설정 => email 과 nickname 은 인덱스가 형성되고, 중복을 허용하지 않게된다.

    private String password; // password 에는 NOTNULL 제약조건 X => SNS 로그인을 염두에 둠

    @Column(nullable = false, length = 20)
    private String username;

    @Column(nullable = false, unique = true, length = 20) // 1
    private String nickname;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true) // 사용자의 권한을 나타내는 Member 엔티티와 권한등급을 나타내는 Role 엔티티간의 브릿지 테이블을 MemberRole 클래스로 정의함 =>한명의 사용자는 여러 개의 권한을 가짐
    private Set<MemberRole> roles; // application 으로 조회시 성능 향상을 위해 Set 으로  선언

    public Member(String email, String password, String username, String nickname, List<Role> roles) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.nickname = nickname;
        this.roles = roles.stream().map(r -> new MemberRole(this, r)).collect(toSet());
    }

    public void updateNickname(String nickname) { //  말그대로 닉네임을 업데이트 할 수 있게 함
        this.nickname = nickname;
    }

}
