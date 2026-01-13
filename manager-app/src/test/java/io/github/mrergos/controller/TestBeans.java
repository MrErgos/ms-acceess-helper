package io.github.mrergos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.mrergos.client.MembersRestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@Configuration
public class TestBeans {

    @Bean
    @Primary
    public MembersRestClient testMembersRestClient(ObjectMapper objectMapper,
                                               @Value("${nkso.api.uri:http://localhost:54321}") String uri){
        return new MembersRestClient(RestClient.builder()
                .baseUrl(uri)
                .messageConverters(converters -> {
                    converters.removeIf(MappingJackson2HttpMessageConverter.class::isInstance);
                    converters.add(new MappingJackson2HttpMessageConverter(objectMapper));
                })
                .build(), objectMapper);
    }
}
