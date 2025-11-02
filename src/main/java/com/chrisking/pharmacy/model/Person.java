package com.chrisking.pharmacy.model;

import java.util.Objects;

public abstract class Person {
    private final String id;        // unique, immutable after creation
    private String name;
    private int age;
    private String phoneNumber;

    protected Person(String id, String name, int age, String phoneNumber) {
        this.id = Objects.requireNonNull(id, "id");
        this.name = Objects.requireNonNull(name, "name");
        this.age = age;
        this.phoneNumber = Objects.requireNonNull(phoneNumber, "phoneNumber");
    }

    public String getId() { return id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = Objects.requireNonNull(name, "name"); }

    public int getAge() { return age; }

    public void setAge(int age) { this.age = age; }

    public String getPhoneNumber() { return phoneNumber; }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = Objects.requireNonNull(phoneNumber, "phoneNumber");
    }

    @Override
    public String toString() {
        return "%s{id='%s', name='%s', age=%d, phone='%s'}"
                .formatted(getClass().getSimpleName(), id, name, age, phoneNumber);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person that)) return false;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() { return id.hashCode(); }
}