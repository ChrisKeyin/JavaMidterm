package com.chrisking.pharmacy.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Patient extends Person {

    private final List<Medication> currentMedications = new ArrayList<>();
    private final List<Prescription> prescriptions = new ArrayList<>();

    public Patient(String id, String name, int age, String phoneNumber) {
        super(id, name, age, phoneNumber);
    }

    public List<Medication> getCurrentMedications() {
        return Collections.unmodifiableList(currentMedications);
    }

    public List<Prescription> getPrescriptions() {
        return Collections.unmodifiableList(prescriptions);
    }

    public boolean addMedication(Medication med) {
        Objects.requireNonNull(med, "medication");
        if (!currentMedications.contains(med)) {
            return currentMedications.add(med);
        }
        return false;
    }

    public boolean removeMedication(Medication med) {
        Objects.requireNonNull(med, "medication");
        return currentMedications.remove(med);
    }

    public boolean addPrescription(Prescription p) {
        Objects.requireNonNull(p, "prescription");
        if (!prescriptions.contains(p)) {
            return prescriptions.add(p);
        }
        return false;
    }

    public boolean removePrescription(Prescription p) {
        Objects.requireNonNull(p, "prescription");
        return prescriptions.remove(p);
    }

    @Override
    public String toString() {
        return super.toString()
                + " meds=" + currentMedications.size()
                + " prescriptions=" + prescriptions.size();
    }
}