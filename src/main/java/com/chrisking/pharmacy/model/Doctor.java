package com.chrisking.pharmacy.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Doctor extends Person {
    private String specialization;
    private final List<Patient> patients = new ArrayList<>();

    public Doctor(String id, String name, int age, String phoneNumber, String specialization) {
        super(id, name, age, phoneNumber);
        this.specialization = specialization;
    }

    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }

    public List<Patient> getPatients() { return Collections.unmodifiableList(patients); }

    public void addPatient(Patient p) { if (!patients.contains(p)) patients.add(p); }
    public void removePatient(Patient p) { patients.remove(p); }
}