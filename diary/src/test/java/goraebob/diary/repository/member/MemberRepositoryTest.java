package goraebob.diary.repository.member;

import goraebob.diary.entity.member.Member;
import goraebob.diary.entity.member.MemberRole;
import goraebob.diary.entity.member.Role;
import goraebob.diary.entity.member.RoleType;
import goraebob.diary.exception.MemberNotFoundException;
import goraebob.diary.repository.role.RoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest //Jpa 관련된 부분만 테스트할 것이기 때문에, 클래스에 @DataJpaTest를 설정
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    RoleRepository roleRepository;
    @PersistenceContext
    EntityManager em;

    @Test
    void createAndReadTest() { // 리포지토리를 이용하여 Member를 저장하고, 저장된 Member를 데이터베이스에서 다시 조회하여 검증
        // given 테스트에 필요한 데이터 또는 상황
        Member member = createMember();

        // when 테스트를 수행함
        memberRepository.save(member);
        clear();

        // then 테스트의 결과ㅓ를 검증
        Member foundMember = memberRepository.findById(member.getId()).orElseThrow(MemberNotFoundException::new);
        assertThat(foundMember.getId()).isEqualTo(member.getId());
    }

    @Test //@MappedSuperClass로 선언하여 상속받았던, EntityDate 클래스에 작성된 필드들이 자동으로 추가되어 생성되는지 확인
    void memberDateTest() {
        // given
        Member member = createMember();

        // when
        memberRepository.save(member);
        clear();

        // then
        Member foundMember = memberRepository.findById(member.getId()).orElseThrow(MemberNotFoundException::new);
        assertThat(foundMember.getCreatedAt()).isNotNull();
        assertThat(foundMember.getModifiedAt()).isNotNull();
        assertThat(foundMember.getCreatedAt()).isEqualTo(foundMember.getModifiedAt());
    }

    @Test //업데이트를 검증 엔티티에서 업데이트된 필드를 보고 트잭이 끝나거나 강제적으로 쿼리를 수행하면 엔티티에서 업데이트 된 필드를 보고, 데이터베이스로 업데이트 쿼리를 날려준다.
    void updateTest() {
        // given
        String updatedNickname = "updated";
        Member member = memberRepository.save(createMember());
        clear();

        // when
        Member foundMember = memberRepository.findById(member.getId()).orElseThrow(MemberNotFoundException::new);
        foundMember.updateNickname(updatedNickname);
        clear();

        // then
        Member updatedMember = memberRepository.findById(member.getId()).orElseThrow(MemberNotFoundException::new);
        assertThat(updatedMember.getNickname()).isEqualTo(updatedNickname);
    }

    @Test
    void deleteTest() { // 이미 삭제된 데이터를 조회했을때 반환되는 옵셔널을 이용하여 객체가 없으면 예외를 발생시킴,
        // given
        Member member = memberRepository.save(createMember());
        clear();

        // when
        memberRepository.delete(member);
        clear();

        // then
        assertThatThrownBy(() -> memberRepository.findById(member.getId()).orElseThrow(MemberNotFoundException::new))
                .isInstanceOf(MemberNotFoundException.class);
    }

    @Test
    void findByEmailTest() {
        // given
        Member member = memberRepository.save(createMember());
        clear();

        // when
        Member foundMember = memberRepository.findByEmail(member.getEmail()).orElseThrow(MemberNotFoundException::new);

        // then
        assertThat(foundMember.getEmail()).isEqualTo(member.getEmail());
    }

    @Test
    void findByNicknameTest() {
        // given
        Member member = memberRepository.save(createMember());
        clear();

        // when
        Member foundMember = memberRepository.findByNickname(member.getNickname()).orElseThrow(MemberNotFoundException::new);

        // then
        assertThat(foundMember.getNickname()).isEqualTo(member.getNickname());
    }

    @Test
    void uniqueEmailTest() { // 유니크 값이라서 예외 발생시 DataIntegrityViolationException 이 발생
        // given
        Member member = memberRepository.save(createMember("email1", "password1", "username1", "nickname1"));
        clear();

        // when, then
        assertThatThrownBy(() -> memberRepository.save(createMember(member.getEmail(), "password2", "username2", "nickname2")))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void uniqueNicknameTest() {
        // given
        Member member = memberRepository.save(createMember("email1", "password1", "username1", "nickname1"));
        clear();

        // when, then
        assertThatThrownBy(() -> memberRepository.save(createMember("email2", "password2", "username2", member.getNickname())))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void existsByEmailTest() { // 레코드가 이미 있는지 테스트 없으면 F 있으면 T
        // given
        Member member = memberRepository.save(createMember());
        clear();

        // when, then
        assertThat(memberRepository.existsByEmail(member.getEmail())).isTrue();
        assertThat(memberRepository.existsByEmail(member.getEmail() + "test")).isFalse();
    }

    @Test
    void existsByNicknameTest() {
        // given
        Member member = memberRepository.save(createMember());
        clear();

        // when, then
        assertThat(memberRepository.existsByNickname(member.getNickname())).isTrue();
        assertThat(memberRepository.existsByNickname(member.getNickname() + "test")).isFalse();
    }

    @Test
    void memberRoleCascadePersistTest() {// Member 엔티티가 일대다 관계를 갖고있는지 MemberRole 이 연달아서 저장되는지 검증하는 테스트
        // given
        List<RoleType> roleTypes = List.of(RoleType.ROLE_NORMAL, RoleType.ROLE_SPECIAL_BUYER, RoleType.ROLE_ADMIN);
        List<Role> roles = roleTypes.stream().map(roleType -> new Role(roleType)).collect(Collectors.toList());
        roleRepository.saveAll(roles);
        clear();

        Member member = memberRepository.save(createMemberWithRoles(roleRepository.findAll()));
        clear();

        // when
        Member foundMember = memberRepository.findById(member.getId()).orElseThrow(MemberNotFoundException::new);
        Set<MemberRole> memberRoles = foundMember.getRoles();

        // then
        assertThat(memberRoles.size()).isEqualTo(roles.size());
    }

    @Test
    void memberRoleCascadeDeleteTest() { // Member를 제거할 때, MemberRole 또한 함께 제거되는지 테스트
        // given
        List<RoleType> roleTypes = List.of(RoleType.ROLE_NORMAL, RoleType.ROLE_SPECIAL_BUYER, RoleType.ROLE_ADMIN);
        List<Role> roles = roleTypes.stream().map(roleType -> new Role(roleType)).collect(Collectors.toList());
        roleRepository.saveAll(roles);
        clear();

        Member member = memberRepository.save(createMemberWithRoles(roleRepository.findAll()));
        clear();

        // when
        memberRepository.deleteById(member.getId());
        clear();

        // then
        List<MemberRole> result = em.createQuery("select mr from MemberRole mr", MemberRole.class).getResultList();
        assertThat(result.size()).isZero();
    }

    private void clear() {
        em.flush();
        em.clear();
    }

    private Member createMemberWithRoles(List<Role> roles) {
        return new Member("email", "password", "username", "nickname", roles);
    }

    private Member createMember(String email, String password, String username, String nickname) {
        return new Member(email, password, username, nickname, emptyList());
    }

    private Member createMember() {
        return new Member("email", "password", "username", "nickname", emptyList());
    }

}