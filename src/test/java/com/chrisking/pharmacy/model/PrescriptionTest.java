package com.chrisking.pharmacy.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PrescriptionTest {

    @Test
    void buildsAndDefaultsExpiryToOneYear() {
        Patient p = new Patient("p1", "Chris", 30, "709-555-0000");
        Doctor d = new Doctor("d1", "Dr. Lee", 50, "709-555-2222", "Family Medicine");
        Medication m = new Medication("m1", "Ibuprofen", "200mg", 30, LocalDate.now().plusDays(90));

        LocalDate issued = LocalDate.now();
        Prescription rx = new Prescription("rx1", d, p, m, issued);

        assertEquals("rx1", rx.getId());
        assertEquals(d, rx.getDoctor());
        assertEquals(p, rx.getPatient());
        assertEquals(m, rx.getMedication());
        assertEquals(issued, rx.getIssuedOn());
        assertEquals(issued.plusYears(1), rx.getExpiresOn());
        assertFalse(rx.isExpired());
    }

    @Test
    void explicitExpiryAndValidation() {
        Patient p = new Patient("p1", "Chris", 30, "709-555-0000");
        Doctor d = new Doctor("d1", "Dr. Lee", 50, "709-555-2222", "Family Medicine");
        Medication m = new Medication("m1", "Ibuprofen", "200mg", 30, LocalDate.now().plusDays(90));

        LocalDate issued = LocalDate.now().minusDays(2);
        LocalDate expiry = LocalDate.now().plusDays(10);
        Prescription rx = new Prescription("rx2", d, p, m, issued, expiry);

        assertEquals(expiry, rx.getExpiresOn());
        assertFalse(rx.isExpired());

        assertThrows(IllegalArgumentException.class, () ->
                rx.setExpiresOn(issued.minusDays(1)));
    }

    @Test
    void isExpiredDetectsPast() {
        Patient p = new Patient("p1", "Chris", 30, "709-555-0000");
        Doctor d = new Doctor("d1", "Dr. Lee", 50, "709-555-2222", "Family Medicine");
        Medication m = new Medication("m1", "Ibuprofen", "200mg", 30, LocalDate.now().plusDays(90));

        LocalDate issued = LocalDate.now().minusYears(2);
        LocalDate expired = LocalDate.now().minusDays(1);
        Prescription rx = new Prescription("rx3", d, p, m, issued, expired);

        assertTrue(rx.isExpired());
    }

    @Test
    void equalsAndHashCodeById() {
        Patient p = new Patient("p1", "Chris", 30, "709-555-0000");
        Doctor d = new Doctor("d1", "Dr. Lee", 50, "709-555-2222", "Family Medicine");
        Medication m = new Medication("m1", "Ibuprofen", "200mg", 30, LocalDate.now().plusDays(90));

        LocalDate issued = LocalDate.now();
        Prescription a = new Prescription("same", d, p, m, issued);
        Prescription b = new Prescription("same", d, p, m, issued.plusDays(1));

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }
}