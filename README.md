# ms-access-helper

**ms-access-helper** — это проект, созданный для облегчения работы с уже существующими базами данных Microsoft Access, предоставляя удобный REST API и простой веб-интерфейс для управления данными.

Проект состоит из двух модулей:

* **member-nkso-api** — backend-сервис (Java, Spring Boot)
* **manager-app** — легковесное web-приложение (Java + Thymeleaf + JS)

---

## Возможности проекта

* Подключение и работа с **MS Access (.mdb/.accdb)** через UCanAccess
* REST API для CRUD-операций
* Web-интерфейс для менеджеров с минималистичной валидацией форм (JS)
* Валидация данных на сервере (Spring Validation)
* Удобная структура модулей: backend API + frontend
* Devtools и actuator для отладки

---

## Архитектура проекта

```
ms-access-helper/
│
├── member-nkso-api/      # Backend-модуль (REST API)
│
└── manager-app/          # Веб-интерфейс (Thymeleaf)
```

---

## Стек технологий

### **Backend (member-nkso-api — Java + Spring Boot)**

* `spring-boot-starter-web`
* `spring-boot-starter-data-jpa`
* `ucanaccess` 5.1.3 — для чтения/записи MS Access
* `spring-boot-starter-validation`
* `lombok`
* Dev: `spring-boot-devtools`, `actuator`
* Test: `spring-boot-starter-test`

### **Frontend (manager-app — Java + Thymeleaf + JS)**

* `spring-boot-starter-web`
* `thymeleaf`
* `lombok`
* `spring-boot-devtools`
* `spring-boot-starter-actuator`
* Vanilla JS для валидации полей

---

## Запуск проекта

### **1. Клонирование репозитория**

```sh
git clone https://github.com/yourname/ms-access-helper.git
cd ms-access-helper
```

### **2. Сборка и запуск (оба модуля)**

```sh
./mvnw clean install
```

### **Запуск backend**

```sh
cd member-nkso-api
./mvnw spring-boot:run
```

Backend стартует по адресу:

```
http://localhost:8081
```

### **Запуск frontend (manager-app)**

```sh
cd manager-app
./mvnw spring-boot:run
```

Frontend будет доступен по адресу:

```
http://localhost:8080
```

---

## Примеры API

### Получить список данных

```http
GET /member-nkso-api/members
```

### Добавить запись

```http
POST /member-nkso-api/members
Content-Type: application/json
```

```json
{
  "registryNum": "00001",
  "dateAddedToRegistry": "01.01.2020",
  "textDateAddedToRegistry": "01.01.2020",
  "fullTextDateAddedToRegistry": "\"01\" января 2020 года",
  "subjectRf": "Регион РФ",
  "city": "Город N",
  "regionPartnership": "Региональное партнёрство субъекта РФ",
  "rpCode": "RF-RP-00",
  "lastname": "Фамилия",
  "firstname": "Имя",
  "middlename": "Отчество",
  "dateOfBirth": "01.01.1990",
  "birthPlace": "г. Город",
  "tin": "000000000000",
  "ssn": "000-000-000 00",
  "membershipInRp": "Да",
  "registerNumInRp": "RP0000",
  "dateJoiningRp": "01.01.2015",
  "dateExclusionFromRp": "01.01.2025",
  "totalWorkExperience": "10 лет",
  "evaluatedWorkExperience": "9 лет",
  "assessmentWorkExperience": "01.01.2023",
  "passport": "Паспорт 00 00 №000000 выдан органом регистрации, код подразделения 000-000",
  "registrationAddress": "г. Город, ул. Улица, д. 1, кв. 1",
  "correspondenceAddress": "г. Город, ул. Улица, д. 1, кв. 1",
  "memberEmailNkso": "example@nksi.ru",
  "memberEmailNkso2": "example@mail.com",
  "cityPhoneCode": "000",
  "contactPhone": "000-00-00",
  "mobilePhone": "+7 (900) 000-00-00",
  "textCertificateNumCriminalRecord": "№",
  "certificateNumCriminalRecord": "00000",
  "textCertificateDateCriminalRecord": "01.01.2024",
  "certificateDateCriminalRecord": "01.01.2024",
  "tCertificateDateCriminalRecord": "\"01\" января 2024 года",
  "dateChangeInRegisterMembers": "Изменение сведений о месте жительства — 01.03.2024",
  "dateChangeMembers": "Внесено изменение по паспорту — 02.03.2024",
  "dateModificationMembers": "Модификация данных электронной почты — 03.03.2024",
  "needToChangeMembers": "Требуется обновить контактный номер телефона",
  "adequacyNksoMember": "Корректен, взаимодействует без нарушений",
  "noteNksoMember": "Участник проявляет активность, замечаний нет"
}
```

---

## Цели проекта

* Обеспечить простой способ взаимодействия с базами Access в корпоративной среде
* Дать менеджерам удобный интерфейс без необходимости доступа к файлам .mdb
