package io.github.mrergos.controller;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import io.github.mrergos.client.PageResponse;
import io.github.mrergos.entity.MemberNkso;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@TestPropertySource(locations = "classpath:application.yaml")
@WireMockTest(httpPort = 54321)
class MemberControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Test
    void getMemberList_ReturnMembersPage() throws Exception {
        //given
        var requestBuilder = MockMvcRequestBuilders.get("/members")
                .with(user("admin").roles("EDITOR"));

        WireMock.stubFor(WireMock.get(WireMock.urlPathMatching("/member-nkso-api/members"))
                .withQueryParam("filter", WireMock.equalTo(""))
                .withQueryParam("page", WireMock.equalTo("0"))
                .willReturn(WireMock.ok(
                         """
                               {
                                   "content": [
                                       {
                                           "registryNum": "00004",
                                           "dateAddedToRegistry": "14.09.2022",
                                           "textDateAddedToRegistry": "14.09.2022",
                                           "fullTextDateAddedToRegistry": "\\"14\\" сентября 2022 года",
                                           "subjectRf": "Экспериментальная область",
                                           "city": "г. Новостройск",
                                           "regionPartnership": "Пилотное партнёрство Южного округа",
                                           "rpCode": "ПИЛОТ-РП-03",
                                           "lastname": "Примеров",
                                           "firstname": "Сергей",
                                           "middlename": "Андреевич",
                                           "dateOfBirth": "05.12.1991",
                                           "birthPlace": "г. Новостройск",
                                           "tin": "333333333333",
                                           "ssn": "555-444-333 00",
                                           "membershipInRp": "Да",
                                           "registerNumInRp": "RP0003",
                                           "dateJoiningRp": "10.02.2020",
                                           "dateExclusionFromRp": "10.02.2021",
                                           "totalWorkExperience": "9 лет",
                                           "evaluatedWorkExperience": "8 лет",
                                           "assessmentWorkExperience": "22.12.2023",
                                           "passport": "Паспорт 00 03 №333333 выдан Экспериментальным отделом, код подразделения 000-333",
                                           "registrationAddress": "Экспериментальная область, г. Новостройск, ул. Нулевая, д. 555, кв. 55",
                                           "correspondenceAddress": "Экспериментальная область, г. Новостройск, ул. Нулевая, д. 555, кв. 55",
                                           "memberEmailNkso": "sergey.primerov@example.com",
                                           "memberEmailNkso2": "primerov.sergey@example.com",
                                           "cityPhoneCode": "903",
                                           "contactPhone": "333-33-33",
                                           "mobilePhone": "+7 (903) 333-33-33",
                                           "textCertificateNumCriminalRecord": "№",
                                           "certificateNumCriminalRecord": "33333",
                                           "textCertificateDateCriminalRecord": "22.12.2023",
                                           "certificateDateCriminalRecord": "22.12.2023",
                                           "dateChangeInRegisterMembers": "Пробное обновление записи — 10.03.2024",
                                           "dateChangeMembers": "Пробное изменение контактов — 11.03.2024",
                                           "dateModificationMembers": "Изменён тестовый e-mail — 12.03.2024",
                                           "needToChangeMembers": "Необходимо техническое обновление сведений",
                                           "adequacyNksoMember": "Организован, вежлив, системный",
                                           "noteNksoMember": "Запись используется для расширенного тестирования",
                                           "tcertificateDateCriminalRecord": "\\"22\\" декабря 2023 года"
                                       }
                                   ],
                                   "totalPages": 1,
                                   "totalElements": 1,
                                   "size": 2,
                                   "number": 0,
                                   "first": true,
                                   "last": true
                               }
                               """).withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)));

        //when
        this.mockMvc.perform(requestBuilder)
                //then
                .andExpectAll(
                        status().isOk(),
                        view().name("members"),
                        model().attribute("filter", ""),
                        model().attribute("membersPage", new PageResponse<MemberNkso>(
                                List.of(
                                        MemberNkso.builder()
                                                .registryNum("00004")
                                                .dateAddedToRegistry(LocalDate.parse("14.09.2022", DateTimeFormatter.ofPattern("dd.MM.yyyy")))
                                                .textDateAddedToRegistry("14.09.2022")
                                                .fullTextDateAddedToRegistry("\"14\" сентября 2022 года")
                                                .subjectRf("Экспериментальная область")
                                                .city("г. Новостройск")
                                                .regionPartnership("Пилотное партнёрство Южного округа")
                                                .rpCode("ПИЛОТ-РП-03")
                                                .lastname("Примеров")
                                                .firstname("Сергей")
                                                .middlename("Андреевич")
                                                .dateOfBirth(LocalDate.parse("05.12.1991", DateTimeFormatter.ofPattern("dd.MM.yyyy")))
                                                .birthPlace("г. Новостройск")
                                                .tin("333333333333")
                                                .ssn("555-444-333 00")
                                                .membershipInRp("Да")
                                                .registerNumInRp("RP0003")
                                                .dateJoiningRp(LocalDate.parse("10.02.2020", DateTimeFormatter.ofPattern("dd.MM.yyyy")))
                                                .dateExclusionFromRp(LocalDate.parse("10.02.2021", DateTimeFormatter.ofPattern("dd.MM.yyyy")))
                                                .totalWorkExperience("9 лет")
                                                .evaluatedWorkExperience("8 лет")
                                                .assessmentWorkExperience(LocalDate.parse("22.12.2023", DateTimeFormatter.ofPattern("dd.MM.yyyy")))
                                                .passport("Паспорт 00 03 №333333 выдан Экспериментальным отделом, код подразделения 000-333")
                                                .registrationAddress("Экспериментальная область, г. Новостройск, ул. Нулевая, д. 555, кв. 55")
                                                .correspondenceAddress("Экспериментальная область, г. Новостройск, ул. Нулевая, д. 555, кв. 55")
                                                .memberEmailNkso("sergey.primerov@example.com")
                                                .memberEmailNkso2("primerov.sergey@example.com")
                                                .cityPhoneCode("903")
                                                .contactPhone("333-33-33")
                                                .mobilePhone("+7 (903) 333-33-33")
                                                .textCertificateNumCriminalRecord("№")
                                                .certificateNumCriminalRecord("33333")
                                                .textCertificateDateCriminalRecord("22.12.2023")
                                                .certificateDateCriminalRecord(LocalDate.parse("22.12.2023", DateTimeFormatter.ofPattern("dd.MM.yyyy")))
                                                .dateChangeInRegisterMembers("Пробное обновление записи — 10.03.2024")
                                                .dateChangeMembers("Пробное изменение контактов — 11.03.2024")
                                                .dateModificationMembers("Изменён тестовый e-mail — 12.03.2024")
                                                .needToChangeMembers("Необходимо техническое обновление сведений")
                                                .adequacyNksoMember("Организован, вежлив, системный")
                                                .noteNksoMember("Запись используется для расширенного тестирования")
                                                .tCertificateDateCriminalRecord("\"22\" декабря 2023 года")
                                                .build()
                                ),
                                1,
                                1,
                                2,
                                0,
                                true,
                                true
                        ))
                );

    }

}