package io.github.mrergos;

import io.github.mrergos.repository.MemberNksoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@Slf4j
@SpringBootApplication
public class MemberApiApplication {
    static void main(String[] args) {
        SpringApplication.run(MemberApiApplication.class, args);
    }

    @Bean
    public CommandLineRunner getAllCustomers(MemberNksoRepository repository) {
        return args -> {
            repository.findAll().forEach(memberNkso -> log.info(memberNkso.toString()));
        };
    }
}
