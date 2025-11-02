package com.chrisking.pharmacy.model;

import java.time.LocalDate;
import java.util.Objects;

public class Medication {
    private final String id;
    private String name;
    private String dose;
    private int quantityInStock;
    private LocalDate expiryDate;

    public Medication(String id, String name, String dose, int quantityInStock, LocalDate expiryDate) {
        this.id = Objects.requireNonNull(id);
        this.name = Objects.requireNonNull(name);
        this.dose = Objects.requireNonNull(dose);
        this.quantityInStock = quantityInStock;
        this.expiryDate = Objects.requireNonNull(expiryDate);
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = Objects.requireNonNull(name); }
    public String getDose() { return dose; }
    public void setDose(String dose) { this.dose = Objects.requireNonNull(dose); }
    public int getQuantityInStock() { return quantityInStock; }
    public void setQuantityInStock(int quantityInStock) { this.quantityInStock = quantityInStock; }
    public LocalDate getExpiryDate() { return expiryDate; }
    public void setExpiryDate(LocalDate expiryDate) { this.expiryDate = Objects.requireNonNull(expiryDate); }

    public boolean isExpired() { return expiryDate.isBefore(LocalDate.now()); }

    @Override public String toString() {
        return "Medication{id='%s', name='%s', dose='%s', qty=%d, expiry=%s}".formatted(id, name, dose, quantityInStock, expiryDate);
    }
}