package io.github.mrergos;

import io.github.mrergos.entity.MemberNkso;
import io.github.mrergos.repository.MemberNksoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
@SpringBootApplication
public class MemberApiApplication {
    static void main(String[] args) {
        SpringApplication.run(MemberApiApplication.class, args);
    }


}
