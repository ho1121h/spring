package goraebob.diary.service.member;

import goraebob.diary.dto.member.MemberDto;
import goraebob.diary.exception.MemberNotFoundException;
import goraebob.diary.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
// 회원 서비스 조회 및 삭제
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberDto read(Long id) {
        return MemberDto.toDto(memberRepository.findById(id).orElseThrow(MemberNotFoundException::new));
    }

    @Transactional
    public void delete(Long id) {
        if(notExistsMember(id)) throw new MemberNotFoundException();
        memberRepository.deleteById(id);
    }

    private boolean notExistsMember(Long id) {
        return !memberRepository.existsById(id);
    }

}