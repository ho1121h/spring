package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 *  간단하게 테스트 해보기 : ctrl + shift + T
 */
//@Service // 컨테이너에 등록시킴
@Transactional
public class MemberService {// final = 여러 컨텍스트에서 한번만 할당되는 엔티티를 정의
    private  MemberRepository memberRepository;

//    @Autowired // MemberReository를 서비스에 등록
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Long join(Member member){

        validateDuplicateMember(member); //중복 회원 검증, 메서드 통째로 생성키 (ctrl + alt + shift + T)
        memberRepository.save(member);
        return member.getId();
//        long start = System.currentTimeMillis();
//
//        try {
//            validateDuplicateMember(member); //중복 회원 검증, 메서드 통째로 생성키 (ctrl + alt + shift + T)
//            memberRepository.save(member);
//            return member.getId();
//        } finally {
//            long finish = System.currentTimeMillis();
//            long timeMs = finish - start;
//            System.out.println("join = " + timeMs + "ms");
//        }
        // 같은 이름이 있는 중복 회원은 안됨
//        Optional<Member> byId = memberRepository.findById(member.getId()); // ctrl + alt + v

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
