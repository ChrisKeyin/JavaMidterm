package com.chrisking.pharmacy.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Doctor extends Person {

    private String specialization;
    private final List<Patient> patients = new ArrayList<>();


    public Doctor(String id, String name, int age, String phoneNumber, String specialization) {
        super(id, name, age, phoneNumber);
        this.specialization = Objects.requireNonNull(specialization, "specialization");
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = Objects.requireNonNull(specialization, "specialization");
    }

    public List<Patient> getPatients() {
        return Collections.unmodifiableList(patients);
    }

    public boolean addPatient(Patient p) {
        Objects.requireNonNull(p, "patient");
        if (!patients.contains(p)) {
            return patients.add(p);
        }
        return false;
    }

    public boolean removePatient(Patient p) {
        Objects.requireNonNull(p, "patient");
        return patients.remove(p);
    }

    @Override
    public String toString() {
        return super.toString() + " specialization=" + specialization + " patients=" + patients.size();
    }
}