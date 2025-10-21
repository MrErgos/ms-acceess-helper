package io.github.mrergos.client;

import io.github.mrergos.entity.MemberNkso;
import io.github.mrergos.util.Pair;

import java.util.Map;

public interface MemberClient {
    Pair getMembersPageByFilter(String filter, int page);
    MemberNkso findMemberById(String registryNum);

    Map<String, Object> getAllFields(MemberNkso member);

    MemberNkso update(MemberNkso member);

    Map<String, Object> getAllEmptyFields();

    MemberNkso create(MemberNkso member);
}
