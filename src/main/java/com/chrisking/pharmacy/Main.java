package com.chrisking.pharmacy;

import com.chrisking.pharmacy.model.*;
import com.chrisking.pharmacy.service.MedicationTrackingSystem;
import com.chrisking.pharmacy.util.Ids;
import com.chrisking.pharmacy.util.Samples;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Main {

    private static final Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        MedicationTrackingSystem sys = new MedicationTrackingSystem();
        Samples.seed(sys);
        runMenu(sys);
    }

    private static void runMenu(MedicationTrackingSystem sys) {
        while (true) {
            System.out.println("\n=== Pharmacy Management System ===");
            System.out.println(" 1) Add Patient");
            System.out.println(" 2) Edit Patient");
            System.out.println(" 3) Delete Patient");
            System.out.println(" 4) Add Doctor");
            System.out.println(" 5) Edit Doctor");
            System.out.println(" 6) Delete Doctor");
            System.out.println(" 7) Add Medication");
            System.out.println(" 8) Edit Medication");
            System.out.println(" 9) Delete Medication");
            System.out.println("10) Search by Name (patients/doctors/medications)");
            System.out.println("11) Assign Patient to Doctor");
            System.out.println("12) Accept Prescription");
            System.out.println("13) Report: Dump All");
            System.out.println("14) Report: Expired Medications");
            System.out.println("15) Report: Prescriptions by Doctor");
            System.out.println("16) Report: Patient Rx Names (Past Year)");
            System.out.println("17) Restock: Set Minimum Level");
            System.out.println("18) Restock: Random Add");
            System.out.println(" 0) Exit");
            System.out.print("Choose: ");
            String choice = in.nextLine().trim();

            try {
                switch (choice) {
                    case "1" -> addPatient(sys);
                    case "2" -> editPatient(sys);
                    case "3" -> deletePatient(sys);
                    case "4" -> addDoctor(sys);
                    case "5" -> editDoctor(sys);
                    case "6" -> deleteDoctor(sys);
                    case "7" -> addMedication(sys);
                    case "8" -> editMedication(sys);
                    case "9" -> deleteMedication(sys);
                    case "10" -> searchByName(sys);
                    case "11" -> assignPatientToDoctor(sys);
                    case "12" -> acceptPrescription(sys);
                    case "13" -> System.out.println(sys.dumpAll());
                    case "14" -> reportExpired(sys);
                    case "15" -> reportPrescriptionsByDoctor(sys);
                    case "16" -> reportPatientRxNamesPastYear(sys);
                    case "17" -> restockMin(sys);
                    case "18" -> restockRandom(sys);
                    case "0" -> { System.out.println("Goodbye!"); return; }
                    default -> System.out.println("Invalid option. Try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static String readNonEmpty(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = in.nextLine().trim();
            if (!s.isEmpty()) return s;
            System.out.println("Please enter a value.");
        }
    }

    private static Integer readIntOrNull(String prompt) {
        System.out.print(prompt);
        String s = in.nextLine().trim();
        if (s.isEmpty()) return null;
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            System.out.println("Not a number. Skipping.");
            return null;
        }
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = in.nextLine().trim();
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static LocalDate readDate(String prompt) {
        while (true) {
            System.out.print(prompt + " (YYYY-MM-DD): ");
            String s = in.nextLine().trim();
            try {
                return LocalDate.parse(s);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date. Try again.");
            }
        }
    }

    private static LocalDate readDateOrNull(String prompt) {
        System.out.print(prompt + " (YYYY-MM-DD, blank to skip): ");
        String s = in.nextLine().trim();
        if (s.isEmpty()) return null;
        try {
            return LocalDate.parse(s);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date; skipping.");
            return null;
        }
    }

    private static void printListWithIds(String title, List<?> list) {
        System.out.println("\n" + title + " (" + list.size() + "):");
        for (Object o : list) System.out.println("  " + o);
    }

    private static void addPatient(MedicationTrackingSystem sys) {
        String name = readNonEmpty("Name: ");
        int age = readInt("Age: ");
        String phone = readNonEmpty("Phone: ");
        Patient p = new Patient(Ids.newId(), name, age, phone);
        sys.addPatient(p);
        System.out.println("Added patient: " + p.getId());
    }

    private static void editPatient(MedicationTrackingSystem sys) {
        printListWithIds("Patients", sys.getPatients());
        String id = readNonEmpty("Patient ID to edit: ");
        String newName = readNonEmpty("New name (leave as current if desired): ");
        Integer newAge = readIntOrNull("New age (blank to skip): ");
        String newPhone = readNonEmpty("New phone (leave as current if desired): ");
        boolean ok = sys.updatePatient(id, newName, newAge, newPhone);
        System.out.println(ok ? "Patient updated." : "Patient not found.");
    }

    private static void deletePatient(MedicationTrackingSystem sys) {
        printListWithIds("Patients", sys.getPatients());
        String id = readNonEmpty("Patient ID to delete: ");
        boolean ok = sys.deletePatientById(id);
        System.out.println(ok ? "Patient deleted." : "Patient not found.");
    }

    private static void addDoctor(MedicationTrackingSystem sys) {
        String name = readNonEmpty("Name: ");
        int age = readInt("Age: ");
        String phone = readNonEmpty("Phone: ");
        String spec = readNonEmpty("Specialization: ");
        Doctor d = new Doctor(Ids.newId(), name, age, phone, spec);
        sys.addDoctor(d);
        System.out.println("Added doctor: " + d.getId());
    }

    private static void editDoctor(MedicationTrackingSystem sys) {
        printListWithIds("Doctors", sys.getDoctors());
        String id = readNonEmpty("Doctor ID to edit: ");
        String newName = readNonEmpty("New name (leave as current if desired): ");
        Integer newAge = readIntOrNull("New age (blank to skip): ");
        String newPhone = readNonEmpty("New phone (leave as current if desired): ");
        String newSpec = readNonEmpty("New specialization (leave as current if desired): ");
        boolean ok = sys.updateDoctor(id, newName, newAge, newPhone, newSpec);
        System.out.println(ok ? "Doctor updated." : "Doctor not found.");
    }

    private static void deleteDoctor(MedicationTrackingSystem sys) {
        printListWithIds("Doctors", sys.getDoctors());
        String id = readNonEmpty("Doctor ID to delete: ");
        boolean ok = sys.deleteDoctorById(id);
        System.out.println(ok ? "Doctor deleted." : "Doctor not found.");
    }

    private static void addMedication(MedicationTrackingSystem sys) {
        String name = readNonEmpty("Name: ");
        String dose = readNonEmpty("Dose (e.g., 500mg): ");
        int qty = readInt("Quantity: ");
        LocalDate exp = readDate("Expiry");
        Medication m = new Medication(Ids.newId(), name, dose, qty, exp);
        sys.addMedication(m);
        System.out.println("Added medication: " + m.getId());
    }

    private static void editMedication(MedicationTrackingSystem sys) {
        printListWithIds("Medications", sys.getMedications());
        String id = readNonEmpty("Medication ID to edit: ");
        String newName = readNonEmpty("New name (leave as current if desired): ");
        String newDose = readNonEmpty("New dose (leave as current if desired): ");
        Integer newQty = readIntOrNull("New quantity (blank to skip): ");
        LocalDate newExp = readDateOrNull("New expiry");
        boolean ok = sys.updateMedication(id, newName, newDose, newQty, newExp);
        System.out.println(ok ? "Medication updated." : "Medication not found.");
    }

    private static void deleteMedication(MedicationTrackingSystem sys) {
        printListWithIds("Medications", sys.getMedications());
        String id = readNonEmpty("Medication ID to delete: ");
        boolean ok = sys.deleteMedicationById(id);
        System.out.println(ok ? "Medication deleted." : "Medication not found.");
    }

    private static void searchByName(MedicationTrackingSystem sys) {
        String term = readNonEmpty("Search term: ");
        var ps = sys.searchPatientsByName(term);
        var ds = sys.searchDoctorsByName(term);
        var ms = sys.searchMedicationsByName(term);
        printListWithIds("Patients (search)", ps);
        printListWithIds("Doctors (search)", ds);
        printListWithIds("Medications (search)", ms);
    }

    private static void assignPatientToDoctor(MedicationTrackingSystem sys) {
        printListWithIds("Patients", sys.getPatients());
        String pid = readNonEmpty("Patient ID: ");
        printListWithIds("Doctors", sys.getDoctors());
        String did = readNonEmpty("Doctor ID: ");
        boolean ok = sys.assignPatientToDoctor(pid, did);
        System.out.println(ok ? "Assigned." : "Check IDs; assignment failed.");
    }

    private static void acceptPrescription(MedicationTrackingSystem sys) {
        printListWithIds("Doctors", sys.getDoctors());
        String did = readNonEmpty("Doctor ID: ");
        printListWithIds("Patients", sys.getPatients());
        String pid = readNonEmpty("Patient ID: ");
        printListWithIds("Medications", sys.getMedications());
        String mid = readNonEmpty("Medication ID: ");
        String rxId = Ids.newId();
        var pr = sys.acceptPrescription(rxId, did, pid, mid);
        System.out.println(pr.isPresent() ? "Accepted: " + pr.get() : "Failed (check IDs).");
    }

    private static void reportExpired(MedicationTrackingSystem sys) {
        var expired = sys.expiredMedications();
        if (expired.isEmpty()) System.out.println("No expired medications.");
        else printListWithIds("Expired Medications", expired);
    }

    private static void reportPrescriptionsByDoctor(MedicationTrackingSystem sys) {
        printListWithIds("Doctors", sys.getDoctors());
        String did = readNonEmpty("Doctor ID: ");
        var list = sys.prescriptionsByDoctor(did);
        if (list.isEmpty()) System.out.println("No prescriptions for that doctor.");
        else printListWithIds("Prescriptions for Doctor", list);
    }

    private static void reportPatientRxNamesPastYear(MedicationTrackingSystem sys) {
        Map<Patient, List<String>> map = sys.patientPrescriptionNamesPastYear();
        System.out.println("\nPatient Rx Names (Past Year):");
        map.forEach((p, names) -> System.out.println("  " + p.getName() + ": " + names));
    }

    private static void restockMin(MedicationTrackingSystem sys) {
        int level = readInt("Set minimum level to: ");
        sys.restockToMinimumLevel(level);
        System.out.println("Restocked to minimum level.");
    }

    private static void restockRandom(MedicationTrackingSystem sys) {
        int min = readInt("Random add min: ");
        int max = readInt("Random add max: ");
        sys.restockRandom(min, max, new Random());
        System.out.println("Random restock applied.");
    }
}
