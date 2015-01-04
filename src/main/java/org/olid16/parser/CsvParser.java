package org.olid16.parser;

import org.olid16.domain.collections.Seniors;
import org.olid16.domain.collections.Interns;
import org.olid16.domain.entities.Senior;
import org.olid16.domain.entities.Intern;
import org.olid16.utils.Strings;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.olid16.domain.entities.Senior.SeniorBuilder;
import static org.olid16.domain.entities.Intern.InternBuilder;

public class CsvParser {

    private final CsvEntitiesFactory csvEntitiesFactory;

    public CsvParser(CsvEntitiesFactory csvEntitiesFactory) {
        this.csvEntitiesFactory = csvEntitiesFactory;
    }

    public ParsingResult parse(String path) {
        try (BufferedReader reader = createReader(path)){
            List<String> header = parseHeader(reader);
            return parseContents(reader, header);
        } catch (IOException e) {
            throw new ParsingException(e);
        }
    }

    private BufferedReader createReader(String path) throws IOException {
        return Files.newBufferedReader(Paths.get(path));
    }

    /**
     * Parse header and consume the first line of the reader.
     */
    private List<String> parseHeader(BufferedReader reader) {
        return reader.lines()
                .findFirst()
                .map(Strings::splitByComma)
                .orElseThrow(ParsingException::new);
    }

    /**
     * Assumes that you've already consumed the header of the reader, calling parseHeader on this stream before
     */
    private ParsingResult parseContents(BufferedReader reader, List<String> header) {
        List<CsvEntity> csvEntities = csvEntitiesFactory.create(reader, header);
        Seniors seniors = parseSeniors(csvEntities);
        Interns interns = parseInterns(csvEntities, seniors);
        return new ParsingResult(seniors, interns);
    }

    private Seniors parseSeniors(List<CsvEntity> csvEntities) {
        List<Senior> seniors = csvEntities.stream()
                .filter(CsvEntity::isSenior)
                .map(csvEntity -> parseSenior(csvEntity))
                .distinct()
                .collect(toList());
        return new Seniors(seniors);
    }

    private Interns parseInterns(List<CsvEntity> csvEntities, Seniors seniors) {
        List<Intern> interns = csvEntities.stream()
                .filter(CsvEntity::isIntern)
                .map(csvEntity -> parseIsIntern(csvEntity, seniors))
                .distinct()
                .collect(toList());
        return new Interns(interns);
    }


    private Senior parseSenior(CsvEntity csvEntity) {
        return new SeniorBuilder()
                .withId(csvEntity.id())
                .withFullName(csvEntity.fullName())
                .withFirstName(csvEntity.firstName())
                .withSex(csvEntity.sex())
                .withAge(csvEntity.age())
                .withDateOfBirth(csvEntity.dateOfBirth())
                .create();
    }

    private Intern parseIsIntern(CsvEntity csvEntity, Seniors seniors) {
        return new InternBuilder()
                .withId(csvEntity.id())
                .withFirstName(csvEntity.firstName())
                .withSex(csvEntity.sex())
                .withAge(csvEntity.age())
                .withDateOfBirth(csvEntity.dateOfBirth())
                .withMentorId(csvEntity.mentorId(seniors))
                .create();
    }



}
