package io.github.mrergos.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "01_Члены_НКСО")
@Data
public class MemberNkso {
    @Id
    @Column(name = "[№_По_Реестру]")
    private String registryNum;

    @Column(name = "[Дата_Вкл_В_Реестр_(сокр)]")
    private LocalDate dateAddedToRegistry;

    @Column(name = "[(Т) Дата_Вкл_В_Реестр_(сокр)]")
    private String textDateAddedToRegistry;

    @Column(name = "[Дата_Вкл_В_Реестр_(полн)]")
    private String fullTextDateAddedToRegistry;

    @Column(name = "[Субъект_РФ]")
    private String subjectRf;

    @Column(name = "[Город]")
    private String city;

    @Column(name = "[Регион_Партнёрство]")
    private String regionPartnership;

    @Column(name = "[Код_РП]")
    private String rpCode;

    @Column(name = "[Фамилия]")
    private String lastname;

    @Column(name = "[Имя]")
    private String firstname;

    @Column(name = "[Отчество]")
    private String middlename;

    @Column(name = "[Дата_Рождения]")
    private LocalDate dateOfBirth;

    @Column(name = "[Место_Рождения]")
    private String birthPlace;

    @Column(name = "[ИНН]")
    private String tin;

    @Column(name = "[СНИЛС]")
    private String ssn;

    @Column(name = "[Членство_В_РП]")
    private String membershipInRp;

    @Column(name = "[Регистр_№_в_РП]")
    private String registerNumInRp;

    @Column(name = "[Дата_Вступл_ Р_РП]")
    private LocalDate dateJoiningRp;

    @Column(name = "[Дата_Искл_Из_ Р_РП]")
    private LocalDate dateExclusionFromRp;

    @Column(name = "[Т_Общий_Трудовой_Стаж]")
    private String totalWorkExperience;

    @Column(name = "[Т_Оценочный_Трудовой_Стаж]")
    private String evaluatedWorkExperience;

    @Column(name = "[Оценочный_Трудовой_Стаж]")
    private LocalDate assessmentWorkExperience; //*

    @Column(name = "[Паспорт]")
    private String passport;

    @Column(name = "[Адрес_Регистрации]")
    private String registrationAddress;

    @Column(name = "[Адрес_Корреспонденции]")
    private String correspondenceAddress;

    @Column(name = "[E-mail_члена_НКСО]")
    private String memberEmailNkso;

    @Column(name = "[E-mail_члена_НКСО_2]")
    private String memberEmailNkso2;

    @Column(name = "[Код_Города_Тел]")
    private String cityPhoneCode;

    @Column(name = "[Тел_Контактный]")
    private String contactPhone;

    @Column(name = "[Телефон_Моб]")
    private String mobilePhone;

    @Column(name = "[Текст_№_Справки_О_Судимостим]")
    private String textCertificateNumCriminalRecord;

    @Column(name = "[№_Справки_О_Судимости]")
    private String certificateNumCriminalRecord;

    @Column(name = "[Текст_Дата_Справки_О_Судимости]")
    private String textCertificateDateCriminalRecord;

    @Column(name = "[Дата_Справки_О_Судимости]")
    private LocalDate certificateDateCriminalRecord;

    @Column(name = "[Т_Дата_Справки_О_Судимости]")
    private String tCertificateDateCriminalRecord;

    @Column(name = "[Дата_Изменения_в_Реестр_Члены]")
    private String dateChangeInRegisterMembers;

    @Column(name = "[Дата_Изменениям_Члены]")
    private String dateChangeMembers; //* Длинный текст?

    @Column(name = "[Дата_Изминение_Члены]")
    private String dateModificationMembers; //* Длинный текст?

    @Column(name = "[Необходимо_Изменит_Члены]")
    private String needToChangeMembers;

    @Column(name = "[Адекватность_Члена_НКСО]")
    private String adequacyNksoMember;

    @Column(name = "[Примечание _о_Члене_НКСО]")
    private String noteNksoMember; //*


}
