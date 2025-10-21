package io.github.mrergos.service;

import io.github.mrergos.controller.payload.request.CreateMemberNksoPayload;
import io.github.mrergos.controller.payload.request.UpdateMemberNksoPayload;
import io.github.mrergos.controller.payload.response.MemberNksoPayloadResponse;
import io.github.mrergos.entity.MemberNkso;
import io.github.mrergos.repository.MemberNksoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberNksoService {
    private final MemberNksoRepository repository;

    public Pair<Integer, List<MemberNksoPayloadResponse>> findAll(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 2);
        Page<MemberNkso> page = repository.findAll(pageable);
        List<MemberNksoPayloadResponse> members = page.getContent()
                .stream()
                .map(MemberNksoPayloadResponse::new)
                .toList();
        return Pair.of(page.getTotalPages(), members);
    }

    public Optional<MemberNksoPayloadResponse> findById(String registryNum) {
        if (registryNum.length() < 5) {
            registryNum = "0".repeat(5 - registryNum.length()) + registryNum;
        }
        return repository.findById(registryNum).map(MemberNksoPayloadResponse::new);
    }

    public Pair<Integer, List<MemberNksoPayloadResponse>> findPageByFilter(String filter, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 2);
        filter = "%" + filter.trim() + "%";
        Page<MemberNkso> page = repository.findByLastnameIsLikeIgnoreCaseOrFirstnameIsLikeIgnoreCaseOrMiddlenameIsLikeIgnoreCaseOrRegistryNumIsLikeIgnoreCase(pageable,filter, filter, filter, filter);
        List<MemberNksoPayloadResponse> members = page.getContent()
                .stream()
                .map(MemberNksoPayloadResponse::new)
                .toList();
        return Pair.of(page.getTotalPages(), members);
    }

    public MemberNksoPayloadResponse save(CreateMemberNksoPayload createMemberNksoPayload) {
        if (createMemberNksoPayload.getDateAddedToRegistry() != null) {
            if ((createMemberNksoPayload.getTextDateAddedToRegistry() == null || createMemberNksoPayload.getTextDateAddedToRegistry().isEmpty())) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                createMemberNksoPayload.setTextDateAddedToRegistry(createMemberNksoPayload.getDateAddedToRegistry().format(formatter));
            }
            if (createMemberNksoPayload.getFullTextDateAddedToRegistry() == null || createMemberNksoPayload.getFullTextDateAddedToRegistry().isEmpty()) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("«dd» MMMM yyyy года");
                createMemberNksoPayload.setFullTextDateAddedToRegistry(createMemberNksoPayload.getDateAddedToRegistry().format(formatter));
            }
        }
        return new MemberNksoPayloadResponse(repository.save(createMemberNksoPayload.toMemberNkso()));
    }

    public MemberNksoPayloadResponse update(UpdateMemberNksoPayload updateMemberNksoPayload) {
        return new MemberNksoPayloadResponse(repository.save(updateMemberNksoPayload.toMemberNkso()));
    }

    public void delete(String registryNum) {
        repository.deleteById(registryNum);
    }
}
