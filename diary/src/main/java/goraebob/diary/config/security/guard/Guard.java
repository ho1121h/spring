package goraebob.diary.config.security.guard;

import goraebob.diary.entity.member.RoleType;

import java.util.List;

public abstract class Guard { // 추상 클래스 생성
    public final boolean check(Long id) {
        return AuthHelper.isAuthenticated() && (hasRole(getRoleTypes()) || isResourceOwner(id));
    }

    abstract protected List<RoleType> getRoleTypes();
    abstract protected boolean isResourceOwner(Long id);

    private boolean hasRole(List<RoleType> roleTypes) {
        return roleTypes.stream().allMatch(roleType -> AuthHelper.extractMemberRoles().contains(roleType));
    }
}