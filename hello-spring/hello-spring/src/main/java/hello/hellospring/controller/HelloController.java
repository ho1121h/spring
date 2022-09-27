package hello.hellospring.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @GetMapping("hello-string")
    @ResponseBody // 바디에 내가 직접 리턴값을 직접 넣겠다 라는 뜻
    public String helloString(@RequestParam("name")String name){
        return "hello "+ name; //"hello spring" 라고 바로나옴 소스를 보면 html 문법이 암것도없음
    }

    @GetMapping("hello-api")
    @ResponseBody //@ResponseBody 를 사용하고, 객체를 반환하면 객체가 JSON으로 변환됨 제이슨쓰는게 편함
    public Hello helloApi(@RequestParam("name")String name){
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
    }
    static class Hello{ //게터세터 컨트룰 + N 제너레이터
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
