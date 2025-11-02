package com.chrisking.pharmacy.model;

import java.time.LocalDate;
import java.util.Objects;

public class Prescription {

    private final String id;
    private final Doctor doctor;
    private final Patient patient;
    private final Medication medication;
    private final LocalDate issuedOn;
    private LocalDate expiresOn;


    public Prescription(String id, Doctor doctor, Patient patient, Medication medication, LocalDate issuedOn) {
        this.id = Objects.requireNonNull(id, "id");
        this.doctor = Objects.requireNonNull(doctor, "doctor");
        this.patient = Objects.requireNonNull(patient, "patient");
        this.medication = Objects.requireNonNull(medication, "medication");
        this.issuedOn = Objects.requireNonNull(issuedOn, "issuedOn");
        this.expiresOn = issuedOn.plusYears(1);
    }


    public Prescription(String id, Doctor doctor, Patient patient, Medication medication,
                        LocalDate issuedOn, LocalDate expiresOn) {
        this(id, doctor, patient, medication, issuedOn);
        setExpiresOn(expiresOn);
    }

    public String getId() { return id; }
    public Doctor getDoctor() { return doctor; }
    public Patient getPatient() { return patient; }
    public Medication getMedication() { return medication; }
    public LocalDate getIssuedOn() { return issuedOn; }
    public LocalDate getExpiresOn() { return expiresOn; }


    public void setExpiresOn(LocalDate expiresOn) {
        Objects.requireNonNull(expiresOn, "expiresOn");
        if (expiresOn.isBefore(issuedOn)) {
            throw new IllegalArgumentException("expiresOn cannot be before issuedOn");
        }
        this.expiresOn = expiresOn;
    }

    public boolean isExpired() {
        return expiresOn.isBefore(LocalDate.now());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Prescription that)) return false;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() { return id.hashCode(); }

    @Override
    public String toString() {
        return "Prescription{id='%s', doctor='%s', patient='%s', med='%s', issued=%s, expires=%s}"
                .formatted(id, doctor.getName(), patient.getName(), medication.getName(), issuedOn, expiresOn);
    }
}