package io.github.mrergos.repository;

import io.github.mrergos.entity.MemberNkso;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class MemberNksoRepositoryTest {
    @Autowired
    MemberNksoRepository repository;

    @BeforeEach
    void setUp() {

    }

    @Test
    void findAll_ReturnAllMembers() {
        Iterable<MemberNkso> members = repository.findAll();

        assertThat(members).isNotNull();
        assertThat(members).isNotEmpty();
    }
}