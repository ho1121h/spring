package service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;

public class MemberService {// final = 여러 컨텍스트에서 한번만 할당되는 엔티티를 정의
    private final MemberRepository memberRepository = new MemoryMemberRepository();

    /**
     * 회원 가입
     */
    public Long join(Member member){
        // 같은 이름이 있는 중복 회원은 안됨
//        Optional<Member> byId = memberRepository.findById(member.getId()); // ctrl + alt + v
        validateDuplicateMember(member); //중복 회원 검증, 메서드 통째로 생성키 (ctrl + alt + shift + T)
        memberRepository.save(member);
        return member.getId();
    }
    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m ->{
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                } );
    }

    /**
     * 전체회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();

    }
    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
