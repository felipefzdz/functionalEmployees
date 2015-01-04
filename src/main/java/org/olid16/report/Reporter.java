package org.olid16.report;

import org.olid16.domain.collections.Seniors;
import org.olid16.domain.collections.Interns;
import org.olid16.domain.entities.Senior;
import org.olid16.domain.entities.Intern;
import org.olid16.domain.values.FirstName;
import org.olid16.domain.values.FullName;
import org.olid16.domain.values.Sex;
import org.olid16.parser.ParsingResult;

import java.util.List;
import java.util.NoSuchElementException;

import static java.util.stream.Collectors.joining;

public class Reporter {
    private static final String MALES_MESSAGE = "There are %d males";
    private static final String AVERAGE_AGE_MESSAGE = "The average age is %.1f";
    private static final String DAYS_OLDER_MESSAGE = "%s is %d days older than %s";
    private static final String HOW_MANY_SENIORS_HAVE_INTERNS_MESSAGE = "%d seniors have interns";
    private static final String WHAT_INTERNS_PETER_HAS_MESSAGE = "The interns of Peter are %s";

    private final Target target;
    private final Seniors seniors;
    private final Interns interns;

    public Reporter(Target target, ParsingResult parsingResult) {
        this.target = target;
        this.seniors = parsingResult.seniors();
        this.interns = parsingResult.interns();
    }

    public void countMales() {
        long numberOfMales = seniors.countBy(Sex.MALE);
        target.write(String.format(MALES_MESSAGE, numberOfMales));
    }

    public void averageAge() {
        double averageAge = seniors.averageAge();
        target.write(String.format(AVERAGE_AGE_MESSAGE, averageAge));
    }

    public void howManyDaysOlder(String aFullName, String anotherFullName) {
        try {
            int daysOlder = seniorBy(aFullName).howManyDaysOlderThan(seniorBy(anotherFullName));
            target.write(String.format(DAYS_OLDER_MESSAGE, aFullName, daysOlder, anotherFullName));
        } catch (NoSuchElementException e) {
            target.write("Error in howManyDaysOlder");
        }
    }

    private Senior seniorBy(String aFullName) {
        return seniors.getBy(new FullName(aFullName));
    }

    public void howManySeniorsHaveInterns() {
        long distinctMentors = interns.countDistinctMentors();
        target.write(String.format(HOW_MANY_SENIORS_HAVE_INTERNS_MESSAGE, distinctMentors));
    }

    public void whatInternsPeterHas() {
        try {
            Senior peter = seniors.getBy(new FirstName("Peter"));
            List<Intern> petsOfPeter = interns.getBy(peter);
            String petsOfPeterNames = petsOfPeter.stream()
                    .map(Intern::firstName)
                    .map(FirstName::value)
                    .collect(joining(", "));
            target.write(String.format(WHAT_INTERNS_PETER_HAS_MESSAGE, petsOfPeterNames));
        } catch (NoSuchElementException e) {
            target.write("Error in whatInternsPeterHas");
        }
    }
}
