package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller // 뭐 없지만 컨테이너 가 생김
public class MemberController {

    private final MemberService memberService; // new 개체를 새로 생성하면 여러개가 임포트 됨 그러므로

    @Autowired //스프링 컨테이너에서 가져옴
    public MemberController(MemberService memberService) {// 단축키 = alt + insert
        this.memberService = memberService;
        System.out.println("memberService = " + memberService.getClass());
    }

    @GetMapping("/members/new")//home.html 에 기능 추가
    public String createForm() {
        return "members/createMemberForm"; // 리턴하면 템플릿에서 파일명 찾음
    }

    @PostMapping("/members/new") // post 가 여기로 전달됨
    public String create(MemberForm form) {
        Member member = new Member();
        member.setName(form.getName());

        memberService.join(member);

        return "redirect:/";
    }

    @GetMapping("/members")
    public String List(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
