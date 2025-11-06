package io.github.mrergos.controller;

import io.github.mrergos.controller.payload.request.CreateMemberNksoPayload;
import io.github.mrergos.controller.payload.request.UpdateMemberNksoPayload;
import io.github.mrergos.controller.payload.response.MemberNksoPayloadResponse;
import io.github.mrergos.controller.payload.response.PageResponse;
import io.github.mrergos.service.MemberNksoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.NoSuchElementException;

@RestController
@RequestMapping(path = "member-nkso-api/members")
@RequiredArgsConstructor
public class MemberRestController {
    private final MemberNksoService service;

    @GetMapping
    public PageResponse<MemberNksoPayloadResponse> getMembersPageByFilter(@RequestParam(value = "filter", required = false, defaultValue = "") String filter,
                                                                                         @RequestParam(value = "page", defaultValue = "0") int page) {
        if (filter.isBlank()) {
            return service.findAll(page);
        } else {
            return service.findPageByFilter(filter, page);
        }
    }

    @GetMapping("/{registryNum}")
    public MemberNksoPayloadResponse getMemberById(@PathVariable("registryNum") String registryNum) {
        return service.findById(registryNum).orElseThrow(() -> new NoSuchElementException("{manager.errors.member-nkso-not-found}"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createMember(@RequestBody @Valid CreateMemberNksoPayload createMemberNksoPayload,
                                          UriComponentsBuilder uriBuilder) {
        MemberNksoPayloadResponse response = service.save(createMemberNksoPayload);
        return ResponseEntity.created(uriBuilder
                        .replacePath("/member-nkso-api/members/{registryNum}")
                        .build(response.getRegistryNum()))
                .body(response);
    }

    @PutMapping("/{registryNum}")
    @ResponseStatus(HttpStatus.OK)
    public MemberNksoPayloadResponse updateMember(@RequestBody @Valid UpdateMemberNksoPayload updateMemberNksoPayload, @PathVariable String registryNum) {
        return service.update(updateMemberNksoPayload, registryNum);
    }

    @DeleteMapping("/{registryNum}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMember(@PathVariable("registryNum") String registryNum) {
        service.delete(registryNum);
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleNotFound(NoSuchElementException ex) {
    }
}
