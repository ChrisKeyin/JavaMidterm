package com.chrisking.pharmacy.model;

import org.junit.jupiter.api.Test;

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
        Medication m = new Medication("m1", "Ibuprofen");

        assertTrue(p.addMedication(m));
        assertFalse(p.addMedication(m)); // duplicate ignored
        assertEquals(1, p.getCurrentMedications().size());
        assertEquals("m1", p.getCurrentMedications().getFirst().getId());
    }

    @Test
    void removeMedicationWorks() {
        Patient p = new Patient("p1", "Chris", 30, "709-555-0000");
        Medication m = new Medication("m1", "Ibuprofen");
        p.addMedication(m);

        assertTrue(p.removeMedication(m));
        assertFalse(p.removeMedication(m)); // already removed
        assertTrue(p.getCurrentMedications().isEmpty());
    }

    @Test
    void addPrescriptionAddsOnceOnly() {
        Patient p = new Patient("p1", "Chris", 30, "709-555-0000");
        Prescription pr = new Prescription("rx1");

        assertTrue(p.addPrescription(pr));
        assertFalse(p.addPrescription(pr)); // duplicate ignored
        assertEquals(1, p.getPrescriptions().size());
        assertEquals("rx1", p.getPrescriptions().getFirst().getId());
    }

    @Test
    void removePrescriptionWorks() {
        Patient p = new Patient("p1", "Chris", 30, "709-555-0000");
        Prescription pr = new Prescription("rx1");
        p.addPrescription(pr);

        assertTrue(p.removePrescription(pr));
        assertFalse(p.removePrescription(pr));
        assertTrue(p.getPrescriptions().isEmpty());
    }

    @Test
    void listsAreUnmodifiableFromOutside() {
        Patient p = new Patient("p1", "Chris", 30, "709-555-0000");
        List<Medication> meds = p.getCurrentMedications();
        assertThrows(UnsupportedOperationException.class, () -> meds.add(new Medication("x", "Y")));
    }
}