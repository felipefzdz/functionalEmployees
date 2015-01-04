package org.olid16.domain.entities;

import org.joda.time.Days;
import org.olid16.domain.values.*;

public class Senior {

    private final Sex sex;
    private final FullName fullName;
    private final DateOfBirth dateOfBirth;
    private final Age age;
    private final Id id;
    private final FirstName firstName;

    public Senior(SeniorBuilder builder) {
        this.sex = builder.sex;
        this.fullName = builder.fullName;
        this.dateOfBirth = builder.dateOfBirth;
        this.age = builder.age;
        this.id = builder.id;
        this.firstName = builder.firstName;
    }

    public Sex sex() {
        return sex;
    }

    public Age age() {
        return age;
    }

    public FullName fullName() {
        return fullName;
    }

    public DateOfBirth dateOfBirth() {
        return dateOfBirth;
    }

    public int howManyDaysOlderThan(Senior anotherSenior) {
        return Days.daysBetween(this.dateOfBirth().value(), anotherSenior.dateOfBirth().value()).getDays();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Senior senior = (Senior) o;

        return id.isPresent() && senior.id.isPresent() ?
                id.equals(senior.id):
                fullName.equals(senior.fullName);
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

    public Id id() {
        return id;
    }

    public FirstName firstName() {
        return firstName;
    }

    public static class SeniorBuilder {
        private Sex sex;
        private FullName fullName;
        private DateOfBirth dateOfBirth;
        private Age age;
        public Id id;
        public FirstName firstName;

        public SeniorBuilder withSex(Sex sex) {
            this.sex = sex;
            return this;
        }

        public SeniorBuilder withFullName(FullName fullName) {
            this.fullName = fullName;
            return this;
        }

        public SeniorBuilder withDateOfBirth(DateOfBirth dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        public SeniorBuilder withAge(Age age) {
            this.age = age;
            return this;
        }

        public SeniorBuilder withId(Id id) {
            this.id = id;
            return this;
        }

        public SeniorBuilder withFirstName(FirstName firstName) {
            this.firstName = firstName;
            return this;
        }

        public Senior create() {
            return new Senior(this);
        }
    }
}
