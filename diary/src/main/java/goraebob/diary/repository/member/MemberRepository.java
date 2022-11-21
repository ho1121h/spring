package goraebob.diary.repository.member;

import goraebob.diary.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// 회원 조회 서비스
// 리포지터리의 대상이 되는 엔티티의 타입(Question)과 해당 엔티티의 PK의 속성 타입(Integer)을 지정
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email); // https://kukekyakya.tistory.com/m/85 참고
    Optional<Member> findByNickname(String nickname); //Optional은 선택형 값을 캡슐화

    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);// 위에서 여기까지 작성하면 쿼리값을 자동으로 생성해줌

}

/**
 *
 * 리포지터리란?
 * 리포지터리는 엔티티에 의해 생성된 데이터베이스 테이블에 접근하는
 * 메서드들(예: findAll, save 등)을 사용하기 위한 인터페이스이다.
 * 데이터 처리를 위해서는 테이블에 어떤 값을 넣거나 값을 조회하는 등의 CRUD(Create, Read, Update, Delete)가 필요하다.
 * 이 때 이러한 CRUD를 어떻게 처리할지 정의하는 계층이 바로 리포지터리이다.
 *
 */