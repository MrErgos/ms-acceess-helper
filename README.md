# OAuth2-secured Data Management Portal for Microsoft Access.

[English] | [Русский](README.ru.md)

**ms-access-helper** is a corporate data management portal secured by OAuth2, designed to simplify operations with existing Microsoft Access databases. The project provides a REST API and a web interface, containerized with Docker and secured using the OAuth2/OpenID Connect protocol (Keycloak).

---

## Project Architecture

The project is based on a microservices architecture where each component is isolated within Docker:

* **Keycloak** — Identity and Access Management (IAM) server. Manages authentication, authorization, and roles.
* **member-nkso-api** — Backend service (Java 25, Spring Boot). Handles MS Access database operations.
* **manager-app** — Frontend application (Java 25, Thymeleaf). Interacts with the API on behalf of the user.



---

## Tech Stack

### **Infrastructure & Security**
* **Docker & Docker Compose** — Containerization and orchestration.
* **Keycloak 26+** — OAuth2 / OpenID Connect Provider.

### **Backend (member-nkso-api)**
* **Java 25** (Amazon Corretto)
* **Spring Boot 3.5.6** (Spring Security, Data JPA)
* **UCanAccess 5.1.3** — Driver for MS Access (.mdb/.accdb) read/write access.
* **Hibernate** — ORM for data mapping.

### **Frontend (manager-app)**
* **Spring Boot 3.5.6** (OAuth2 Client)
* **Thymeleaf** — Template engine.
* **Vanilla JS** — Field validation and UI dynamics.

---

## Quick Start (Docker)

This is the easiest way to launch the entire stack (API, Frontend, Keycloak) with a single command.

### **1. Data Preparation**
1. Place your Access database file at `./database/bd_nkso.accdb` (a sample test file is included in the test build).
2. Place the realm export file at `./keycloak/import/nkso-access-helper-realm.json` (a sample test file is included in the test build).

### **2. DNS Configuration**
For OAuth2 (Redirect URIs) to function correctly, add the following line to your `hosts` file (`C:\Windows\System32\drivers\etc\hosts` on Windows):
```text
127.0.0.1 keycloak
```
### **3. Launch**

```sh
docker-compose up -d --build
```

Once started, services will be available at:
* Frontend: http://localhost:8080
* * User main.editor: %=Fx7h%NZYnxeY) (Full permissions)
* * User viewer: Yabqt;p57uD&C6k (Read-only permissions)
* Backend API: http://localhost:8081
* Keycloak: http://localhost:8082 (Admin Console: admin/admin)

---

## API Examples

### Get all records

```http
GET /member-nkso-api/members
```

### Add a new record

```http
POST /member-nkso-api/members
Content-Type: application/json
```

```json
{
  "registryNum": "00001",
  "dateAddedToRegistry": "01.01.2020",
  "textDateAddedToRegistry": "01.01.2020",
  "fullTextDateAddedToRegistry": "\"01\" January 2020",
  "subjectRf": "Region RF",
  "city": "City N",
  "regionPartnership": "Regional Partnership of the RF subject",
  "rpCode": "RF-RP-00",
  "lastname": "LastName",
  "firstname": "FirstName",
  "middlename": "MiddleName",
  "dateOfBirth": "01.01.1990",
  "birthPlace": "City Name",
  "tin": "000000000000",
  "ssn": "000-000-000 00",
  "membershipInRp": "Yes",
  "registerNumInRp": "RP0000",
  "dateJoiningRp": "01.01.2015",
  "dateExclusionFromRp": "01.01.2025",
  "totalWorkExperience": "10 years",
  "evaluatedWorkExperience": "9 years",
  "assessmentWorkExperience": "01.01.2023",
  "passport": "Passport details here...",
  "registrationAddress": "Address details...",
  "correspondenceAddress": "Address details...",
  "memberEmailNkso": "example@nksi.ru",
  "memberEmailNkso2": "example@mail.com",
  "cityPhoneCode": "000",
  "contactPhone": "000-00-00",
  "mobilePhone": "+7 (900) 000-00-00",
  "textCertificateNumCriminalRecord": "№",
  "certificateNumCriminalRecord": "00000",
  "textCertificateDateCriminalRecord": "01.01.2024",
  "certificateDateCriminalRecord": "01.01.2024",
  "tCertificateDateCriminalRecord": "\"01\" January 2024",
  "dateChangeInRegisterMembers": "Change of residence — 01.03.2024",
  "dateChangeMembers": "Passport update — 02.03.2024",
  "dateModificationMembers": "Email modification — 03.03.2024",
  "needToChangeMembers": "Contact number update required",
  "adequacyNksoMember": "Valid, no violations",
  "noteNksoMember": "Active participant, no issues"
}
```

---

## Project Goals

* Provide a simple way to interact with Access databases in a corporate environment.
* Offer managers a user-friendly interface without requiring direct access to .mdb or .accdb files.