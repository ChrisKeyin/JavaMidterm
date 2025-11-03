package com.chrisking.pharmacy.util;

import com.chrisking.pharmacy.model.*;
import com.chrisking.pharmacy.service.MedicationTrackingSystem;

import java.util.Random;

public final class Samples {
    private Samples() {}

    public static void seed(MedicationTrackingSystem sys) {
        Doctor d1 = new Doctor(Ids.newId(), "Dr. Alice Gray", 46, "709-555-1001", "Cardiology");
        Doctor d2 = new Doctor(Ids.newId(), "Dr. Bob Lee",   51, "709-555-1002", "Family Medicine");
        sys.addDoctor(d1);
        sys.addDoctor(d2);

        Patient p1 = new Patient(Ids.newId(), "Chris King", 30, "709-555-2001");
        Patient p2 = new Patient(Ids.newId(), "Sam Taylor", 28, "709-555-2002");
        Patient p3 = new Patient(Ids.newId(), "Maya Park",  34, "709-555-2003");
        sys.addPatient(p1);
        sys.addPatient(p2);
        sys.addPatient(p3);

        Random rng = new Random();
        Medication m1 = new Medication(Ids.newId(), "Ibuprofen",   "200mg", 150,
                Medication.randomExpiryWithinDays(400, 400, rng));
        Medication m2 = new Medication(Ids.newId(), "Amoxicillin", "500mg",  80,
                Medication.randomExpiryWithinDays(400, 400, rng));
        Medication m3 = new Medication(Ids.newId(), "Metformin",   "850mg",  90,
                Medication.randomExpiryWithinDays(400, 400, rng));
        Medication m4 = new Medication(Ids.newId(), "Amlodipine",  "10mg",  120,
                Medication.randomExpiryWithinDays(400, 400, rng));
        sys.addMedication(m1);
        sys.addMedication(m2);
        sys.addMedication(m3);
        sys.addMedication(m4);

        sys.assignPatientToDoctor(p1.getId(), d2.getId());
        sys.acceptPrescription(Ids.newId(), d2.getId(), p1.getId(), m1.getId());
        sys.restockToMinimumLevel(100);
    }
}
