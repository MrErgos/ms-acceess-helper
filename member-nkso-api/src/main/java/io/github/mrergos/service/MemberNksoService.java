package io.github.mrergos.service;

import io.github.mrergos.controller.payload.request.CreateMemberNksoPayload;
import io.github.mrergos.controller.payload.request.UpdateMemberNksoPayload;
import io.github.mrergos.controller.payload.response.MemberNksoPayloadResponse;
import io.github.mrergos.controller.payload.response.PageResponse;
import io.github.mrergos.ecxeption.MemberExistsException;
import io.github.mrergos.ecxeption.MemberNotFoundException;
import io.github.mrergos.entity.MemberNkso;
import io.github.mrergos.repository.MemberNksoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberNksoService {
    private final MemberNksoRepository repository;
    private final Environment environment;
    private static final String templateFilter = "%%%s%%";

    public PageResponse<MemberNksoPayloadResponse> findAll(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, environment.getProperty("page.size", Integer.class, 10));
        Page<MemberNkso> page = repository.findAll(pageable);
        List<MemberNksoPayloadResponse> members = page.getContent()
                .stream()
                .map(MemberNksoPayloadResponse::new)
                .toList();
        return new PageResponse<>(members,
                page.getTotalPages(),
                (int) page.getTotalElements(),
                page.getSize(),
                page.getNumber(),
                page.isFirst(),
                page.isLast());
    }

    public PageResponse<MemberNksoPayloadResponse> findPageByFilter(String filter, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, environment.getProperty("page.size", Integer.class, 10));
        String[] filters = filter.trim().split(" ");
        Page<MemberNkso> page = switch (filters.length) {
            case 1 -> repository.findMembersByFilters(templateFilter.formatted(filters[0]), pageable);
            case 2 -> repository.findMembersByFilters(templateFilter.formatted(filters[0]), templateFilter.formatted(filters[1]), pageable);
            case 3 -> repository.findMembersByFilters(templateFilter.formatted(filters[0]), templateFilter.formatted(filters[1]), templateFilter.formatted(filters[2]), pageable);
            default -> repository.findMembersByFilters(templateFilter.formatted(filters[0]), templateFilter.formatted(filters[1]), templateFilter.formatted(filters[2]), templateFilter.formatted(filters[3]), pageable);
        };
        List<MemberNksoPayloadResponse> members = page.getContent()
                .stream()
                .map(MemberNksoPayloadResponse::new)
                .toList();
        return new PageResponse<>(members,
                page.getTotalPages(),
                (int) page.getTotalElements(),
                page.getSize(),
                page.getNumber(),
                page.isFirst(),
                page.isLast());
    }

    public Optional<MemberNksoPayloadResponse> findById(String registryNum) {
        if (registryNum.length() < 5) {
            registryNum = "0".repeat(5 - registryNum.length()) + registryNum;
        }
        return repository.findById(registryNum).map(MemberNksoPayloadResponse::new);
    }

    public MemberNksoPayloadResponse save(CreateMemberNksoPayload createMemberNksoPayload) {
        if (repository.existsById(createMemberNksoPayload.getRegistryNum())) {
            throw new MemberExistsException("{manager.member.creating.error.member_is_already_exists}", createMemberNksoPayload);
        }
        if (createMemberNksoPayload.getDateAddedToRegistry() != null) {
            if ((createMemberNksoPayload.getTextDateAddedToRegistry() == null || createMemberNksoPayload.getTextDateAddedToRegistry().isEmpty())) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                createMemberNksoPayload.setTextDateAddedToRegistry(createMemberNksoPayload.getDateAddedToRegistry().format(formatter));
            }
            if (createMemberNksoPayload.getFullTextDateAddedToRegistry() == null || createMemberNksoPayload.getFullTextDateAddedToRegistry().isEmpty()) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("\"dd\" MMMM yyyy года");
                createMemberNksoPayload.setFullTextDateAddedToRegistry(createMemberNksoPayload.getDateAddedToRegistry().format(formatter));
            }
        }
        return new MemberNksoPayloadResponse(repository.save(createMemberNksoPayload.toMemberNkso()));
    }

    public MemberNksoPayloadResponse update(UpdateMemberNksoPayload updateMemberNksoPayload, String registryNum) {
        MemberNkso existing = repository.findById(registryNum)
                .orElseThrow(() -> new MemberNotFoundException("manager.member.updating.error.member_does_not_exist", updateMemberNksoPayload, registryNum));

        BeanUtils.copyProperties(updateMemberNksoPayload.toMemberNkso(), existing);
        existing.setRegistryNum(registryNum);
        if (existing.getDateAddedToRegistry() != null) {
            DateTimeFormatter f1 = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            DateTimeFormatter f2 = DateTimeFormatter.ofPattern("\"dd\" MMMM yyyy года");
            existing.setTextDateAddedToRegistry(existing.getDateAddedToRegistry().format(f1));
            existing.setFullTextDateAddedToRegistry(existing.getDateAddedToRegistry().format(f2));
        }
        return new MemberNksoPayloadResponse(repository.save(existing));
    }

    public void delete(String registryNum) {
        repository.deleteById(registryNum);
    }
}
