# Корпоративный портал управления данными с OAuth2 авторизацией

[English](README.md) | [Русский]

**ms-access-helper** — это корпоративный портал управления данными с поддержкой OAuth2, предназначенный для упрощения работы с существующими базами данных Microsoft Access. Проект предоставляет REST API и веб-интерфейс, упакованные в Docker-контейнеры и защищенные протоколом OAuth2/OpenID Connect (Keycloak).

---

## Архитектура проекта

Проект базируется на микросервисной архитектуре, где каждый компонент изолирован в Docker:

* **Keycloak** — сервер авторизации (IAM). Управляет доступом и ролями.
* **member-nkso-api** — Backend-сервис (Java 25, Spring Boot). Работает с БД Access.
* **manager-app** — Frontend-приложение (Java 25, Thymeleaf). Взаимодействует с API от имени пользователя.



---

## Стек технологий

### **Infrastructure & Security**
* **Docker & Docker Compose** — контейнеризация и оркестрация.
* **Keycloak 26+** — OAuth2 / OpenID Connect Provider.

### **Backend (member-nkso-api)**
* **Java 25** (Amazon Corretto)
* **Spring Boot 3.5.6** (Spring Security, Data JPA)
* **UCanAccess** — драйвер для доступа к MS Access (.mdb/.accdb).
* **Hibernate** — ORM для маппинга данных.

### **Frontend (manager-app)**
* **Spring Boot 3.5.6** (OAuth2 Client)
* **Thymeleaf** — шаблонизатор.
* **Vanilla JS** — валидация форм и динамика.

---

## Быстрый запуск (Docker)

Это самый простой способ запустить весь стек (API, Frontend, Keycloak) одной командой.

### **1. Подготовка данных**
1. Поместите вашу базу данных Access в папку `./database/bd_nkso.accdb` в тестовой сборке присутствует тест файл.
2. Поместите файл экспорта рилма в `./keycloak/import/nkso-access-helper-realm.json` в тестовой сборке присутствует тест файл.

### **2. Настройка DNS**
Для корректной работы OAuth2 (Redirect URIs), добавьте в ваш файл `hosts` (`C:\Windows\System32\drivers\etc\hosts`):
```text
127.0.0.1 keycloak
```

### **3. Запуск**

```sh
docker-compose up -d --build
```

После запуска сервисы будут доступны по адресам:
* Frontend: http://localhost:8080 
* * main.editor	%=Fx7h%NZYnxeY) (полные права)
* * viewer		Yabqt;p57uD&C6k (только права просмотра)
* Backend API: http://localhost:8081
* Keycloak: http://localhost:8082 (логин/пароль: admin/admin)

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
