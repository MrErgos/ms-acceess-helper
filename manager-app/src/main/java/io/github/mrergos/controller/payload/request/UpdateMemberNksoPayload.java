package io.github.mrergos.controller.payload.request;

import io.github.mrergos.entity.MemberNkso;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class UpdateMemberNksoPayload {
    private LocalDate dateAddedToRegistry;
    private String textDateAddedToRegistry;
    private String fullTextDateAddedToRegistry;
    private String subjectRf;
    private String city;
    private String regionPartnership;
    private String rpCode;
    private String lastname;
    private String firstname;
    private String middlename;
    private LocalDate dateOfBirth;
    private String birthPlace;
    private String tin;
    private String ssn;
    private String membershipInRp;
    private String registerNumInRp;
    private LocalDate dateJoiningRp;
    private LocalDate dateExclusionFromRp;
    private String totalWorkExperience;
    private String evaluatedWorkExperience;
    private LocalDate assessmentWorkExperience;
    private String passport;
    private String registrationAddress;
    private String correspondenceAddress;
    private String memberEmailNkso;
    private String memberEmailNkso2;
    private String cityPhoneCode;
    private String contactPhone;
    private String mobilePhone;
    private String textCertificateNumCriminalRecord;
    private String certificateNumCriminalRecord;
    private String textCertificateDateCriminalRecord;
    private LocalDate certificateDateCriminalRecord;
    private String tCertificateDateCriminalRecord;
    private String dateChangeInRegisterMembers;
    private String dateChangeMembers;
    private String dateModificationMembers;
    private String needToChangeMembers;
    private String adequacyNksoMember;
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
