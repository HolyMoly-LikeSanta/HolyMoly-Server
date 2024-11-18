package likelion.holymoly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class HolymolyApplication {

	public static void main(String[] args) {
		SpringApplication.run(HolymolyApplication.class, args);
	}

}
