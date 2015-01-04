package org.olid16.domain.entities;

import org.olid16.domain.values.*;

public class Intern {

    private final Id id;
    private final FirstName firstName;
    private final Sex sex;
    private final Age age;
    private final DateOfBirth dateOfBirth;
    private final Id mentorId;

    public Intern(InternBuilder builder) {
        this.id = builder.id;
        this.firstName = builder.firstName;
        this.sex = builder.sex;
        this.age = builder.age;
        this.dateOfBirth = builder.dateOfBirth;
        this.mentorId = builder.mentorId;
    }

    public Id mentorId() {
        return mentorId;
    }

    public FirstName firstName() {
        return firstName;
    }

    public static class InternBuilder {
        private Id id;
        private FirstName firstName;
        private Sex sex;
        private Age age;
        private DateOfBirth dateOfBirth;
        private Id mentorId;

        public InternBuilder withId(Id id) {
            this.id = id;
            return this;
        }

        public InternBuilder withFirstName(FirstName firstName) {
            this.firstName = firstName;
            return this;
        }

        public InternBuilder withSex(Sex sex) {
            this.sex = sex;
            return this;
        }

        public InternBuilder withAge(Age age) {
            this.age = age;
            return this;
        }

        public InternBuilder withDateOfBirth(DateOfBirth dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        public InternBuilder withMentorId(Id mentorId) {
            this.mentorId = mentorId;
            return this;
        }

        public Intern create() {
            return new Intern(this);
        }
    }
}
