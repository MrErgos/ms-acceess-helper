package io.github.mrergos.client;

import io.github.mrergos.entity.MemberNkso;

public interface MemberClient {
    PageResponse<MemberNkso> getMembersPageByFilter(String filter, int page);
    MemberNkso findMemberById(String registryNum);


    MemberNkso update(MemberNkso member);


    MemberNkso create(MemberNkso member);
}
