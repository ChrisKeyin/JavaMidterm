package com.chrisking.pharmacy.model;

public class Medication {
    private final String id;
    private final String name;

    public Medication(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() { return id; }
    public String getName() { return name; }

    @Override public String toString() { return "Medication{id='%s', name='%s'}".formatted(id, name); }
}