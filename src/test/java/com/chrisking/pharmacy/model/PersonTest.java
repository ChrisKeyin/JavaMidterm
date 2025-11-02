package com.chrisking.pharmacy.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {

    private static class TestPerson extends Person {
        public TestPerson(String id, String name, int age, String phoneNumber) {
            super(id, name, age, phoneNumber);
        }
    }

    @Test
    void constructorAndGettersWork() {
        TestPerson p = new TestPerson("id-1", "Chris", 30, "709-555-0000");
        assertEquals("id-1", p.getId());
        assertEquals("Chris", p.getName());
        assertEquals(30, p.getAge());
        assertEquals("709-555-0000", p.getPhoneNumber());
    }

    @Test
    void settersUpdateFields() {
        TestPerson p = new TestPerson("id-2", "Alice", 40, "709-555-1111");
        p.setName("Alicia");
        p.setAge(41);
        p.setPhoneNumber("709-555-2222");
        assertEquals("Alicia", p.getName());
        assertEquals(41, p.getAge());
        assertEquals("709-555-2222", p.getPhoneNumber());
    }

    @Test
    void equalsAndHashCodeUseIdOnly() {
        TestPerson a = new TestPerson("same", "A", 20, "1");
        TestPerson b = new TestPerson("same", "B", 99, "9");
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void nullChecks() {
        assertThrows(NullPointerException.class, () -> new TestPerson(null, "N", 1, "P"));
        assertThrows(NullPointerException.class, () -> new TestPerson("id", null, 1, "P"));
        assertThrows(NullPointerException.class, () -> new TestPerson("id", "N", 1, null));
    }
}