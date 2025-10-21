package io.github.mrergos.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.mrergos.client.exception.ProblemDetailException;
import io.github.mrergos.entity.MemberNkso;
import io.github.mrergos.util.Pair;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.client.RestClient;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class MembersRestClient implements MemberClient {
    private final RestClient restClient;
    private final ObjectMapper objectMapper;


    @Override
    public Pair getMembersPageByFilter(String filter, int page) {
        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/member-nkso-api/members")
                        .queryParam("filter", filter)
                        .queryParam("page", page)
                        .build())
                .retrieve()
                .body(Pair.class);
    }

    @Override
    public MemberNkso findMemberById(String registryNum) {
        return restClient.get()
                .uri("/member-nkso-api/members/{registryNum}", registryNum)
                .retrieve()
                .body(MemberNkso.class);
    }

    @Override
    public MemberNkso create(MemberNkso member) {
        return restClient.post()
                .uri("/member-nkso-api/members")
                .contentType(MediaType.APPLICATION_JSON)
                .body(member)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, ((request, response) -> {
                    ProblemDetail problemDetail = objectMapper.readValue(response.getBody(), ProblemDetail.class);
                    throw new ProblemDetailException(member,problemDetail);
                }))
                .body(MemberNkso.class);
    }

    @Override
    public MemberNkso update(MemberNkso member) {
        return restClient.put()
                .uri("/member-nkso-api/members/{registryNum}", member.registryNum())
                .contentType(MediaType.APPLICATION_JSON)
                .body(member)
                .retrieve()
                .body(MemberNkso.class);
    }

    @Override
    public Map<String, Object> getAllFields(MemberNkso member) {
        Map<String, Object> fields = new HashMap<>();
        for (Field field : member.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                fields.put(field.getName(), field.get(member));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return fields;
    }

    @Override
    public Map<String, Object> getAllEmptyFields() {
        Map<String, Object> fields = new HashMap<>();
        for (Field field : MemberNkso.class.getDeclaredFields()) {
            field.setAccessible(true);
            fields.put(field.getName(), null);
        }
        return fields;
    }
}
