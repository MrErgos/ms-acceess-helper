package io.github.mrergos.controller;

import io.github.mrergos.controller.payload.response.MemberNksoPayloadResponse;
import io.github.mrergos.service.MemberNksoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(path = "member-nkso-api/members")
@RequiredArgsConstructor
public class MemberRestController {
    private final MemberNksoService service;

    @GetMapping
    public List<MemberNksoPayloadResponse> getMembers() {
        return service.findAll();
    }

    @GetMapping("/{registryNum}")
    public MemberNksoPayloadResponse getMemberById(@PathVariable("registryNum") String registryNum) {
        return service.findById(registryNum).orElseThrow(() -> new NoSuchElementException("{manager.errors.member-nkso-not-found}"));
    }
}
