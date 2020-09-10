package person.zone.dynamic.db;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class DynamicDbApplication {

	public static void main(String[] args) {
		SpringApplication.run(DynamicDbApplication.class, args);
		log.info("DynamicDbApplication run successfully info");
	}
}
