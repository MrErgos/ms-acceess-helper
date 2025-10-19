package io.github.mrergos.client;

import io.github.mrergos.entity.MemberNkso;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.List;

@RequiredArgsConstructor
public class MembersRestClient implements MemberClient {
    private final RestClient restClient;

    @Override
    public List<MemberNkso> findAllMembers() {
        return restClient.get()
                .uri("/member-nkso-api/members")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    }

    @Override
    public MemberNkso findMemberById(String registryNum) {
        return restClient.get()
                .uri("/member-nkso-api/members/{registryNum}", registryNum)
                .retrieve()
                .body(MemberNkso.class);
    }
}
