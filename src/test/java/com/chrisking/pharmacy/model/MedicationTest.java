package com.chrisking.pharmacy.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class MedicationTest {

    @Test
    void constructorAndGettersWork() {
        LocalDate exp = LocalDate.now().plusDays(10);
        Medication m = new Medication("m1", "Ibuprofen", "200mg", 100, exp);

        assertEquals("m1", m.getId());
        assertEquals("Ibuprofen", m.getName());
        assertEquals("200mg", m.getDose());
        assertEquals(100, m.getQuantityInStock());
        assertEquals(exp, m.getExpiryDate());
    }

    @Test
    void settersUpdateFields() {
        Medication m = new Medication("m2", "X", "10mg", 1, LocalDate.now());
        m.setName("Amoxicillin");
        m.setDose("500mg");
        m.setQuantityInStock(250);
        LocalDate newExp = LocalDate.now().plusDays(90);
        m.setExpiryDate(newExp);

        assertEquals("Amoxicillin", m.getName());
        assertEquals("500mg", m.getDose());
        assertEquals(250, m.getQuantityInStock());
        assertEquals(newExp, m.getExpiryDate());
    }

    @Test
    void isExpiredDetectsPast() {
        Medication past = new Medication("m3", "Z", "1mg", 1, LocalDate.now().minusDays(1));
        Medication today = new Medication("m4", "Z", "1mg", 1, LocalDate.now());            // not before today
        Medication future = new Medication("m5", "Z", "1mg", 1, LocalDate.now().plusDays(1));
        assertTrue(past.isExpired());
        assertFalse(today.isExpired());
        assertFalse(future.isExpired());
    }

    @Test
    void randomExpiryWithinDaysCoversPastAndFuture() {
        Random rng = new Random(42);
        LocalDate date = Medication.randomExpiryWithinDays(400, 400, rng);
        // Check within range [-400..+400]
        long diff = java.time.temporal.ChronoUnit.DAYS.between(LocalDate.now(), date);
        assertTrue(diff >= -400 && diff <= 400);
    }

    @Test
    void equalsAndHashCodeById() {
        Medication a = new Medication("same", "A", "X", 1, LocalDate.now());
        Medication b = new Medication("same", "B", "Y", 2, LocalDate.now().plusDays(7));
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }
}