package io.github.mrergos.entity;

import java.time.LocalDate;

public record MemberNkso(String registryNum, LocalDate dateAddedToRegistry, String textDateAddedToRegistry,
                         String fullTextDateAddedToRegistry, String subjectRf, String city, String regionPartnership,
                         String rpCode, String lastname, String firstname, String middlename, LocalDate dateOfBirth,
                         String birthPlace, String tin, String ssn, String membershipInRp, String registerNumInRp,
                         LocalDate dateJoiningRp, LocalDate dateExclusionFromRp, String totalWorkExperience,
                         String evaluatedWorkExperience, LocalDate assessmentWorkExperience, String passport,
                         String registrationAddress, String correspondenceAddress, String memberEmailNkso,
                         String memberEmailNkso2, String cityPhoneCode, String contactPhone, String mobilePhone,
                         String textCertificateNumCriminalRecord, String certificateNumCriminalRecord,
                         String textCertificateDateCriminalRecord, LocalDate certificateDateCriminalRecord,
                         String tCertificateDateCriminalRecord, String dateChangeInRegisterMembers,
                         String dateChangeMembers, String dateModificationMembers, String needToChangeMembers,
                         String adequacyNksoMember, String noteNksoMember) {
}
