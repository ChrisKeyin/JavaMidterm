package com.chrisking.pharmacy.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Patient extends Person {
    private final List<Medication> currentMedications = new ArrayList<>();
    private final List<Prescription> prescriptions = new ArrayList<>();

    public Patient(String id, String name, int age, String phoneNumber) {
        super(id, name, age, phoneNumber);
    }

    public List<Medication> getCurrentMedications() { return Collections.unmodifiableList(currentMedications); }
    public List<Prescription> getPrescriptions() { return Collections.unmodifiableList(prescriptions); }

    public void addMedication(Medication med) { if (!currentMedications.contains(med)) currentMedications.add(med); }
    public void removeMedication(Medication med) { currentMedications.remove(med); }

    public void addPrescription(Prescription p) { if (!prescriptions.contains(p)) prescriptions.add(p); }
    public void removePrescription(Prescription p) { prescriptions.remove(p); }
}