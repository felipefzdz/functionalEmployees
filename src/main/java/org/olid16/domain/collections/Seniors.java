package org.olid16.domain.collections;

import org.olid16.domain.entities.Senior;
import org.olid16.domain.values.*;

import java.util.List;

public class Seniors {

    private List<Senior> seniors;

    public Seniors(List<Senior> seniors) {
        this.seniors = seniors;
    }

    public int count() {
        return seniors.size();
    }

    public long countBy(Sex sex) {
        return seniors.stream()
                .filter(person -> sex.equals(person.sex()))
                .count();
    }

    public double averageAge() {
        return seniors.stream()
                .mapToInt(person -> person.age().value())
                .average()
                .orElse(0);
    }

    public Senior getBy(FullName fullName) {
        return seniors.stream()
                .filter(person -> fullName.equals(person.fullName()))
                .findFirst()
                .get();
    }

    public boolean existsBy(Id ownerId) {
        return seniors.stream()
                .anyMatch(person -> ownerId.equals(person.id()));
    }

    public Senior getBy(FirstName firstName) {
        return seniors.stream()
                .filter(person -> firstName.equals(person.firstName()))
                .findFirst()
                .get();
    }
}
