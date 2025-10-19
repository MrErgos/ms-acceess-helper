package io.github.mrergos.client;

import io.github.mrergos.entity.MemberNkso;

import java.util.List;

public interface MemberClient {
    List<MemberNkso> findAllMembers();
    MemberNkso findMemberById(String registryNum);
}
