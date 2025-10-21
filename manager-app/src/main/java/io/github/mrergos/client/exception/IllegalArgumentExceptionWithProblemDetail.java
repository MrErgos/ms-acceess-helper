package io.github.mrergos.client.exception;

import io.github.mrergos.entity.MemberNkso;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ProblemDetail;

public class IllegalArgumentExceptionWithProblemDetail extends RuntimeException {
    @Getter
    @Setter
    private ProblemDetail problemDetail;
    @Getter
    @Setter
    private MemberNkso member;
    public IllegalArgumentExceptionWithProblemDetail(MemberNkso memberNkso,ProblemDetail problemDetail) {
        this.member = memberNkso;
        this.problemDetail = problemDetail;
    }
}
