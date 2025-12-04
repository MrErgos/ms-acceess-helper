package io.github.mrergos.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.mrergos.client.exception.ProblemDetailException;
import io.github.mrergos.entity.MemberNkso;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.client.RestClient;

import static org.springframework.security.oauth2.client.web.client.RequestAttributeClientRegistrationIdResolver.clientRegistrationId;

@Slf4j
@RequiredArgsConstructor
public class MembersRestClient implements MemberClient {
    private final RestClient restClient;
    private final ObjectMapper objectMapper;


    @Override
    public PageResponse<MemberNkso> getMembersPageByFilter(String filter, int page) {
        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/member-nkso-api/members")
                        .queryParam("filter", filter)
                        .queryParam("page", page)
                        .build())
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
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
                .attributes(clientRegistrationId("keycloak"))
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
                .uri("/member-nkso-api/members/{registryNum}", member.getRegistryNum())
                .contentType(MediaType.APPLICATION_JSON)
                .body(member)
                .attributes(clientRegistrationId("keycloak"))
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, ((request, response) -> {
                    ProblemDetail problemDetail = objectMapper.readValue(response.getBody(), ProblemDetail.class);
                    throw new ProblemDetailException(member,problemDetail);
                }))
                .body(MemberNkso.class);
    }
}
