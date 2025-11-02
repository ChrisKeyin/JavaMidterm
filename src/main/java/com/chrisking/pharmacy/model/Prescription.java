package com.chrisking.pharmacy.model;

public class Prescription {
    private final String id;

    public Prescription(String id) {
        this.id = id;
    }

    public String getId() { return id; }

    @Override public String toString() { return "Prescription{id='%s'}".formatted(id); }
}