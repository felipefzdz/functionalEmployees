package org.olid16.parser;

import com.google.common.base.Joiner;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.olid16.domain.collections.Seniors;
import org.olid16.domain.values.*;

import java.util.List;
import java.util.Optional;

import static org.olid16.parser.CsvHeaders.*;

public class CsvEntity {
    private final List<Entry> entries;

    public CsvEntity(List<Entry> entries) {
        this.entries = entries;
    }

    public boolean isIntern(){
        return entries.stream()
                .anyMatch(entry -> SENIOR_INTERN.equals(entry.key()) && INTERN.equals(entry.value()));
    }

    public boolean isSenior(){
        return !isIntern();
    }


    public Id mentorId(Seniors seniors) {
        try {
            int rawMentorId = Integer.valueOf(extractMandatoryContentFor(MENTOR));
            Id mentorId = new Id(rawMentorId);
            if(seniors.existsBy(mentorId)){
                return mentorId;
            }
            throw new ParsingException();
        } catch (NumberFormatException e) {
            throw new ParsingException();
        }
    }

    public FirstName firstName() {
        String firstName = extractOptionalContentFor(FIRST_NAME).orElse("");
        return new FirstName(firstName);
    }

    public Id id() {
        Optional<String> id = extractOptionalContentFor(ID);
        return id.isPresent() ? parseId(id) : new Id();
    }

    private Id parseId(Optional<String> id) {
        try {
            return new Id(Integer.valueOf(id.get()));
        } catch (NumberFormatException e) {
            throw new ParsingException();
        }
    }

    public DateOfBirth dateOfBirth() {
        String rawDateOfBirth = extractMandatoryContentFor(D_O_B);
        try {
            DateTime dateOfBirth = DateTime.parse(rawDateOfBirth, DateTimeFormat.forPattern(DATE_OF_BIRTH_FORMAT));
            return new DateOfBirth(dateOfBirth);
        } catch (IllegalArgumentException e) {
            throw new ParsingException();
        }
    }

    public Age age() {
        try {
            String rawAge = extractMandatoryContentFor(AGE);
            return new Age(Integer.valueOf(rawAge));
        } catch (NumberFormatException e) {
            throw new ParsingException();
        }
    }

    public Sex sex() {
        String rawSex = extractMandatoryContentFor(SEX);
        return Sex.valueOf(rawSex.toUpperCase());
    }

    public FullName fullName() {
        Optional<String> fullName = extractOptionalContentFor(FULL_NAME);
        if(fullName.isPresent()){
            return new FullName(fullName.get());
        }
        String firstName = extractOptionalContentFor(FIRST_NAME).orElse(null);
        String surname = extractOptionalContentFor(SURNAME).orElse(null);
        return new FullName(Joiner.on(" ").skipNulls().join(firstName, surname));
    }

    private String extractMandatoryContentFor(String key) {
        return extractContentFor(key)
                .orElseThrow(ParsingException::new);
    }

    private Optional<String> extractOptionalContentFor(String key) {
        return extractContentFor(key);
    }

    private Optional<String> extractContentFor(String key) {
        return entries.stream()
                .filter(entry -> key.equals(entry.key()))
                .findFirst()
                .map(Entry::value);
    }

    public static class Entry {

        private final String key;
        private final String value;

        public Entry(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String key() {
            return key;
        }

        public String value() {
            return value;
        }
    }

}


