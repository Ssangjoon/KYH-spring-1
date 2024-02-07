package hello.servet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

// 스프링 부트는 서블릿을 직접 등록해서 사용할 수 있도록 @ServletComponentScan 을 지원한다.
@ServletComponentScan // 서블릿 자동 등록
@SpringBootApplication
public class ServetApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServetApplication.class, args);
	}

}
