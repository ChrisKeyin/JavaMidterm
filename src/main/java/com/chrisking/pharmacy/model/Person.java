package com.chrisking.pharmacy.model;

import java.util.Objects;

public abstract class Person {
    private final String id; // unique
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
    public void setName(String name) { this.name = Objects.requireNonNull(name); }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = Objects.requireNonNull(phoneNumber); }

    @Override public String toString() {
        return "%s{id='%s', name='%s', age=%d, phone='%s'}".formatted(getClass().getSimpleName(), id, name, age, phoneNumber);
    }
}