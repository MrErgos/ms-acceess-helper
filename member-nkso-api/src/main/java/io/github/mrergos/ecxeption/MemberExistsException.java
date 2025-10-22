package io.github.mrergos.ecxeption;

import io.github.mrergos.controller.payload.request.CreateMemberNksoPayload;
import lombok.Getter;

public class MemberExistsException extends RuntimeException {

    @Getter
    private CreateMemberNksoPayload createMemberNksoPayload;

    public MemberExistsException(String message, CreateMemberNksoPayload createMemberNksoPayload) {
        super(message);
        this.createMemberNksoPayload = createMemberNksoPayload;
    }

    public MemberExistsException(String message) {
        super(message);
    }
}
