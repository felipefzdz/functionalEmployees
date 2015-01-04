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
                .map(Intern::mentorId)
                .distinct()
                .count();
    }

    public List<Intern> getBy(Senior senior) {
        return interns.stream()
                .filter(intern -> intern.mentorId().equals(senior.id()))
                .collect(toList());
    }
}
