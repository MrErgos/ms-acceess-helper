package io.github.mrergos.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberNkso {
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
}
