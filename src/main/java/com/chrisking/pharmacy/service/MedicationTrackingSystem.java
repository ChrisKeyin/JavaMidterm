package com.chrisking.pharmacy.service;

import com.chrisking.pharmacy.model.*;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MedicationTrackingSystem {

    private final List<Patient> patients = new ArrayList<>();
    private final List<Doctor> doctors = new ArrayList<>();
    private final List<Medication> medications = new ArrayList<>();
    private final List<Prescription> prescriptions = new ArrayList<>();

    public void addPatient(Patient p) { Objects.requireNonNull(p); patients.add(p); }
    public void addDoctor(Doctor d)   { Objects.requireNonNull(d); doctors.add(d); }
    public void addMedication(Medication m) { Objects.requireNonNull(m); medications.add(m); }

    public Optional<Patient> findPatientById(String id) {
        return patients.stream().filter(p -> p.getId().equals(id)).findFirst();
    }
    public Optional<Doctor> findDoctorById(String id) {
        return doctors.stream().filter(d -> d.getId().equals(id)).findFirst();
    }
    public Optional<Medication> findMedicationById(String id) {
        return medications.stream().filter(m -> m.getId().equals(id)).findFirst();
    }

    public List<Patient> searchPatientsByName(String term) {
        return searchByName(patients, term, Patient::getName);
    }
    public List<Doctor> searchDoctorsByName(String term) {
        return searchByName(doctors, term, Doctor::getName);
    }
    public List<Medication> searchMedicationsByName(String term) {
        return searchByName(medications, term, Medication::getName);
    }

    private <T> List<T> searchByName(List<T> list, String term, Function<T,String> nameGetter) {
        String q = Objects.requireNonNullElse(term, "").toLowerCase();
        return list.stream()
                .filter(x -> nameGetter.apply(x).toLowerCase().contains(q))
                .collect(Collectors.toList());
    }

    public boolean updatePatient(String id, String newName, Integer newAge, String newPhone) {
        var opt = findPatientById(id);
        if (opt.isEmpty()) return false;
        var p = opt.get();
        if (newName != null) p.setName(newName);
        if (newAge != null)  p.setAge(newAge);
        if (newPhone != null) p.setPhoneNumber(newPhone);
        return true;
    }

    public boolean updateDoctor(String id, String newName, Integer newAge, String newPhone, String newSpec) {
        var opt = findDoctorById(id);
        if (opt.isEmpty()) return false;
        var d = opt.get();
        if (newName != null) d.setName(newName);
        if (newAge != null)  d.setAge(newAge);
        if (newPhone != null) d.setPhoneNumber(newPhone);
        if (newSpec != null) d.setSpecialization(newSpec);
        return true;
    }

    public boolean updateMedication(String id, String newName, String newDose, Integer newQty, LocalDate newExpiry) {
        var opt = findMedicationById(id);
        if (opt.isEmpty()) return false;
        var m = opt.get();
        if (newName != null)  m.setName(newName);
        if (newDose != null)  m.setDose(newDose);
        if (newQty != null)   m.setQuantityInStock(newQty);
        if (newExpiry != null) m.setExpiryDate(newExpiry);
        return true;
    }

    public boolean deletePatientById(String id) {
        // Also detach from any doctor.patient lists (defensive cleanup)
        var opt = findPatientById(id);
        opt.ifPresent(p -> doctors.forEach(d -> d.removePatient(p)));
        return patients.removeIf(p -> p.getId().equals(id));
    }

    public boolean deleteDoctorById(String id) {
        return doctors.removeIf(d -> d.getId().equals(id));
    }

    public boolean deleteMedicationById(String id) {
        return medications.removeIf(m -> m.getId().equals(id));
    }

    public boolean assignPatientToDoctor(String patientId, String doctorId) {
        var p = findPatientById(patientId);
        var d = findDoctorById(doctorId);
        if (p.isEmpty() || d.isEmpty()) return false;
        return d.get().addPatient(p.get());
    }

    public Optional<Prescription> acceptPrescription(String prescriptionId, String doctorId, String patientId, String medicationId) {
        var d = findDoctorById(doctorId);
        var p = findPatientById(patientId);
        var m = findMedicationById(medicationId);
        if (d.isEmpty() || p.isEmpty() || m.isEmpty()) return Optional.empty();

        Prescription pr = new Prescription(prescriptionId, d.get(), p.get(), m.get(), LocalDate.now());
        prescriptions.add(pr);

        p.get().addPrescription(pr);
        p.get().addMedication(m.get());

        d.get().addPatient(p.get());

        return Optional.of(pr);
    }

    public List<Medication> expiredMedications() {
        return medications.stream().filter(Medication::isExpired).collect(Collectors.toList());
    }

    public List<Prescription> prescriptionsByDoctor(String doctorId) {
        return prescriptions.stream()
                .filter(pr -> pr.getDoctor().getId().equals(doctorId))
                .collect(Collectors.toList());
    }

    public Map<Patient, List<String>> patientPrescriptionNamesPastYear() {
        LocalDate aYearAgo = LocalDate.now().minusYears(1);
        Map<Patient, List<String>> map = new LinkedHashMap<>();
        for (Patient p : patients) {
            List<String> names = p.getPrescriptions().stream()
                    .filter(pr -> !pr.getIssuedOn().isBefore(aYearAgo))
                    .map(pr -> pr.getMedication().getName())
                    .distinct()
                    .sorted()
                    .collect(Collectors.toList());
            map.put(p, names);
        }
        return map;
    }

    public String dumpAll() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Doctors ===\n");
        doctors.forEach(d -> sb.append(d).append('\n'));
        sb.append("\n=== Patients ===\n");
        patients.forEach(p -> sb.append(p).append('\n'));
        sb.append("\n=== Medications ===\n");
        medications.forEach(m -> sb.append(m).append('\n'));
        sb.append("\n=== Prescriptions ===\n");
        prescriptions.forEach(pr -> sb.append(pr).append('\n'));
        return sb.toString();
    }

    public void restockToMinimumLevel(int minLevel) {
        for (Medication m : medications) {
            if (m.getQuantityInStock() < minLevel) {
                m.setQuantityInStock(minLevel);
            }
        }
    }

    public void restockRandom(int minAddInclusive, int maxAddInclusive, Random rng) {
        if (minAddInclusive < 0 || maxAddInclusive < minAddInclusive)
            throw new IllegalArgumentException("Invalid restock range");
        for (Medication m : medications) {
            int span = maxAddInclusive - minAddInclusive + 1;
            int delta = rng.nextInt(span) + minAddInclusive;
            m.setQuantityInStock(m.getQuantityInStock() + delta);
        }
    }

    public List<Patient> getPatients() { return Collections.unmodifiableList(patients); }
    public List<Doctor> getDoctors() { return Collections.unmodifiableList(doctors); }
    public List<Medication> getMedications() { return Collections.unmodifiableList(medications); }
    public List<Prescription> getPrescriptions() { return Collections.unmodifiableList(prescriptions); }
}
