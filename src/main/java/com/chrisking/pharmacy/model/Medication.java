package com.chrisking.pharmacy.model;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Random;

public class Medication {

    private final String id;
    private String name;
    private String dose;
    private int quantityInStock;
    private LocalDate expiryDate;

    public Medication(String id, String name, String dose, int quantityInStock, LocalDate expiryDate) {
        this.id = Objects.requireNonNull(id, "id");
        this.name = Objects.requireNonNull(name, "name");
        this.dose = Objects.requireNonNull(dose, "dose");
        this.quantityInStock = quantityInStock;
        this.expiryDate = Objects.requireNonNull(expiryDate, "expiryDate");
    }

    public static LocalDate randomExpiryWithinDays(int pastDays, int futureDays, Random rng) {
        if (pastDays < 0 || futureDays < 0) throw new IllegalArgumentException("pastDays/futureDays must be >= 0");
        int span = pastDays + futureDays + 1; // inclusive of both ends
        int offset = rng.nextInt(span) - pastDays; // shift into [-pastDays..futureDays]
        return LocalDate.now().plusDays(offset);
    }

    public String getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = Objects.requireNonNull(name, "name"); }

    public String getDose() { return dose; }
    public void setDose(String dose) { this.dose = Objects.requireNonNull(dose, "dose"); }

    public int getQuantityInStock() { return quantityInStock; }
    public void setQuantityInStock(int quantityInStock) { this.quantityInStock = quantityInStock; }

    public LocalDate getExpiryDate() { return expiryDate; }
    public void setExpiryDate(LocalDate expiryDate) { this.expiryDate = Objects.requireNonNull(expiryDate, "expiryDate"); }

    public boolean isExpired() {
        return expiryDate.isBefore(LocalDate.now());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Medication that)) return false;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() { return id.hashCode(); }

    @Override
    public String toString() {
        return "Medication{id='%s', name='%s', dose='%s', qty=%d, expiry=%s}"
                .formatted(id, name, dose, quantityInStock, expiryDate);
    }
}