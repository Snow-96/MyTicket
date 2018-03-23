package snow.myticket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MyTicketApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyTicketApplication.class, args);
	}
}
