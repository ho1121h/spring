package hello.hellospring.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute("data","hello!!!");
        return "hello";

    }
    @GetMapping("hello-mvc") // 뷰 생성  http://localhost:8080/hello-mvc?name=spring! 라고 치면됨
    public String helloMvc(@RequestParam("name") String name ,Model model) {
        model.addAttribute("name", name);
        return "hello-template";
    }
}
