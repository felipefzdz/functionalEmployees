package org.olid16.domain.values;

public class FullName {
    private final String value;
    public FullName(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FullName fullName = (FullName) o;

        if (!value.equals(fullName.value)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    public String value() {
        return value;
    }
}
