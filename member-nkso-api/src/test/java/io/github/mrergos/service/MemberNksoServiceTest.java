package io.github.mrergos.service;

import io.github.mrergos.controller.payload.request.CreateMemberNksoPayload;
import io.github.mrergos.controller.payload.request.UpdateMemberNksoPayload;
import io.github.mrergos.controller.payload.response.MemberNksoPayloadResponse;
import io.github.mrergos.controller.payload.response.PageResponse;
import io.github.mrergos.ecxeption.MemberExistsException;
import io.github.mrergos.ecxeption.MemberNotFoundException;
import io.github.mrergos.entity.MemberNkso;
import io.github.mrergos.repository.MemberNksoRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
class MemberNksoServiceTest {
    MemberNksoRepository repository = Mockito.mock(MemberNksoRepository.class);
    Environment environment = Mockito.mock(Environment.class);

    static List<MemberNkso> members;

    MemberNksoService service = new MemberNksoService(repository, environment);

    @BeforeAll
    static void beforeAll() {
        members = List.of(
                new MemberNkso(
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
                ),
                new MemberNkso(
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
                ),
                new MemberNkso(
                        "00006",
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
                )
        );
    }

    @BeforeEach
    void setUp() {
        Mockito.doReturn(10).when(environment).getProperty(Mockito.any(), Mockito.any(), Mockito.any());
    }

    @Test
    void findAll_ShouldReturnPageResponse() {
        Page<MemberNkso> page = new PageImpl<>(members);
        Mockito.doReturn(page).when(repository).findAll(Mockito.any(Pageable.class));

        PageResponse<MemberNksoPayloadResponse> response = service.findAll(0);

        assertThat(response).isNotNull();
        assertThat(response.getTotalElements()).isEqualTo(3);

        Mockito.verify(repository).findAll(Mockito.any(Pageable.class));
    }

    @Test
    void findPageByFilter_OneFilter_ShouldReturnPageResponse() {
        List<MemberNkso> ivanovs = List.of(members.get(0), members.get(2));
        Page<MemberNkso> page = new PageImpl<>(ivanovs);
        Mockito.doReturn(page).when(repository).findMembersByFilters(Mockito.any(), Mockito.any());

        PageResponse<MemberNksoPayloadResponse> response = service.findPageByFilter("Иванов", 0);

        Mockito.verify(repository).findMembersByFilters(Mockito.any(), Mockito.any());
        assertThat(response).isNotNull();
        assertThat(response.getTotalElements()).isEqualTo(2);
    }

    @Test
    void findPageByFilter_TwoFilters_ShouldReturnPageResponse() {
        List<MemberNkso> ivanovs = List.of(members.get(0), members.get(2));
        Page<MemberNkso> page = new PageImpl<>(ivanovs);
        Mockito.doReturn(page).when(repository).findMembersByFilters(Mockito.any(), Mockito.any(), Mockito.any());

        PageResponse<MemberNksoPayloadResponse> response = service.findPageByFilter("Иванов Иван", 0);

        Mockito.verify(repository).findMembersByFilters(Mockito.any(), Mockito.any(), Mockito.any());
        assertThat(response).isNotNull();
        assertThat(response.getTotalElements()).isEqualTo(2);
    }

    @Test
    void findPageByFilter_ThreeFilters_ShouldReturnPageResponse() {
        List<MemberNkso> ivanov = List.of(members.get(0));
        Page<MemberNkso> page = new PageImpl<>(ivanov);
        Mockito.doReturn(page).when(repository).findMembersByFilters(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any());

        PageResponse<MemberNksoPayloadResponse> response = service.findPageByFilter("Иванов Иван Иванович", 0);

        Mockito.verify(repository).findMembersByFilters(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any());
        assertThat(response).isNotNull();
        assertThat(response.getTotalElements()).isEqualTo(1);
    }

    @Test
    void findPageByFilter_FourFilters_ShouldReturnPageResponse() {
        List<MemberNkso> ivanovs = List.of(members.get(0));
        Page<MemberNkso> page = new PageImpl<>(ivanovs);
        Mockito.doReturn(page).when(repository).findMembersByFilters(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any());

        PageResponse<MemberNksoPayloadResponse> response = service.findPageByFilter("Иванов Иван Иванович 12345", 0);

        Mockito.verify(repository).findMembersByFilters(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any());
        assertThat(response).isNotNull();
        assertThat(response.getTotalElements()).isEqualTo(1);
    }

    @Test
    void findById_registryNumIsFull_ShouldReturnOptionalMemberNksoPayloadResponse() {
        MemberNksoPayloadResponse memberNksoPayloadResponse = new MemberNksoPayloadResponse(members.get(2));
        String registryNum = memberNksoPayloadResponse.getRegistryNum();
        Mockito.doReturn(Optional.ofNullable(members.get(2))).when(repository).findById(registryNum);

        var returnedOptional = service.findById("00006");

        assertThat(returnedOptional).contains(memberNksoPayloadResponse);
        Mockito.verify(repository).findById(registryNum);
    }

    @Test
    void findById_registryNumIsOneStringDigit_ShouldReturnOptionalMemberNksoPayloadResponse() {
        MemberNksoPayloadResponse memberNksoPayloadResponse = new MemberNksoPayloadResponse(members.get(2));
        String registryNum = memberNksoPayloadResponse.getRegistryNum();
        Mockito.doReturn(Optional.ofNullable(members.get(2))).when(repository).findById(registryNum);

        var returnedOptional = service.findById("6");

        assertThat(returnedOptional).contains(memberNksoPayloadResponse);
        Mockito.verify(repository).findById(registryNum);
    }

    @Test
    void save_MemberIsValid_shouldThrowException() {
        CreateMemberNksoPayload member = new CreateMemberNksoPayload(
                "00001",
                LocalDate.of(2025, 12, 12),
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
        MemberNkso memberNkso = member.toMemberNkso();
        Mockito.doReturn(false).when(repository).existsById("00001");
        Mockito.doReturn(memberNkso).when(repository).save(Mockito.any());

        MemberNksoPayloadResponse response = service.save(member);

        assertThat(response).isNotNull();
        assertThat(response.getRegistryNum()).isEqualTo(memberNkso.getRegistryNum());
    }

    @Test
    void save_MemberIsValidButTextDateAddedToRegistryAndFullTextDateAddedToRegistryIsNull_shouldThrowException() {
        CreateMemberNksoPayload member = new CreateMemberNksoPayload(
                "00001",
                LocalDate.of(2025, 12, 12),
                null,
                null,
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
        MemberNkso memberNkso = member.toMemberNkso();
        Mockito.doReturn(false).when(repository).existsById("00001");
        Mockito.doReturn(memberNkso).when(repository).save(Mockito.any());

        MemberNksoPayloadResponse response = service.save(member);

        assertThat(response).isNotNull();
        assertThat(response.getRegistryNum()).isEqualTo(memberNkso.getRegistryNum());
    }

    @Test
    void save_MemberDateAddedToRegistryIsNull_shouldThrowException() {
        CreateMemberNksoPayload member = new CreateMemberNksoPayload(
                "00001",
                null,
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
        MemberNkso memberNkso = member.toMemberNkso();
        Mockito.doReturn(false).when(repository).existsById("00001");
        Mockito.doReturn(memberNkso).when(repository).save(Mockito.any());

        MemberNksoPayloadResponse response = service.save(member);

        assertThat(response).isNotNull();
        assertThat(response.getRegistryNum()).isEqualTo(memberNkso.getRegistryNum());
    }

    @Test
    void save_MemberIsAlreadyExist_shouldThrowException() {
        CreateMemberNksoPayload existedMember = Mockito.mock(CreateMemberNksoPayload.class);
        Mockito.doReturn("12345").when(existedMember).getRegistryNum();
        Mockito.doReturn(true).when(repository).existsById("12345");

        Assertions.assertThatThrownBy(() -> service.save(existedMember))
                .isInstanceOf(MemberExistsException.class)
                .hasMessage("{manager.member.creating.error.member_is_already_exists}");
    }


    @Test
    void update_MemberIsNotExist_shouldThrowException() {
        UpdateMemberNksoPayload  updateMemberNksoPayload = Mockito.mock(UpdateMemberNksoPayload.class);
        String registryNum = "00001";
        Mockito.doReturn(Optional.empty()).when(repository).findById(registryNum);

        Assertions.assertThatThrownBy(() -> service.update(updateMemberNksoPayload, registryNum))
                .isInstanceOf(MemberNotFoundException.class)
                .hasMessage("{manager.member.updating.error.member_does_not_exist}");
    }

    @Test
    void update_MemberIsValid_ShouldReturnMemberNksoPayloadResponse() {
        UpdateMemberNksoPayload updateMemberNksoPayload = new UpdateMemberNksoPayload(
                LocalDate.of(2020, 5, 10),
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
        String registryNum = "12345";
        Mockito.doReturn(Optional.ofNullable(updateMemberNksoPayload.toMemberNkso())).when(repository).findById(registryNum);
        Mockito.doReturn(updateMemberNksoPayload.toMemberNkso()).when(repository).save(Mockito.any());

        service.update(updateMemberNksoPayload, registryNum);

        ArgumentCaptor<MemberNkso> captor = ArgumentCaptor.forClass(MemberNkso.class);
        Mockito.verify(repository).save(captor.capture());

        MemberNkso updatedCaptured = captor.getValue();

        assertThat(updatedCaptured.getTextDateAddedToRegistry()).isEqualTo("10.05.2020");
        assertThat(updatedCaptured.getFullTextDateAddedToRegistry()).isEqualTo("\"10\" мая 2020 года");
    }

    @Test
    void delete_ShouldInvokeRepositoryDeleteMethod() {
        Mockito.doNothing().when(repository).deleteById(Mockito.any());

        service.delete("00001");
        Mockito.verify(repository).deleteById(Mockito.any());
    }
}