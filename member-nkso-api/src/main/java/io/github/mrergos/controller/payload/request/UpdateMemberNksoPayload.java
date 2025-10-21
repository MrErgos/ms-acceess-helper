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
    @NotNull(message = "{manager.membernkso.creating.error.registryNum_is_required}")
    @Size(max = 5, message = "{manager.membernkso.creating.error.registryNum_must_be_shorter}")
    private String registryNum;

    @NotNull(message = "{manager.membernkso.creating.error.dateAddedToRegistry_is_required}")
    private LocalDate dateAddedToRegistry;

    @Pattern(regexp = "(^\\d{1,2}\\.\\d{1,2}\\.\\d{4}$)|(^$)", message = "{manager.membernkso.creating.error.textDateAddedToRegistry_must_be_like}")
    @Size(max = 50, message = "{manager.membernkso.creating.error.textDateAddedToRegistry_must_be_shorter}")
    private String textDateAddedToRegistry;

    @Pattern(regexp = "(^«\\d{1,2}»\\s[а-яА-Я]+\\s\\d{4}\\sгода$)|(^$)", message = "{manager.membernkso.creating.error.fullTextDateAddedToRegistry_must_be_like}")
    @Size(max = 30, message = "{manager.membernkso.creating.error.fullTextDateAddedToRegistry_must_be_shorter}")
    private String fullTextDateAddedToRegistry;

    @Size(max = 60, message = "{manager.membernkso.creating.error.subjectRf_must_be_shorter}")
    private String subjectRf;

    @Size(max = 60, message = "{manager.membernkso.creating.error.city_must_be_shorter}")
    private String city;

    @Size(max = 75, message = "{manager.membernkso.creating.error.regionPartnership_must_be_shorter}")
    private String regionPartnership;

    @Size(max = 75, message = "{manager.membernkso.creating.error.rpCode_must_be_shorter}")
    private String rpCode;

    @Size(max = 40, message = "{manager.membernkso.creating.error.lastname_must_be_shorter}")
    private String lastname;

    @Size(max = 20, message = "{manager.membernkso.creating.error.firstname_must_be_shorter}")
    private String firstname;

    @Size(max = 30, message = "{manager.membernkso.creating.error.middlename_must_be_shorter}")
    private String middlename;

    private LocalDate dateOfBirth;

    @Size(max = 150, message = "{manager.membernkso.creating.error.birthPlace_must_be_shorter}")
    private String birthPlace;

    @Size(max = 15, message = "{manager.membernkso.creating.error.tin_must_be_shorter}")
    private String tin;

    @Size(max = 255, message = "{manager.membernkso.creating.error.ssn_must_be_shorter}")
    private String ssn;

    @Size(max = 15, message = "{manager.membernkso.creating.error.membershipInRp_must_be_shorter}")
    private String membershipInRp;

    @Size(max = 7, message = "{manager.membernkso.creating.error.registerNumInRp_must_be_shorter}")
    private String registerNumInRp;

    private LocalDate dateJoiningRp;
    private LocalDate dateExclusionFromRp;

    @Size(max = 30, message = "{manager.membernkso.creating.error.totalWorkExperience_must_be_shorter}")
    private String totalWorkExperience;

    @Size(max = 30, message = "{manager.membernkso.creating.error.evaluatedWorkExperience_must_be_shorter}")
    private String evaluatedWorkExperience;
    private LocalDate assessmentWorkExperience;

    @Size(max = 255, message = "{manager.membernkso.creating.error.passport_must_be_shorter}")
    private String passport; //default "паспорт 0 выдан УФМС России по ..20, код п."

    @Size(max = 255, message = "{manager.membernkso.creating.error.registrationAddress_must_be_shorter}")
    private String registrationAddress;

    @Size(max = 255, message = "{manager.membernkso.creating.error.correspondenceAddress_must_be_shorter}")
    private String correspondenceAddress;

    @Size(max = 50, message = "{manager.membernkso.creating.error.memberEmailNkso_must_be_shorter}")
    private String memberEmailNkso;

    @Size(max = 50, message = "{manager.membernkso.creating.error.memberEmailNkso2_must_be_shorter}")
    private String memberEmailNkso2;

    @Size(max = 20, message = "{manager.membernkso.creating.error.cityPhoneCode_must_be_shorter}")
    private String cityPhoneCode;

    @Size(max = 70, message = "{manager.membernkso.creating.error.contactPhone_must_be_shorter}")
    private String contactPhone;

    @Size(max = 50, message = "{manager.membernkso.creating.error.mobilePhone_must_be_shorter}")
    private String mobilePhone;

    @Size(max = 50, message = "{manager.membernkso.creating.error.textCertificateNumCriminalRecord_must_be_shorter}")
    private String textCertificateNumCriminalRecord; //default №

    @Size(max = 20, message = "{manager.membernkso.creating.error.certificateNumCriminalRecord_must_be_shorter}")
    private String certificateNumCriminalRecord;

    @Size(max = 50, message = "{manager.membernkso.creating.error.textCertificateDateCriminalRecord_must_be_shorter}")
    private String textCertificateDateCriminalRecord;
    private LocalDate certificateDateCriminalRecord;

    @Size(max = 50, message = "{manager.membernkso.creating.error.tCertificateDateCriminalRecord_must_be_shorter}")
    private String tCertificateDateCriminalRecord;

    @Size(max = 150, message = "{manager.membernkso.creating.error.dateChangeInRegisterMembers_must_be_shorter}")
    private String dateChangeInRegisterMembers;

    //Длинное поле в MS Access возможно MEMO
    private String dateChangeMembers;

    //Длинное поле в MS Access возможно MEMO
    private String dateModificationMembers;

    @Size(max = 255, message = "{manager.membernkso.creating.error.needToChangeMembers_must_be_shorter}")
    private String needToChangeMembers;

    @Size(max = 255, message = "{manager.membernkso.creating.error.adequacyNksoMember_must_be_shorter}")
    private String adequacyNksoMember;

    //Длинное поле в MS Access возможно MEMO
    private String noteNksoMember;


    public MemberNkso toMemberNkso() {
        return new MemberNkso(this.registryNum, this.dateAddedToRegistry, this.textDateAddedToRegistry,
                this.fullTextDateAddedToRegistry, this.subjectRf, this.city, this.regionPartnership,
                this.rpCode, this.lastname, this.firstname, this.middlename, this.dateOfBirth,
                this.birthPlace, this.tin, this.ssn, this.membershipInRp, this.registerNumInRp,
                this.dateJoiningRp, this.dateExclusionFromRp, this.totalWorkExperience,
                this.evaluatedWorkExperience, this.assessmentWorkExperience, this.passport,
                this.registrationAddress, this.correspondenceAddress, this.memberEmailNkso,
                this.memberEmailNkso2, this.cityPhoneCode, this.contactPhone, this.mobilePhone,
                this.textCertificateNumCriminalRecord, this.certificateNumCriminalRecord,
                this.textCertificateDateCriminalRecord, this.certificateDateCriminalRecord,
                this.tCertificateDateCriminalRecord, this.dateChangeInRegisterMembers,
                this.dateChangeMembers, this.dateModificationMembers, this.needToChangeMembers,
                this.adequacyNksoMember, this.noteNksoMember);
    }
}
