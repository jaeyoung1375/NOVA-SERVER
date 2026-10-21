package kr.co.nova;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class NovaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(NovaServerApplication.class, args);
	}

}
