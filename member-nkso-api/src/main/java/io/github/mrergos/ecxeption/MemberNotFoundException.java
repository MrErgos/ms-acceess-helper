package io.github.mrergos.ecxeption;

import io.github.mrergos.controller.payload.request.UpdateMemberNksoPayload;
import lombok.Getter;

public class MemberNotFoundException extends RuntimeException {
    @Getter
    private UpdateMemberNksoPayload updateMemberNksoPayload;

    @Getter
    private String registryNum;

    public MemberNotFoundException(String message, UpdateMemberNksoPayload updateMemberNksoPayload, String registryNum) {
        super(message);
        this.updateMemberNksoPayload = updateMemberNksoPayload;
        this.registryNum = registryNum;
    }

    public MemberNotFoundException(String message) {
        super(message);
    }
}
