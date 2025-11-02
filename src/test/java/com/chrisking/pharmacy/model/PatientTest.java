package com.chrisking.pharmacy.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PatientTest {

    @Test
    void startsWithEmptyLists() {
        Patient p = new Patient("p1", "Chris", 30, "709-555-0000");
        assertTrue(p.getCurrentMedications().isEmpty());
        assertTrue(p.getPrescriptions().isEmpty());
    }

    @Test
    void addMedicationAddsOnceOnly() {
        Patient p = new Patient("p1", "Chris", 30, "709-555-0000");
        Medication m = new Medication("m1", "Ibuprofen", "200mg", 100, LocalDate.now().plusDays(30));

        assertTrue(p.addMedication(m));
        assertFalse(p.addMedication(m));
        assertEquals(1, p.getCurrentMedications().size());
        assertEquals("m1", p.getCurrentMedications().getFirst().getId());
    }

    @Test
    void removeMedicationWorks() {
        Patient p = new Patient("p1", "Chris", 30, "709-555-0000");
        Medication m = new Medication("m1", "Ibuprofen", "200mg", 100, LocalDate.now().plusDays(30));
        p.addMedication(m);

        assertTrue(p.removeMedication(m));
        assertFalse(p.removeMedication(m));
        assertTrue(p.getCurrentMedications().isEmpty());
    }

    @Test
    void addPrescriptionAddsOnceOnly() {
        Patient p = new Patient("p1", "Chris", 30, "709-555-0000");
        Doctor d = new Doctor("d1", "Dr. Lee", 50, "709-555-2222", "Family Medicine");
        Medication m = new Medication("m1", "Ibuprofen", "200mg", 100, LocalDate.now().plusDays(30));
        Prescription pr = new Prescription("rx1", d, p, m, LocalDate.now());

        assertTrue(p.addPrescription(pr));
        assertFalse(p.addPrescription(pr));
        assertEquals(1, p.getPrescriptions().size());
        assertEquals("rx1", p.getPrescriptions().getFirst().getId());
    }

    @Test
    void removePrescriptionWorks() {
        Patient p = new Patient("p1", "Chris", 30, "709-555-0000");
        Doctor d = new Doctor("d1", "Dr. Lee", 50, "709-555-2222", "Family Medicine");
        Medication m = new Medication("m1", "Ibuprofen", "200mg", 100, LocalDate.now().plusDays(30));
        Prescription pr = new Prescription("rx1", d, p, m, LocalDate.now());
        p.addPrescription(pr);

        assertTrue(p.removePrescription(pr));
        assertFalse(p.removePrescription(pr));
        assertTrue(p.getPrescriptions().isEmpty());
    }

    @Test
    void listsAreUnmodifiableFromOutside() {
        Patient p = new Patient("p1", "Chris", 30, "709-555-0000");
        List<Medication> meds = p.getCurrentMedications();
        assertThrows(UnsupportedOperationException.class, () ->
                meds.add(new Medication("x", "Y", "5mg", 1, LocalDate.now())));
    }
}