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
                .filter(senior -> sex.equals(senior.sex()))
                .count();
    }

    public double averageAge() {
        return seniors.stream()
                .mapToInt(senior -> senior.age().value())
                .average()
                .orElse(0);
    }

    public Senior getBy(FullName fullName) {
        return seniors.stream()
                .filter(senior -> fullName.equals(senior.fullName()))
                .findFirst()
                .get();
    }

    public boolean existsBy(Id mentorId) {
        return seniors.stream()
                .anyMatch(senior -> mentorId.equals(senior.id()));
    }

    public Senior getBy(FirstName firstName) {
        return seniors.stream()
                .filter(senior -> firstName.equals(senior.firstName()))
                .findFirst()
                .get();
    }
}
