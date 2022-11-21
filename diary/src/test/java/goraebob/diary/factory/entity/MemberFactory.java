package goraebob.diary.factory.entity;

import goraebob.diary.entity.member.Member;
import goraebob.diary.entity.member.Role;

import java.util.List;

import static java.util.Collections.emptyList;

public class MemberFactory {

    public static Member createMember() {
        return new Member("email@email.com", "123456a!", "username", "nickname", emptyList());
    }

    public static Member createMember(String email, String password, String username, String nickname) {
        return new Member(email, password, username, nickname, emptyList());
    }

    public static Member createMemberWithRoles(List<Role> roles) {
        return new Member("email@email.com", "123456a!", "username", "nickname", roles);
    }

}
//메소드는 모두 퍼블릭 스태틱 메소드로 바꿔줍시다.
//
//그리고 기존의 테스트 클래스에 작성된 인스턴스 생성 메소드를 제거하고, 새로운 팩토리 클래스의 메소드를 사용하도록 스태틱 임포트 해주겠습니다.
//
//팩토리 메소드의 원활한 재사용을 위해, 전달받는 파라미터를 제외한 기본 값들은, 의도했던 규칙에 맞게 값을 설정해주도록 하겠습니다.
//
//예를 들어, email은 이메일 양식에 따라서, password는 비밀번호 생성 규칙(알파벳, 숫자, 특수문자 포함, 8자리 이상)에 따라서 설정해줍니다.
//
//