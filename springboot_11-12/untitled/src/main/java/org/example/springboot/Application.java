package org.example.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application { // main 클래스
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
/**
 * 스프링 Bean 읽기와 생성을 모두 자동으로 설정하여 읽음
 * 전체 패키지중 최상단에 위치하여야 전체를 읽을 수 있기때문에 이 위치에 위치하여아한다.
 *
 */