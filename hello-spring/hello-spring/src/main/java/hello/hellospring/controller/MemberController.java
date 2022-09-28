package hello.hellospring.controller;

import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller // 뭐 없지만 컨테이너 가 생김
public class MemberController {

    private final MemberService memberService; // new 개체를 새로 생성하면 여러개가 임포트 됨 그러므로

    @Autowired //스프링 컨테이너에서 가져옴 오류 발생이유는 컨테이너 안에 암것도 없어서
    public MemberController(MemberService memberService) {// 단축키 = alt + insert
        this.memberService = memberService;
    }
}
