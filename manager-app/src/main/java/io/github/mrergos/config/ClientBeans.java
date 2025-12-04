package io.github.mrergos.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.mrergos.client.MembersRestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.client.OAuth2ClientHttpRequestInterceptor;
import org.springframework.web.client.RestClient;

@Configuration
public class ClientBeans {
    @Bean
    public MembersRestClient membersRestClient(ObjectMapper objectMapper, OAuth2AuthorizedClientManager oAuth2AuthorizedClientManager) {
        OAuth2ClientHttpRequestInterceptor interceptor = new OAuth2ClientHttpRequestInterceptor(oAuth2AuthorizedClientManager);
        return new MembersRestClient(RestClient.builder()
                .baseUrl("http://localhost:8081")
                .messageConverters(converter -> {
                    converter.removeIf(MappingJackson2HttpMessageConverter.class::isInstance);
                    converter.add(new MappingJackson2HttpMessageConverter(objectMapper));
                })
                .requestInterceptor(interceptor)
                .build(), objectMapper);
    }
}
