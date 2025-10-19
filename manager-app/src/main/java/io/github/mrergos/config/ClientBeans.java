package io.github.mrergos.config;

import io.github.mrergos.client.MembersRestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class ClientBeans {
    @Bean
    public MembersRestClient membersRestClient(){
        return new MembersRestClient(RestClient.builder()
                .baseUrl("http://localhost:8081")
                .build());
    }
}
