package com.chrisking.pharmacy.service;

import com.chrisking.pharmacy.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class MedicationTrackingSystemTest {

    private MedicationTrackingSystem sys;
    private Patient p1, p2;
    private Doctor d1, d2;
    private Medication m1, m2, mExpired;

    @BeforeEach
    void setup() {
        sys = new MedicationTrackingSystem();

        p1 = new Patient("P1", "Chris", 30, "709-555-0000");
        p2 = new Patient("P2", "Sam",   28, "709-555-1111");
        d1 = new Doctor("D1", "Dr. Lee", 50, "709-555-2222", "Family Medicine");
        d2 = new Doctor("D2", "Dr. Gray", 45, "709-555-3333", "Cardiology");

        m1 = new Medication("M1", "Ibuprofen", "200mg", 100, LocalDate.now().plusDays(90));
        m2 = new Medication("M2", "Amoxicillin", "500mg", 50, LocalDate.now().plusDays(30));
        mExpired = new Medication("M3", "OldMed", "10mg", 10, LocalDate.now().minusDays(1));

        sys.addPatient(p1);
        sys.addPatient(p2);
        sys.addDoctor(d1);
        sys.addDoctor(d2);
        sys.addMedication(m1);
        sys.addMedication(m2);
        sys.addMedication(mExpired);
    }

    @Test
    void searchByNameWorks() {
        List<Patient> ps = sys.searchPatientsByName("ch");
        assertEquals(1, ps.size());
        assertEquals("Chris", ps.getFirst().getName());

        List<Doctor> ds = sys.searchDoctorsByName("dr.");
        assertEquals(2, ds.size());

        List<Medication> ms = sys.searchMedicationsByName("amo");
        assertEquals(1, ms.size());
        assertEquals("Amoxicillin", ms.getFirst().getName());
    }

    @Test
    void assignPatientToDoctor() {
        assertTrue(sys.assignPatientToDoctor("P1", "D1"));
        assertTrue(d1.getPatients().contains(p1));

        assertFalse(sys.assignPatientToDoctor("P1", "D1"));
        assertEquals(1, d1.getPatients().size());

        assertFalse(sys.assignPatientToDoctor("BAD", "D1"));
        assertFalse(sys.assignPatientToDoctor("P1", "BAD"));
    }

    @Test
    void acceptPrescriptionLinksEntities() {
        var rxOpt = sys.acceptPrescription("RX1", "D1", "P1", "M2");
        assertTrue(rxOpt.isPresent());
        var rx = rxOpt.get();

        assertEquals(d1, rx.getDoctor());
        assertEquals(p1, rx.getPatient());
        assertEquals(m2, rx.getMedication());

        assertTrue(sys.getPrescriptions().contains(rx));
        assertTrue(p1.getPrescriptions().contains(rx));
        assertTrue(p1.getCurrentMedications().contains(m2));
        assertTrue(d1.getPatients().contains(p1));
    }

    @Test
    void editUpdateAndDeleteEntities() {
        assertTrue(sys.updatePatient("P2", "Samuel", 29, "709-555-2220"));
        assertEquals("Samuel", sys.findPatientById("P2").get().getName());
        assertEquals(29, sys.findPatientById("P2").get().getAge());

        assertTrue(sys.updateDoctor("D2", "Dr. Alice Gray", 46, null, "Internal Medicine"));
        assertEquals("Dr. Alice Gray", sys.findDoctorById("D2").get().getName());
        assertEquals("Internal Medicine", sys.findDoctorById("D2").get().getSpecialization());

        assertTrue(sys.updateMedication("M1", "Ibu", "400mg", 120, LocalDate.now().plusDays(120)));
        assertEquals("Ibu", sys.findMedicationById("M1").get().getName());
        assertEquals(120, sys.findMedicationById("M1").get().getQuantityInStock());

        assertTrue(sys.deletePatientById("P2"));
        assertFalse(sys.findPatientById("P2").isPresent());
        assertTrue(sys.deleteDoctorById("D2"));
        assertFalse(sys.findDoctorById("D2").isPresent());
        assertTrue(sys.deleteMedicationById("M3"));
        assertFalse(sys.findMedicationById("M3").isPresent());
    }

    @Test
    void reportsExpiredAndByDoctorAndPastYearNames() {
        var expired = sys.expiredMedications();
        assertEquals(1, expired.size());
        assertEquals("OldMed", expired.getFirst().getName());

        sys.acceptPrescription("RX1", "D1", "P1", "M1");
        sys.acceptPrescription("RX2", "D1", "P1", "M2");

        var byDoc = sys.prescriptionsByDoctor("D1");
        assertEquals(2, byDoc.size());

        Map<Patient, List<String>> names = sys.patientPrescriptionNamesPastYear();
        assertTrue(names.containsKey(p1));
        assertTrue(names.get(p1).containsAll(List.of("Amoxicillin", "Ibuprofen")));
    }

    @Test
    void restockOperations() {
        sys.updateMedication("M1", null, null, 5, null);
        sys.updateMedication("M2", null, null, 3, null);

        sys.restockToMinimumLevel(10);
        assertTrue(sys.findMedicationById("M1").get().getQuantityInStock() >= 10);
        assertTrue(sys.findMedicationById("M2").get().getQuantityInStock() >= 10);

        int before = sys.findMedicationById("M1").get().getQuantityInStock();
        sys.restockRandom(1, 3, new Random(7));
        int after = sys.findMedicationById("M1").get().getQuantityInStock();
        assertTrue(after >= before + 1 && after <= before + 3);
    }

    @Test
    void dumpAllProducesReadableOutput() {
        String dump = sys.dumpAll();
        assertTrue(dump.contains("=== Doctors ==="));
        assertTrue(dump.contains("=== Patients ==="));
        assertTrue(dump.contains("=== Medications ==="));
    }
}
