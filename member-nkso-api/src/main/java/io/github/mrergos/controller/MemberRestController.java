package io.github.mrergos.controller;

import io.github.mrergos.controller.payload.request.CreateMemberNksoPayload;
import io.github.mrergos.controller.payload.request.UpdateMemberNksoPayload;
import io.github.mrergos.controller.payload.response.MemberNksoPayloadResponse;
import io.github.mrergos.service.MemberNksoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(path = "member-nkso-api/members")
@RequiredArgsConstructor
public class MemberRestController {
    private final MemberNksoService service;
    private final MessageSource messageSource;

    @GetMapping
    public Pair<Integer, List<MemberNksoPayloadResponse>> getMembersPageByFilter(@RequestParam(value = "filter", required = false, defaultValue = "") String filter,
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
                                          Errors errors,
                                          UriComponentsBuilder uriBuilder,
                                          Locale locale) {
        if (errors.hasErrors()) {
            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                    messageSource.getMessage("errors.400.title", new Object[0], "errors.400.title", locale));
            problemDetail.setProperty("errors",
                    errors.getAllErrors().stream()
                            .map(error -> Pair.of(((FieldError)error).getField(),error.getDefaultMessage()))
                            .toList());
            return ResponseEntity.badRequest()
                    .body(problemDetail);
        }
        MemberNksoPayloadResponse response = service.save(createMemberNksoPayload);
        return ResponseEntity.created(uriBuilder
                        .replacePath("/member-nkso-api/members/{registryNum}")
                        .build(response.getRegistryNum()))
                .body(response);
    }

    @PutMapping("/{registryNum}")
    @ResponseStatus(HttpStatus.OK)
    public MemberNksoPayloadResponse updateMember(@RequestBody UpdateMemberNksoPayload updateMemberNksoPayload) {
        return service.update(updateMemberNksoPayload);
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
