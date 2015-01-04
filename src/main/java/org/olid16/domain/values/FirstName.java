package org.olid16.domain.values;

public class FirstName {
    private final String value;

    public FirstName(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FirstName firstName = (FirstName) o;

        if (value != null ? !value.equals(firstName.value) : firstName.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }
}
