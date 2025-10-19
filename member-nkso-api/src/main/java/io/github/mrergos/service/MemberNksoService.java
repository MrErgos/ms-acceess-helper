package io.github.mrergos.service;

import io.github.mrergos.controller.payload.response.MemberNksoPayloadResponse;
import io.github.mrergos.repository.MemberNksoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class MemberNksoService {
    private final MemberNksoRepository repository;

    public List<MemberNksoPayloadResponse> findAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .map(MemberNksoPayloadResponse::new)
                .toList();
    }

    public Optional<MemberNksoPayloadResponse> findById(String registryNum) {
        if (registryNum.length() < 5) {
            registryNum = "0".repeat(5 - registryNum.length()) + registryNum;
        }
        return repository.findById(registryNum).map(MemberNksoPayloadResponse::new);
    }
}
