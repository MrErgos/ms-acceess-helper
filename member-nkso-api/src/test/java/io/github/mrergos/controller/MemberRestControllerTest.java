package io.github.mrergos.controller;

import io.github.mrergos.controller.payload.request.CreateMemberNksoPayload;
import io.github.mrergos.controller.payload.request.UpdateMemberNksoPayload;
import io.github.mrergos.controller.payload.response.MemberNksoPayloadResponse;
import io.github.mrergos.controller.payload.response.PageResponse;
import io.github.mrergos.entity.MemberNkso;
import io.github.mrergos.service.MemberNksoService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberRestControllerTest {
    @Mock
    MemberNksoService memberNksoService;

    @InjectMocks
    MemberRestController memberRestController;

    static List<MemberNkso> members;
    @BeforeAll
    static void setup() {
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

    @Test
    void getMembersPageByFilter_FilterIsBlank_ReturnsAllMembers() {
        //given
        String filter = "";
        int page = 0;
        var pageResponse = new PageResponse<MemberNksoPayloadResponse>();
        pageResponse.setTotalElements(3);
        doReturn(pageResponse).when(memberNksoService).findAll(0);
        //when
        var response = memberRestController.getMembersPageByFilter(filter, page);
        //then
        assertThat(response.getTotalElements()).isEqualTo(3);
        verify(memberNksoService).findAll(0);
        verifyNoMoreInteractions(memberNksoService);
    }

    @Test
    void getMembersPageByFilter_FilterIsNotEmpty_ReturnsAllMembersByFilter() {
        //given
        String filter = "Иванов";
        int page = 0;
        var pageResponse = new PageResponse<MemberNksoPayloadResponse>();
        pageResponse.setTotalElements(2);
        doReturn(pageResponse).when(memberNksoService).findPageByFilter("Иванов", 0);
        //when
        var response = memberRestController.getMembersPageByFilter(filter, page);
        //then
        assertThat(response.getTotalElements()).isEqualTo(2);
        verify(memberNksoService).findPageByFilter("Иванов",0);
        verifyNoMoreInteractions(memberNksoService);
    }

    @Test
    void getMemberById_RegistryNumIsExist_ReturnsMemberNksoPayloadResponse() {
        //given
        String registryNum = "12345";
        doReturn(Optional.of(new MemberNksoPayloadResponse(members.get(0)))).when(memberNksoService).findById("12345");
        //when
        var member = memberRestController.getMemberById(registryNum);
        //then
        assertThat(member.getRegistryNum()).isEqualTo("12345");
        verify(memberNksoService).findById("12345");
        verifyNoMoreInteractions(memberNksoService);
    }

    @Test
    void getMemberById_RegistryNumIsNotExist_ThrowsNoSuchElementException() {
        //given
        String registryNum = "00001";
        doReturn(Optional.empty())
                .when(memberNksoService)
                .findById("00001");
        //when
        assertThatThrownBy(() -> memberRestController.getMemberById(registryNum))
                //then
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("{manager.errors.member-nkso-not-found}");
        verify(memberNksoService).findById("00001");
        verifyNoMoreInteractions(memberNksoService);
    }

    @Test
    void createMember_PayloadIsValid_ReturnsResponseEntityWithCreatedStatus() {
        //given
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("http://localhost:8081/member-nkso-api/members/");
        CreateMemberNksoPayload payload = createMemberNksoPayload(1);
        doReturn(new MemberNksoPayloadResponse(payload.toMemberNkso())).when(memberNksoService).save(notNull());
        //when
        var response = memberRestController.createMember(payload, builder);
        //then
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getHeaders().getLocation().toString()).isEqualTo("http://localhost:8081/member-nkso-api/members/00001");
        verify(memberNksoService).save(notNull());
        verifyNoMoreInteractions(memberNksoService);
    }

    @Test
    void updateMember_PayloadIsValid_ReturnsMemberNksoPayloadResponse() {
        //given
        UpdateMemberNksoPayload updateMemberNksoPayload = new UpdateMemberNksoPayload();
        updateMemberNksoPayload.setDateAddedToRegistry(LocalDate.of(2020, 5, 10));
        String registryNum = "12345";
        doReturn(new MemberNksoPayloadResponse(members.get(0))).when(memberNksoService).update(notNull(), eq("12345"));
        //when
        memberRestController.updateMember(updateMemberNksoPayload, registryNum);
        verify(memberNksoService).update(notNull(), eq("12345"));
        verifyNoMoreInteractions(memberNksoService);
    }

    @Test
    void deleteMember_ReturnsVoid() {
        //given
        doNothing().when(memberNksoService).delete("12345");
        //when
        memberRestController.deleteMember("12345");
        //then
        verify(memberNksoService).delete("12345");
        verifyNoMoreInteractions(memberNksoService);
    }

    @Test
    void handleNotFound_ReturnsVoid() {
        //given
        //when
        memberRestController.handleNotFound(new NoSuchElementException());
        //then
    }

    private CreateMemberNksoPayload createMemberNksoPayload(int value) {
        return new CreateMemberNksoPayload(
                "0".repeat(5 - (value+"").length()) + value,
                LocalDate.of(2023, 5, 10),
                "10.05.2023",
                "\"10\" мая 2023 года",
                "Московская область" + value,
                "Москва"  + value,
                "Московское РП"  + value,
                "RP123",
                "Иванов" + value,
                "Иван" + value,
                "Иванович" + value,
                LocalDate.of(1985, 3, 15),
                "г. Москва" + value,
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
    }
}