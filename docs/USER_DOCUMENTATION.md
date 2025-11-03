# Pharmacy Management System — User Guide

## What is this app?
A console-based system that helps pharmacy staff manage patients, doctors, medications,** and prescriptions.  
You can add/edit/delete records, search by name, accept prescriptions (linking doctor → patient → medication), generate reports, and restock inventory.

## How do I run it?
1. Install Java 21 and IntelliJ IDEA (Community).
2. Open the project in IntelliJ.
3. Run: `Main` (green play button on `Main.main()`).

> Optional: seeded demo data loads automatically via `Samples.seed()` inside `Main`.  
> Comment it out if you want to start with an empty system.

## Menu Overview
- Add/Edit/Delete**: Patients, Doctors, Medications
- Search by Name**: Case-insensitive on patients, doctors, meds
- Assign Patient to Doctor**: Link a patient to a managing doctor
- Accept Prescription**: Create a prescription tying a doctor, patient, and medication
- Reports:
    - Dump All
    - Expired Medications
    - Prescriptions by Doctor
    - Patient Rx Names
- Restock:
    - Set Minimum Level (ensures all meds at least meet a target quantity)

## Quick Start
1. 10 – Search the seeded data
2. 11 – Assign a patient to a doctor
3. 12 – Accept a new prescription
4. 14 – View expired medications
5. 16 – See patient Rx names (past year)
6. 17 – Restock to a minimum level (e.g., 100)



```md
## Class Diagram (ASCII)
```text
                  ┌──────────────────┐
                  │    Person (abs)  │
                  │  id, name, age,  │
                  │  phoneNumber     │
                  └───────┬──────────┘
                          │
        ┌─────────────────┘
        │
┌─────────▼─────────┐             ┌─────────▼──────────┐
│      Patient       │             │       Doctor       │
│ + currentMeds[]    │             │ + specialization   │
│ + prescriptions[]  │             │ + patients[]       │
└────────────────────┘             └────────────────────┘

┌────────────────────┐
│     Medication     │
│ id, name, dose,    │
│ quantity, expiry   │
└────────────────────┘

┌───────────────────────────────────────────┐
│               Prescription                │
│ id, doctor, patient, medication,          │
│ issuedOn, expiresOn (+1y default)         │
└───────────────────────────────────────────┘

┌───────────────────────────────────────────┐
│       MedicationTrackingSystem            │
│ lists: patients, doctors, meds, rx        │
│ search, CRUD, assign, accept, reports     │
│ restock(min or random)                    │
└───────────────────────────────────────────┘
