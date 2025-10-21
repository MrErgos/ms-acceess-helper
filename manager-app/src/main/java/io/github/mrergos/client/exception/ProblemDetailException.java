package io.github.mrergos.client.exception;

import io.github.mrergos.entity.MemberNkso;
import lombok.Getter;
import org.springframework.http.ProblemDetail;

@Getter
public class ProblemDetailException extends RuntimeException {
    private final ProblemDetail problemDetail;
    private final MemberNkso member;
    public ProblemDetailException(MemberNkso memberNkso, ProblemDetail problemDetail) {
        super(problemDetail.getDetail());
        this.member = memberNkso;
        this.problemDetail = problemDetail;
    }
}
