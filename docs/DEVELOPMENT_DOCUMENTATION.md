# Pharmacy Management System — Development Guide

## Project Structure
```text
pharmacy-management/
├─ pom.xml
├─ README.md
├─ docs/
│  ├─ USER_DOCUMENTATION.md
│  └─ DEVELOPMENT_DOCUMENTATION.md
├─ src/
│  ├─ main/java/com/chrisking/pharmacy/
│  │  ├─ Main.java
│  │  ├─ model/
│  │  │  ├─ Person.java
│  │  │  ├─ Patient.java
│  │  │  ├─ Doctor.java
│  │  │  ├─ Medication.java
│  │  │  └─ Prescription.java
│  │  ├─ service/
│  │  │  └─ MedicationTrackingSystem.java
│  │  └─ util/
│  │     ├─ Ids.java
│  │     └─ Samples.java
│  └─ test/java/com/chrisking/pharmacy/
│     ├─ model/
│     │  ├─ PersonTest.java
│     │  ├─ PatientTest.java
│     │  ├─ DoctorTest.java
│     │  ├─ MedicationTest.java
│     │  └─ PrescriptionTest.java
│     └─ service/
│        └─ MedicationTrackingSystemTest.java


## Build & Run
- Build: `mvn package`
- Run tests: `mvn test`
- Run app: In IntelliJ, run `Main.main()` (green play button)

## Dependencies
- Java 21
- Maven
- JUnit 5 (test scope)
- (No database or external libs required)

## Coding Standards
- Packages under `com.chrisking.pharmacy.*`
- Entities are simple; business rules live in `MedicationTrackingSystem`
- Use `LocalDate` for dates; keep time zones out of scope
- Identity (`equals/hashCode`) by `id` for entity classes

## Javadocs
- Generate: `mvn javadoc:javadoc`
- Output (default): `target/site/apidocs/index.html`

## Theoretical Database Design (not required to implement)
**Tables**
- `doctor(id PK, name, age, phone, specialization)`
- `patient(id PK, name, age, phone)`
- `medication(id PK, name, dose, quantity_in_stock, expiry_date)`
- `prescription(id PK, doctor_id FK, patient_id FK, medication_id FK, issued_on, expires_on)`

**Relationships**
- `doctor (1) ─ (n) prescription`
- `patient (1) ─ (n) prescription`
- `medication (1) ─ (n) prescription`
