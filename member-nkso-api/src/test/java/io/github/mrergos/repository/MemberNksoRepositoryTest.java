package io.github.mrergos.repository;


import io.github.mrergos.MemberApiApplication;
import io.github.mrergos.entity.MemberNkso;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = MemberApiApplication.class)
@TestPropertySource(locations = "classpath:application.yaml")
class MemberNksoRepositoryTest {
    @Autowired
    MemberNksoRepository repository;
//Test data are artificial
    @BeforeEach
    void setup() {
        MemberNkso memberNkso1 = new MemberNkso(
                "12345",
                LocalDate.of(2023, 5, 10),
                "10.05.2023",
                "\"10\" мая 2023 года",
                "Московская область",
                "Москва",
                "Московское РП",
                "RP123",
                "Иванов",
                "Иван",
                "Иванович",
                LocalDate.of(1985, 3, 15),
                "г. Москва",
                "123456789012",
                "112-233-445 95",
                "Член РП",
                "56789",
                LocalDate.of(2020, 1, 15),
                null,
                "15 лет",
                "10 лет",
                LocalDate.of(2021, 12, 1),
                "паспорт 0 выдан УФМС России по Москве, код 770-001",
                "г. Москва, ул. Ленина, д. 10",
                "г. Москва, ул. Пушкина, д. 12",
                "ivanov@example.com",
                "ivanov2@example.com",
                "495",
                "123-45-67",
                "+7-999-123-4567",
                "№12345",
                "12345",
                "от 10.01.2024",
                LocalDate.of(2024, 1, 10),
                "10.01.2024",
                "10.01.2025",
                "Изменения от 10.01.2025",
                "Модификация 2025",
                "Не требуется",
                "Адекватен",
                "Примечание о члене НКСО"
        );

        MemberNkso memberNkso2 = new MemberNkso(
                "54321",
                LocalDate.of(2024, 2, 20),
                "20.02.2024",
                "\"20\" февраля 2024 года",
                "Республика Татарстан",
                "Казань",
                "Татарстанское РП",
                "RP777",
                "Сидоров",
                "Алексей",
                "Петрович",
                LocalDate.of(1990, 7, 25),
                "г. Казань",
                "987654321098",
                "112-556-778 99",
                "Член РП",
                "76543",
                LocalDate.of(2021, 6, 10),
                null,
                "12 лет",
                "8 лет",
                LocalDate.of(2022, 3, 5),
                "паспорт 1 выдан УФМС России по Татарстану, код 160-002",
                "г. Казань, ул. Баумана, д. 5",
                "г. Казань, ул. Кремлевская, д. 8",
                "sidorov@example.com",
                "sidorov2@example.com",
                "843",
                "222-33-44",
                "+7-917-555-6677",
                "№67890",
                "67890",
                "от 15.02.2024",
                LocalDate.of(2024, 2, 15),
                "15.02.2024",
                "15.03.2025",
                "Изменения от 15.03.2025",
                "Модификация 2025",
                "Не требуется",
                "Адекватен",
                "Дополнительное примечание"
        );

        MemberNkso memberNkso3 = new MemberNkso(
                "12346",
                LocalDate.of(2023, 5, 10),
                "10.05.2023",
                "\"10\" мая 2023 года",
                "Московская область",
                "Москва",
                "Московское РП",
                "RP123",
                "Иванов",
                "Иван",
                "Владимирович",
                LocalDate.of(1985, 3, 15),
                "г. Москва",
                "123456789012",
                "112-233-445 95",
                "Член РП",
                "56789",
                LocalDate.of(2020, 1, 15),
                null,
                "15 лет",
                "10 лет",
                LocalDate.of(2021, 12, 1),
                "паспорт 0 выдан УФМС России по Москве, код 770-001",
                "г. Москва, ул. Ленина, д. 10",
                "г. Москва, ул. Пушкина, д. 12",
                "ivanov@example.com",
                "ivanov2@example.com",
                "495",
                "123-45-67",
                "+7-999-123-4567",
                "№12345",
                "12345",
                "от 10.01.2024",
                LocalDate.of(2024, 1, 10),
                "10.01.2024",
                "10.01.2025",
                "Изменения от 10.01.2025",
                "Модификация 2025",
                "Не требуется",
                "Адекватен",
                "Примечание о члене НКСО"
        );

        MemberNkso savedMember1 = repository.save(memberNkso1);
        MemberNkso savedMember2 = repository.save(memberNkso2);
        MemberNkso savedMember3 = repository.save(memberNkso3);

        assertThat(savedMember1).isNotNull();
        assertThat(savedMember1).isEqualTo(memberNkso1);

        assertThat(savedMember2).isNotNull();
        assertThat(savedMember2).isEqualTo(memberNkso2);

        assertThat(savedMember3).isNotNull();
        assertThat(savedMember3).isEqualTo(memberNkso3);
    }

    @Test
    void findAll_ValidPageable_ShouldReturnTwoMembers() {
        Pageable pageable = PageRequest.of(0, 10);

        Iterable<MemberNkso> listOfMembers = repository.findAll(pageable);
        assertThat(listOfMembers).isNotNull();
        assertThat(listOfMembers).hasSize(3);
    }

    @Test
    void findById_ValidId_ShouldReturnMemberNkso() {
        MemberNkso memberNkso = new MemberNkso(
                "54321",
                LocalDate.of(2024, 2, 20),
                "20.02.2024",
                "\"20\" февраля 2024 года",
                "Республика Татарстан",
                "Казань",
                "Татарстанское РП",
                "RP777",
                "Сидоров",
                "Алексей",
                "Петрович",
                LocalDate.of(1990, 7, 25),
                "г. Казань",
                "987654321098",
                "112-556-778 99",
                "Член РП",
                "76543",
                LocalDate.of(2021, 6, 10),
                null,
                "12 лет",
                "8 лет",
                LocalDate.of(2022, 3, 5),
                "паспорт 1 выдан УФМС России по Татарстану, код 160-002",
                "г. Казань, ул. Баумана, д. 5",
                "г. Казань, ул. Кремлевская, д. 8",
                "sidorov@example.com",
                "sidorov2@example.com",
                "843",
                "222-33-44",
                "+7-917-555-6677",
                "№67890",
                "67890",
                "от 15.02.2024",
                LocalDate.of(2024, 2, 15),
                "15.02.2024",
                "15.03.2025",
                "Изменения от 15.03.2025",
                "Модификация 2025",
                "Не требуется",
                "Адекватен",
                "Дополнительное примечание"
        );

        Optional<MemberNkso> foundMember = repository.findById("54321");
        assertThat(foundMember).isNotEmpty();
        assertThat(foundMember).contains(memberNkso);
    }

    @Test
    void findMembersByFilters_ValidOneFilter_ShouldReturnMembers() {
        String filter = "Иванов";
        Pageable pageable = PageRequest.of(0, 10);

        Page<MemberNkso> foundMember = repository.findMembersByFilters(filter, pageable);
        assertThat(foundMember).isNotEmpty();
        assertThat(foundMember.getTotalElements()).isEqualTo(2);
    }

    @Test
    void findMembersByFilters_ValidTwoFilter_ShouldReturnMembers() {
        String filter = "Иванов";
        String filter2 = "Иван";
        Pageable pageable = PageRequest.of(0, 10);

        Page<MemberNkso> foundMember = repository.findMembersByFilters(filter, filter2, pageable);
        assertThat(foundMember).isNotEmpty();
        assertThat(foundMember.getTotalElements()).isEqualTo(2);
    }

    @Test
    void findMembersByFilters_ValidThreeFilter_ShouldReturnMembers() {
        String filter = "Иванов";
        String filter2 = "Иван";
        String filter3 = "Иванович";
        Pageable pageable = PageRequest.of(0, 10);

        Page<MemberNkso> foundMember = repository.findMembersByFilters(filter, filter2, filter3, pageable);
        assertThat(foundMember).isNotEmpty();
        assertThat(foundMember.getTotalElements()).isEqualTo(1);
    }

    @Test
    void findMembersByFilters_ValidFourFilter_ShouldReturnMembers() {
        String filter = "Иванов";
        String filter2 = "Иван";
        String filter3 = "Иванович";
        String filter4 = "12345";
        Pageable pageable = PageRequest.of(0, 10);

        Page<MemberNkso> foundMember = repository.findMembersByFilters(filter, filter2, filter3, filter4, pageable);
        assertThat(foundMember).isNotEmpty();
        assertThat(foundMember.getTotalElements()).isEqualTo(1);
    }

    @Test
    void existsById_ValidId_ShouldReturnTrue() {
        boolean exists = repository.existsById("54321");
        assertThat(exists).isTrue();
    }

    @Test
    void deleteById_ValidId_ShouldReturnTrue() {
        MemberNkso memberNksoForDelete = new MemberNkso(
                "00001",
                LocalDate.of(2023, 5, 10),
                "10.05.2023",
                "\"10\" мая 2023 года",
                "Московская область",
                "Москва",
                "Московское РП",
                "RP123",
                "Удаленов",
                "Удален",
                "Удаленович",
                LocalDate.of(1985, 3, 15),
                "г. Москва",
                "123456789012",
                "112-233-445 95",
                "Член РП",
                "56789",
                LocalDate.of(2020, 1, 15),
                null,
                "15 лет",
                "10 лет",
                LocalDate.of(2021, 12, 1),
                "паспорт 0 выдан УФМС России по Москве, код 770-001",
                "г. Москва, ул. Ленина, д. 10",
                "г. Москва, ул. Пушкина, д. 12",
                "ivanov@example.com",
                "ivanov2@example.com",
                "495",
                "123-45-67",
                "+7-999-123-4567",
                "№12345",
                "12345",
                "от 10.01.2024",
                LocalDate.of(2024, 1, 10),
                "10.01.2024",
                "10.01.2025",
                "Изменения от 10.01.2025",
                "Модификация 2025",
                "Не требуется",
                "Адекватен",
                "Примечание о члене НКСО"
        );
        repository.save(memberNksoForDelete);

        repository.deleteById("00001");
        boolean result = repository.existsById("00001");
        assertThat(result).isFalse();
    }
}