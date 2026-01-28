package ssurent.ssurentbe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SsurentbeApplication {

    public static void main(String[] args) {
        SpringApplication.run(SsurentbeApplication.class, args);
    }

}
