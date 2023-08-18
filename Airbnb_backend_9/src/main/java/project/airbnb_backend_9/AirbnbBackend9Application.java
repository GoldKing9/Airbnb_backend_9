package project.airbnb_backend_9;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class AirbnbBackend9Application {
	//해당 메서드의 리턴되는 오브젝트를 IoC로 등록
	@Bean
	public BCryptPasswordEncoder encodePwd(){
		return new BCryptPasswordEncoder();
	}
	public static void main(String[] args) {
		SpringApplication.run(AirbnbBackend9Application.class, args);
	}

}
