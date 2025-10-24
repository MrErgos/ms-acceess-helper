package io.github.mrergos.controller.payload.request;

import io.github.mrergos.entity.MemberNkso;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class UpdateMemberNksoPayload {
    @NotNull(message = "{manager.member.creating.error.dateAddedToRegistry_is_required}")
    private LocalDate dateAddedToRegistry;

    @Pattern(regexp = "(^\\d{1,2}\\.\\d{1,2}\\.\\d{4}$)|(^$)", message = "{manager.member.creating.error.textDateAddedToRegistry_must_be_like}")
    @Size(max = 50, message = "{manager.member.creating.error.textDateAddedToRegistry_must_be_shorter}")
    private String textDateAddedToRegistry;

    @Pattern(regexp = "(^\"\\d{1,2}\"\\s[а-яА-Я]+\\s\\d{4}\\sгода$)|(^$)", message = "{manager.member.creating.error.fullTextDateAddedToRegistry_must_be_like}")
    @Size(max = 30, message = "{manager.member.creating.error.fullTextDateAddedToRegistry_must_be_shorter}")
    private String fullTextDateAddedToRegistry;

    @Size(max = 60, message = "{manager.member.creating.error.subjectRf_must_be_shorter}")
    private String subjectRf;

    @Size(max = 60, message = "{manager.member.creating.error.city_must_be_shorter}")
    private String city;

    @Size(max = 75, message = "{manager.member.creating.error.regionPartnership_must_be_shorter}")
    private String regionPartnership;

    @Size(max = 75, message = "{manager.member.creating.error.rpCode_must_be_shorter}")
    private String rpCode;

    @Size(max = 40, message = "{manager.member.creating.error.lastname_must_be_shorter}")
    private String lastname;

    @Size(max = 20, message = "{manager.member.creating.error.firstname_must_be_shorter}")
    private String firstname;

    @Size(max = 30, message = "{manager.member.creating.error.middlename_must_be_shorter}")
    private String middlename;

    private LocalDate dateOfBirth;

    @Size(max = 150, message = "{manager.member.creating.error.birthPlace_must_be_shorter}")
    private String birthPlace;

    @Size(max = 15, message = "{manager.member.creating.error.tin_must_be_shorter}")
    private String tin;

    @Size(max = 255, message = "{manager.member.creating.error.ssn_must_be_shorter}")
    private String ssn;

    @Size(max = 15, message = "{manager.member.creating.error.membershipInRp_must_be_shorter}")
    private String membershipInRp;

    @Size(max = 7, message = "{manager.member.creating.error.registerNumInRp_must_be_shorter}")
    private String registerNumInRp;

    private LocalDate dateJoiningRp;
    private LocalDate dateExclusionFromRp;

    @Size(max = 30, message = "{manager.member.creating.error.totalWorkExperience_must_be_shorter}")
    private String totalWorkExperience;

    @Size(max = 30, message = "{manager.member.creating.error.evaluatedWorkExperience_must_be_shorter}")
    private String evaluatedWorkExperience;
    private LocalDate assessmentWorkExperience;

    @Size(max = 255, message = "{manager.member.creating.error.passport_must_be_shorter}")
    private String passport; //default "паспорт 0 выдан УФМС России по ..20, код п."

    @Size(max = 255, message = "{manager.member.creating.error.registrationAddress_must_be_shorter}")
    private String registrationAddress;

    @Size(max = 255, message = "{manager.member.creating.error.correspondenceAddress_must_be_shorter}")
    private String correspondenceAddress;

    @Size(max = 50, message = "{manager.member.creating.error.memberEmailNkso_must_be_shorter}")
    private String memberEmailNkso;

    @Size(max = 50, message = "{manager.member.creating.error.memberEmailNkso2_must_be_shorter}")
    private String memberEmailNkso2;

    @Size(max = 20, message = "{manager.member.creating.error.cityPhoneCode_must_be_shorter}")
    private String cityPhoneCode;

    @Size(max = 70, message = "{manager.member.creating.error.contactPhone_must_be_shorter}")
    private String contactPhone;

    @Size(max = 50, message = "{manager.member.creating.error.mobilePhone_must_be_shorter}")
    private String mobilePhone;

    @Size(max = 50, message = "{manager.member.creating.error.textCertificateNumCriminalRecord_must_be_shorter}")
    private String textCertificateNumCriminalRecord; //default №

    @Size(max = 20, message = "{manager.member.creating.error.certificateNumCriminalRecord_must_be_shorter}")
    private String certificateNumCriminalRecord;

    @Size(max = 50, message = "{manager.member.creating.error.textCertificateDateCriminalRecord_must_be_shorter}")
    private String textCertificateDateCriminalRecord;
    private LocalDate certificateDateCriminalRecord;

    @Size(max = 50, message = "{manager.member.creating.error.tCertificateDateCriminalRecord_must_be_shorter}")
    private String tCertificateDateCriminalRecord;

    @Size(max = 150, message = "{manager.member.creating.error.dateChangeInRegisterMembers_must_be_shorter}")
    private String dateChangeInRegisterMembers;

    //Длинное поле в MS Access возможно MEMO
    private String dateChangeMembers;

    //Длинное поле в MS Access возможно MEMO
    private String dateModificationMembers;

    @Size(max = 255, message = "{manager.member.creating.error.needToChangeMembers_must_be_shorter}")
    private String needToChangeMembers;

    @Size(max = 255, message = "{manager.member.creating.error.adequacyNksoMember_must_be_shorter}")
    private String adequacyNksoMember;

    //Длинное поле в MS Access возможно MEMO
    private String noteNksoMember;


    public MemberNkso toMemberNkso() {
        return MemberNkso.builder()
                .dateAddedToRegistry(this.dateAddedToRegistry)
                .textDateAddedToRegistry(this.textDateAddedToRegistry)
                .fullTextDateAddedToRegistry(this.fullTextDateAddedToRegistry)
                .subjectRf(this.subjectRf)
                .city(this.city)
                .regionPartnership(this.regionPartnership)
                .rpCode(this.rpCode)
                .lastname(this.lastname)
                .firstname(this.firstname)
                .middlename(this.middlename)
                .dateOfBirth(this.dateOfBirth)
                .birthPlace(this.birthPlace)
                .tin(this.tin)
                .ssn(this.ssn)
                .membershipInRp(this.membershipInRp)
                .registerNumInRp(this.registerNumInRp)
                .dateJoiningRp(this.dateJoiningRp)
                .dateExclusionFromRp(this.dateExclusionFromRp)
                .totalWorkExperience(this.totalWorkExperience)
                .evaluatedWorkExperience(this.evaluatedWorkExperience)
                .assessmentWorkExperience(this.assessmentWorkExperience)
                .passport(this.passport)
                .registrationAddress(this.registrationAddress)
                .correspondenceAddress(this.correspondenceAddress)
                .memberEmailNkso(this.memberEmailNkso)
                .memberEmailNkso2(this.memberEmailNkso2)
                .cityPhoneCode(this.cityPhoneCode)
                .contactPhone(this.contactPhone)
                .mobilePhone(this.mobilePhone)
                .textCertificateNumCriminalRecord(this.textCertificateNumCriminalRecord)
                .certificateNumCriminalRecord(this.certificateNumCriminalRecord)
                .textCertificateDateCriminalRecord(this.textCertificateDateCriminalRecord)
                .certificateDateCriminalRecord(this.certificateDateCriminalRecord)
                .tCertificateDateCriminalRecord(this.tCertificateDateCriminalRecord)
                .dateChangeInRegisterMembers(this.dateChangeInRegisterMembers)
                .dateChangeMembers(this.dateChangeMembers)
                .dateModificationMembers(this.dateModificationMembers)
                .needToChangeMembers(this.needToChangeMembers)
                .adequacyNksoMember(this.adequacyNksoMember)
                .noteNksoMember(this.noteNksoMember)
                .build();
    }
}
