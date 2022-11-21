package goraebob.diary.factory.entity;


import goraebob.diary.entity.member.Role;
import goraebob.diary.entity.member.RoleType;

public class RoleFactory {
    public static Role createRole() {
        return new Role(RoleType.ROLE_NORMAL);
    }
}