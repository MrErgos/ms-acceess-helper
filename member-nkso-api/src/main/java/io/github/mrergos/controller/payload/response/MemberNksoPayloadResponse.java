package io.github.mrergos.controller.payload.response;

import io.github.mrergos.entity.MemberNkso;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class MemberNksoPayloadResponse {
    private String registryNum;
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

    public MemberNksoPayloadResponse(MemberNkso memberNkso) {
        this.registryNum = memberNkso.getRegistryNum();
        this.dateAddedToRegistry = memberNkso.getDateAddedToRegistry();
        this.textDateAddedToRegistry = memberNkso.getTextDateAddedToRegistry();
        this.fullTextDateAddedToRegistry = memberNkso.getFullTextDateAddedToRegistry();
        this.subjectRf = memberNkso.getSubjectRf();
        this.city = memberNkso.getCity();
        this.regionPartnership = memberNkso.getRegionPartnership();
        this.rpCode = memberNkso.getRpCode();
        this.lastname = memberNkso.getLastname();
        this.firstname = memberNkso.getFirstname();
        this.middlename = memberNkso.getMiddlename();
        this.dateOfBirth = memberNkso.getDateOfBirth();
        this.birthPlace = memberNkso.getBirthPlace();
        this.tin = memberNkso.getTin();
        this.ssn = memberNkso.getSsn();
        this.membershipInRp = memberNkso.getMembershipInRp();
        this.registerNumInRp = memberNkso.getRegisterNumInRp();
        this.dateJoiningRp = memberNkso.getDateJoiningRp();
        this.dateExclusionFromRp = memberNkso.getDateExclusionFromRp();
        this.totalWorkExperience = memberNkso.getTotalWorkExperience();
        this.evaluatedWorkExperience = memberNkso.getEvaluatedWorkExperience();
        this.assessmentWorkExperience = memberNkso.getAssessmentWorkExperience();
        this.passport = memberNkso.getPassport();
        this.registrationAddress = memberNkso.getRegistrationAddress();
        this.correspondenceAddress = memberNkso.getCorrespondenceAddress();
        this.memberEmailNkso = memberNkso.getMemberEmailNkso();
        this.memberEmailNkso2 = memberNkso.getMemberEmailNkso2();
        this.cityPhoneCode = memberNkso.getCityPhoneCode();
        this.contactPhone = memberNkso.getContactPhone();
        this.mobilePhone = memberNkso.getMobilePhone();
        this.textCertificateNumCriminalRecord = memberNkso.getTextCertificateNumCriminalRecord();
        this.certificateNumCriminalRecord = memberNkso.getCertificateNumCriminalRecord();
        this.textCertificateDateCriminalRecord = memberNkso.getTextCertificateDateCriminalRecord();
        this.certificateDateCriminalRecord = memberNkso.getCertificateDateCriminalRecord();
        this.tCertificateDateCriminalRecord = memberNkso.getTCertificateDateCriminalRecord();
        this.dateChangeInRegisterMembers = memberNkso.getDateChangeInRegisterMembers();
        this.dateChangeMembers = memberNkso.getDateChangeMembers();
        this.dateModificationMembers = memberNkso.getDateModificationMembers();
        this.needToChangeMembers = memberNkso.getNeedToChangeMembers();
        this.adequacyNksoMember = memberNkso.getAdequacyNksoMember();
        this.noteNksoMember = memberNkso.getNoteNksoMember();
    }
}
