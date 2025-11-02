package com.chrisking.pharmacy.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DoctorTest {

    @Test
    void constructorAndGettersWork() {
        Doctor d = new Doctor("d1", "Dr. Lee", 50, "709-555-2222", "Family Medicine");
        assertEquals("d1", d.getId());
        assertEquals("Dr. Lee", d.getName());
        assertEquals(50, d.getAge());
        assertEquals("709-555-2222", d.getPhoneNumber());
        assertEquals("Family Medicine", d.getSpecialization());
        assertTrue(d.getPatients().isEmpty());
    }

    @Test
    void setSpecializationUpdates() {
        Doctor d = new Doctor("d1", "Dr. Lee", 50, "709-555-2222", "Family Medicine");
        d.setSpecialization("Cardiology");
        assertEquals("Cardiology", d.getSpecialization());
    }

    @Test
    void addPatientIdempotentAndRemoveWorks() {
        Doctor d = new Doctor("d1", "Dr. Lee", 50, "709-555-2222", "Family Medicine");
        Patient p1 = new Patient("p1", "Chris", 30, "709-555-0000");
        Patient p2 = new Patient("p2", "Sam", 28, "709-555-1111");

        assertTrue(d.addPatient(p1));
        assertTrue(d.addPatient(p2));
        assertFalse(d.addPatient(p1)); // duplicate ignored
        assertEquals(2, d.getPatients().size());

        assertTrue(d.removePatient(p1));
        assertFalse(d.removePatient(p1)); // already removed
        assertEquals(1, d.getPatients().size());
    }

    @Test
    void patientsListIsUnmodifiable() {
        Doctor d = new Doctor("d1", "Dr. Lee", 50, "709-555-2222", "Family Medicine");
        assertThrows(UnsupportedOperationException.class, () ->
                d.getPatients().add(new Patient("pX", "X", 1, "000")));
    }
}