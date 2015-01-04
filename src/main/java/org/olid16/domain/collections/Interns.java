package org.olid16.domain.collections;

import org.olid16.domain.entities.Senior;
import org.olid16.domain.entities.Intern;

import java.util.List;

import static java.util.stream.Collectors.*;

public class Interns {
    private final List<Intern> interns;

    public Interns(List<Intern> interns) {
        this.interns = interns;
    }

    public int count() {
        return interns.size();
    }

    public long countDistinctMentors() {
        return interns.stream()
                .map(Intern::ownerId)
                .distinct()
                .count();
    }

    public List<Intern> getBy(Senior senior) {
        return interns.stream()
                .filter(pet -> pet.ownerId().equals(senior.id()))
                .collect(toList());
    }
}
