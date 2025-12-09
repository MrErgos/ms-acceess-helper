package io.github.mrergos.controller;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Locale;

import static org.hamcrest.Matchers.greaterThan;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@TestPropertySource(locations = "classpath:application.yaml")
@Sql(scripts = "classpath:sql/fillup.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(scripts = "classpath:sql/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
public class MemberRestControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    JwtDecoder jwtDecoder;

    @Test
    void getMembersPageByFilter_NoFilterAndNoPage_ReturnsMembersPage() throws Exception {
        //given
        var requestBuilder = MockMvcRequestBuilders.get("/member-nkso-api/members")
                .with(jwt().jwt(builder -> builder.claim("scope", "view_members")));

        //when
        this.mockMvc.perform(requestBuilder)
        //then
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        jsonPath("$[\"totalElements\"]").value(greaterThan(1)),
                        jsonPath("$[\"content\"][?(@.registryNum == \"00001\")]").exists()
                );
    }

    @Test
    void getMembersPageByFilter_NoFilterAndPageIsValid_ReturnsMembersPage() throws Exception {
        // given
        var requestBuilder = MockMvcRequestBuilders.get("/member-nkso-api/members")
                .param("page", "1")
                .with(jwt().jwt(builder -> builder.claim("scope", "view_members")));
        //when
        this.mockMvc.perform(requestBuilder)
        //then
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        jsonPath("$[\"number\"]").value(1)
                );
    }

    @Test
    void getMembersPageByFilter_FilterIsValidAndNoPage_ReturnsMembersPage() throws Exception {
        // given
        var requestBuilder = MockMvcRequestBuilders.get("/member-nkso-api/members")
                .param("filter", "Тестов")
                .with(jwt().jwt(builder -> builder.claim("scope", "view_members")));
        //when
        this.mockMvc.perform(requestBuilder)
                //then
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        jsonPath("$[\"totalElements\"]").value(1),
                        jsonPath("$[\"content\"][?(@.registryNum == \"00001\")]").exists()
                );
    }

    @Test
    void getMembersPageByFilter_NoFilterAndPageIsNotValid_ReturnsMembersPage() throws Exception {
        // given
        var requestBuilder = MockMvcRequestBuilders.get("/member-nkso-api/members")
                .param("page", "first")
                .with(jwt().jwt(builder -> builder.claim("scope", "view_members")));
        //when
        this.mockMvc.perform(requestBuilder)
                //then
                .andExpectAll(
                        status().isBadRequest()
                );
    }

    @Test
    void getMembersPageByFilter_NoFilterAndPageIsGreaterThanExist_ReturnsMembersPage() throws Exception {
        // given
        var requestBuilder = MockMvcRequestBuilders.get("/member-nkso-api/members")
                .param("page", "3")
                .with(jwt().jwt(builder -> builder.claim("scope", "view_members")));
        //when
        this.mockMvc.perform(requestBuilder)
                //then
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$[\"content\"]").isEmpty()
                );
    }


    @Test
    void getMemberById_ReturnsMember() throws Exception {
        //given
        var requestBuilder = MockMvcRequestBuilders.get("/member-nkso-api/members/00001")
                .with(jwt().jwt(builder -> builder.claim("scope", "view_members")));
        //when
        this.mockMvc.perform(requestBuilder)
                //then
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        jsonPath("$").isNotEmpty(),
                        jsonPath("registryNum").value("00001"),
                        jsonPath("lastname").value("Тестов")
                );
    }

    @Test
    void getMemberById_RegistryNumIsInvalid_ReturnsNotFound() throws Exception {
        //given
        var requestBuilder = MockMvcRequestBuilders.get("/member-nkso-api/members/00000")
                .with(jwt().jwt(builder -> builder.claim("scope", "view_members")));
        //when
        this.mockMvc.perform(requestBuilder)
                //then
                .andExpectAll(
                        status().isNotFound()
                );
    }


    @Test
    void createMember_ReturnsCreatedMemberAndLocation() throws Exception {
        //given
        var requestBuilder = MockMvcRequestBuilders.post("/member-nkso-api/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
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
                           "tCertificateDateCriminalRecord": "\\"22\\" декабря 2023 года",
                           "dateChangeInRegisterMembers": "Пробное обновление записи — 10.03.2024",
                           "dateChangeMembers": "Пробное изменение контактов — 11.03.2024",
                           "dateModificationMembers": "Изменён тестовый e-mail — 12.03.2024",
                           "needToChangeMembers": "Необходимо техническое обновление сведений",
                           "adequacyNksoMember": "Организован, вежлив, системный",
                           "noteNksoMember": "Запись используется для расширенного тестирования"
                        }
                        """)
                .with(jwt().jwt(builder -> builder
                        .claim("scope", "edit_members")));
        //when
        this.mockMvc.perform(requestBuilder)
                //then
                .andExpectAll(
                        status().isCreated(),
                        header().string(HttpHeaders.LOCATION,"http://localhost/member-nkso-api/members/00004"),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        jsonPath("$..registryNum").value("00004"),
                        jsonPath("$..regionPartnership").value("Пилотное партнёрство Южного округа")
                );
    }

    @Test
    void createMember_PayloadIsInvalid_ReturnsProblemsDetail() throws Exception {
        //given
        var requestBuilder = MockMvcRequestBuilders.post("/member-nkso-api/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "registryNum": "00004",
                            "dateAddedToRegistry": ""
                        }
                        """)
                .locale(Locale.of("ru", "RU"))
                .with(jwt().jwt(builder -> builder
                        .claim("scope", "edit_members")));
        //when
        this.mockMvc.perform(requestBuilder)
                //then
                .andExpectAll(
                        status().isBadRequest(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON),
                        jsonPath("$..errors").isNotEmpty(),
                        jsonPath("$..errors[?(@.field == \"dateAddedToRegistry\")]").isNotEmpty(),
                        jsonPath("$..errors[?(@.field == \"dateAddedToRegistry\")].message").value("Дата включения в реестр не должна быть пустой")
                );
    }

    @Test
    void createMember_NotAuthorized_ReturnsForbidden() throws Exception {
        //given
        var requestBuilder = MockMvcRequestBuilders.post("/member-nkso-api/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "registryNum": "00004",
                            "dateAddedToRegistry": ""
                        }
                        """)
                .locale(Locale.of("ru", "RU"))
                .with(jwt().jwt(builder -> builder
                        .claim("scope", "view_members")));
        //when
        this.mockMvc.perform(requestBuilder)
                //then
                .andExpectAll(
                        status().isForbidden()
                );
    }

    @Test
    void updateMember_PayloadIsValid_ReturnsUpdatedMember() throws Exception {
        //given
        var requestBuilder = MockMvcRequestBuilders.put("/member-nkso-api/members/00003")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                           "dateAddedToRegistry": "30.03.2023",
                           "textDateAddedToRegistry": "30.03.2023",
                           "fullTextDateAddedToRegistry": "\\"30\\" марта 2023 года",
                        
                           "subjectRf": "Пробный субъект",
                           "city": "г. Фейксити",
                           "regionPartnership": "Западное пробное партнёрство ОБНОВЛЕНО",
                           "rpCode": "ПРОБА-РП-02",
                        
                           "lastname": "Фиктивов",
                           "firstname": "Дмитрий",
                           "middlename": "Сергеевич",
                        
                           "dateOfBirth": "19.08.1988",
                           "birthPlace": "г. Фейксити",
                        
                           "tin": "222222222222",
                           "ssn": "321-123-456 00",
                        
                           "membershipInRp": "Да",
                           "registerNumInRp": "RP0002",
                           "dateJoiningRp": "01.07.2020",
                           "dateExclusionFromRp": "10.07.2020",
                        
                           "totalWorkExperience": "12 лет",
                           "evaluatedWorkExperience": "11 лет",
                           "assessmentWorkExperience": "05.01.2024",
                        
                           "passport": "Паспорт 00 02 №222222 выдан Пробным управлением, код подразделения 000-222",
                        
                           "registrationAddress": "Пробный субъект, г. Фейксити, ул. Фантомная, д. 777, кв. 7",
                           "correspondenceAddress": "Пробный субъект, г. Фейксити, ул. Фантомная, d. 777, кв. 7",
                        
                           "memberEmailNkso": "dmitry.fiktiv@example.com",
                           "memberEmailNkso2": "fiktiv.dmitry@example.com",
                        
                           "cityPhoneCode": "902",
                           "contactPhone": "222-22-22",
                           "mobilePhone": "+7 (902) 222-22-22",
                        
                           "textCertificateNumCriminalRecord": "№",
                           "certificateNumCriminalRecord": "22222",
                        
                           "textCertificateDateCriminalRecord": "05.01.2024",
                           "certificateDateCriminalRecord": "05.01.2024",
                           "tCertificateDateCriminalRecord": "\\"5\\" января 2024 года",
                        
                           "dateChangeInRegisterMembers": "Техническое изменение №1 — 15.03.2024",
                           "dateChangeMembers": "Техническое изменение №2 — 16.03.2024",
                           "dateModificationMembers": "Техническое изменение №3 — 17.03.2024",
                        
                           "needToChangeMembers": "Требуется уточнение сведений",
                           "adequacyNksoMember": "Сдержанный, ответственный, технический",
                           "noteNksoMember": "Используется как третья тестовая запись"
                        }
                        """)
                .locale(Locale.of("ru", "RU"))
                .with(jwt().jwt(builder -> builder
                        .claim("scope", "edit_members")));
        //when
        this.mockMvc.perform(requestBuilder)
                //then
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        jsonPath("$").exists(),
                        jsonPath("$..registryNum").value("00003"),
                        jsonPath("$..regionPartnership").value("Западное пробное партнёрство ОБНОВЛЕНО")
                );
    }

    @Test
    void updateMember_PayloadIsInvalid_ReturnsProblemsDetail() throws Exception {
        //given
        var requestBuilder = MockMvcRequestBuilders.put("/member-nkso-api/members/00003")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                           "dateAddedToRegistry": "",
                           "textDateAddedToRegistry": "30.03.2023",
                           "fullTextDateAddedToRegistry": "\\"30\\" марта 2023 года"
                        }
                        """)
                .locale(Locale.of("ru", "RU"))
                .with(jwt().jwt(builder -> builder
                        .claim("scope", "edit_members")));
        //when
        this.mockMvc.perform(requestBuilder)
                //then
                .andExpectAll(
                        status().isBadRequest(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON),
                        jsonPath("$..errors").isNotEmpty(),
                        jsonPath("$..errors[?(@.field == \"dateAddedToRegistry\")]").isNotEmpty(),
                        jsonPath("$..errors[?(@.field == \"dateAddedToRegistry\")].message").value("Дата включения в реестр не должна быть пустой")
                );
    }

    @Test
    void updateMember_NotAuthorized_ReturnsForbidden() throws Exception {
        //given
        var requestBuilder = MockMvcRequestBuilders.put("/member-nkso-api/members/00003")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                           "dateAddedToRegistry": "",
                           "textDateAddedToRegistry": "30.03.2023",
                           "fullTextDateAddedToRegistry": "\\"30\\" марта 2023 года"
                        }
                        """)
                .locale(Locale.of("ru", "RU"))
                .with(jwt().jwt(builder -> builder
                        .claim("scope", "view_members")));
        //when
        this.mockMvc.perform(requestBuilder)
                //then
                .andExpectAll(
                        status().isForbidden()
                );
    }

    @Test
    void updateMember_RegistryNumIsInvalid_ReturnsProblemsDetail() throws Exception {
        //given
        var requestBuilder = MockMvcRequestBuilders.put("/member-nkso-api/members/00000")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                           "dateAddedToRegistry": "30.03.2023",
                           "textDateAddedToRegistry": "30.03.2023",
                           "fullTextDateAddedToRegistry": "\\"30\\" марта 2023 года",
                        
                           "subjectRf": "Пробный субъект",
                           "city": "г. Фейксити",
                           "regionPartnership": "Западное пробное партнёрство ОБНОВЛЕНО",
                           "rpCode": "ПРОБА-РП-02",
                        
                           "lastname": "Фиктивов",
                           "firstname": "Дмитрий",
                           "middlename": "Сергеевич",
                        
                           "dateOfBirth": "19.08.1988",
                           "birthPlace": "г. Фейксити",
                        
                           "tin": "222222222222",
                           "ssn": "321-123-456 00",
                        
                           "membershipInRp": "Да",
                           "registerNumInRp": "RP0002",
                           "dateJoiningRp": "01.07.2020",
                           "dateExclusionFromRp": "10.07.2020",
                        
                           "totalWorkExperience": "12 лет",
                           "evaluatedWorkExperience": "11 лет",
                           "assessmentWorkExperience": "05.01.2024",
                        
                           "passport": "Паспорт 00 02 №222222 выдан Пробным управлением, код подразделения 000-222",
                        
                           "registrationAddress": "Пробный субъект, г. Фейксити, ул. Фантомная, д. 777, кв. 7",
                           "correspondenceAddress": "Пробный субъект, г. Фейксити, ул. Фантомная, d. 777, кв. 7",
                        
                           "memberEmailNkso": "dmitry.fiktiv@example.com",
                           "memberEmailNkso2": "fiktiv.dmitry@example.com",
                        
                           "cityPhoneCode": "902",
                           "contactPhone": "222-22-22",
                           "mobilePhone": "+7 (902) 222-22-22",
                        
                           "textCertificateNumCriminalRecord": "№",
                           "certificateNumCriminalRecord": "22222",
                        
                           "textCertificateDateCriminalRecord": "05.01.2024",
                           "certificateDateCriminalRecord": "05.01.2024",
                           "tCertificateDateCriminalRecord": "\\"5\\" января 2024 года",
                        
                           "dateChangeInRegisterMembers": "Техническое изменение №1 — 15.03.2024",
                           "dateChangeMembers": "Техническое изменение №2 — 16.03.2024",
                           "dateModificationMembers": "Техническое изменение №3 — 17.03.2024",
                        
                           "needToChangeMembers": "Требуется уточнение сведений",
                           "adequacyNksoMember": "Сдержанный, ответственный, технический",
                           "noteNksoMember": "Используется как третья тестовая запись"
                        }
                        """)
                .locale(Locale.of("ru", "RU"))
                .with(jwt().jwt(builder -> builder.claim("scope", "edit_members")));
        //when
        this.mockMvc.perform(requestBuilder)
                //then
                .andExpectAll(
                        status().isBadRequest(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON),
                        jsonPath("$..detail").isNotEmpty(),
                        jsonPath("$..detail").value("Член с таким регистрационным номером не существует")
                );
    }

    @Test
    void deleteMember_RegistryNumIsValid_ReturnsNoContent() throws Exception {
        //given
        var requestBuilder = MockMvcRequestBuilders.delete("/member-nkso-api/members/00005")
                .with(jwt().jwt(builder -> builder.claim("scope", "edit_members")));
        //when
        this.mockMvc.perform(requestBuilder)
                //then
                .andExpectAll(
                        status().isNoContent()
                );
    }

    @Test
    void deleteMember_NotAuthorized_ReturnsForbidden() throws Exception {
        //given
        var requestBuilder = MockMvcRequestBuilders.delete("/member-nkso-api/members/00005")
                .with(jwt().jwt(builder -> builder.claim("scope", "view_members")));
        //when
        this.mockMvc.perform(requestBuilder)
                //then
                .andExpectAll(
                        status().isForbidden()
                );
    }
}
