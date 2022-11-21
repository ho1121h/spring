package goraebob.diary.repository.role;

import goraebob.diary.entity.member.Role;
import goraebob.diary.entity.member.RoleType;
import goraebob.diary.exception.RoleNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

// CREATE, READ, DELETE에 대한 테스트
//
@DataJpaTest
class RoleRepositoryTest {
    @Autowired
    RoleRepository roleRepository;
    @PersistenceContext
    EntityManager em;

    @Test
    void createAndReadTest() {
        Role role = createRole();

        roleRepository.save(role);
        clear();

        Role foundRole = roleRepository.findById(role.getId()).orElseThrow(RoleNotFoundException::new);
        assertThat(foundRole.getId()).isEqualTo(role.getId());
    }
    @Test
    void deleteTest() { // 2
        // given
        Role role = roleRepository.save(createRole());
        clear();

        // when
        roleRepository.delete(role);

        // then
        assertThatThrownBy(() -> roleRepository.findById(role.getId()).orElseThrow(RoleNotFoundException::new))
                .isInstanceOf(RoleNotFoundException.class);
    }

    @Test
    void uniqueRoleTypeTest() { // RoleType의 unique 제약 조건 테스트
        // given
        roleRepository.save(createRole());
        clear();

        // when, then
        assertThatThrownBy(() -> roleRepository.save(createRole()))
                .isInstanceOf(DataIntegrityViolationException.class);

    }

    private Role createRole() {
        return new Role(RoleType.ROLE_NORMAL);
    }

    private void clear() {
        em.flush();
        em.clear();
    }
}
