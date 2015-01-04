package org.olid16.domain.values;

public class Id {
    private int id;
    private boolean isPresent;

    public Id(int id) {
        this.id = id;
        this.isPresent = true;
    }

    public Id() {
    }

    public boolean isPresent(){
        return isPresent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Id id1 = (Id) o;

        if (id != id1.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
