package goraebob.diary.dto.member;

import goraebob.diary.entity.member.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {
    private Long id;
    private String email;
    private String username;
    private String nickname;

    public static MemberDto toDto(Member member) { //엔티티를 dto로 변환하기 위한 스태틱 메소드
        return new MemberDto(member.getId(), member.getEmail(), member.getUsername(), member.getNickname());
    }
    public static MemberDto empty() {
        return new MemberDto(null, "", "", "");
    }
}
