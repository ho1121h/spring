package goraebob.diary.repository.role;

import goraebob.diary.entity.member.Role;
import goraebob.diary.entity.member.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// 권한 조회 인터페이스
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleType(RoleType roleType);
}
