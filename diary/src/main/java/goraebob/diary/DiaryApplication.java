package goraebob.diary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
// 인텔리제이를 쓰신다면 여기서 실행하시면 됩니다. 아니면 터미널 명령어로 빌드실행하시면되요.
@EnableJpaAuditing //
@SpringBootApplication //@Configuration, @EnableAutoConfiguration, @ComponentScan 3가지를 하나의 애노테이션으로 합친 것
public class DiaryApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiaryApplication.class, args);
	}

}
