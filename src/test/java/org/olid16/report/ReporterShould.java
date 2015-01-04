package org.olid16.report;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.junit.Before;
import org.junit.Test;
import org.olid16.domain.collections.Seniors;
import org.olid16.domain.entities.Senior;
import org.olid16.domain.values.*;
import org.olid16.parser.ParsingResult;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.olid16.domain.entities.Senior.*;

public class ReporterShould {

    private static final String MALES_MESSAGE = "There are %d males";
    private static final String AVERAGE_AGE_MESSAGE = "The average age is %.1f";
    private static final String DAYS_OLDER_MESSAGE = "%s is %d days older than %s";
    private String message;
    private StubTarget target;

    @Before
    public void setUp() throws Exception {
        target = new StubTarget();
    }

    @Test
    public void count_n_male_when_there_is_n_male_on_employees(){
        int numberOfMales = new Random().nextInt(10);
        aReporterFor(employeesWithNMales(numberOfMales)).countMales();
        String malesMessage = String.format(MALES_MESSAGE, numberOfMales);
        assertThat(message, is(malesMessage));
    }

    @Test
    public void write_the_proper_average_age(){
        List<Integer> ages = randomAges();
        aReporterFor(employeesWith(ages)).averageAge();
        String averageAgeMessage = String.format(AVERAGE_AGE_MESSAGE, averageAgeOf(ages));
        assertThat(message, is(averageAgeMessage));
    }

    @Test
    public void write_how_many_days_older_is_a_employee_respect_another_one(){
        Senior aSenior = aEmployeeWith(aDateOfBirth(), aFullName());
        Senior anotherSenior = aEmployeeWith(aDateOfBirth(), aFullName());
        int daysOlder = daysOlderFor(aSenior.dateOfBirth().value(), anotherSenior.dateOfBirth().value());
        aReporterFor(employeesWith(aSenior, anotherSenior)).howManyDaysOlder(aSenior.fullName().value(), anotherSenior.fullName().value());
        String days_older_message = String.format(DAYS_OLDER_MESSAGE, aSenior.fullName().value(), daysOlder, anotherSenior.fullName().value());
        assertThat(message, is(days_older_message));
    }

    private Seniors employeesWith(Senior aSenior, Senior anotherSenior) {
        List<Senior> seniors = new ArrayList<>();
        seniors.add(aSenior);
        seniors.add(anotherSenior);
        return new Seniors(seniors);
    }

    private FullName aFullName() {
        return new FullName(UUID.randomUUID().toString());
    }

    private Senior aEmployeeWith(DateTime dateOfBirth, FullName fullName) {
        return new SeniorBuilder().
                withDateOfBirth(new DateOfBirth(dateOfBirth))
                .withFullName(fullName)
                .create();
    }

    private DateTime aDateOfBirth() {
        long beginTime = Timestamp.valueOf("1984-01-01 00:00:00").getTime();
        long endTime = Timestamp.valueOf("1986-12-31 00:58:00").getTime();
        long diff = endTime - beginTime + 1;
        long randomTimeBetweenTwoDates = beginTime + (long) (Math.random() * diff);

        return new DateTime(randomTimeBetweenTwoDates);
    }

    private int daysOlderFor(DateTime aDate, DateTime anotherDate) {
        return Days.daysBetween(aDate, anotherDate).getDays();
    }

    private double averageAgeOf(List<Integer> ages) {
        return ages.stream()
                .mapToInt(x -> x)
                .average()
                .orElse(0);
    }

    private List<Integer> randomAges() {
        int numberOfEmployees = new Random().nextInt(10);
        List<Integer> ages = new ArrayList<>();
        for(int i=0; i< numberOfEmployees; i++){
            ages.add(new Random().nextInt(100));
        }
        return ages;
    }

    private Seniors employeesWith(List<Integer> ages) {
        List<Senior> seniors = ages.stream()
                .map(age -> anEmployeeOf(age))
                .collect(toList());
        return new Seniors(seniors);
    }

    private Senior anEmployeeOf(Integer age) {
        return new SeniorBuilder()
                .withAge(new Age(age))
                .create();
    }


    private Seniors employeesWithNMales(int numberOfMales) {
        List<Senior> seniors = new ArrayList<>();
        for(int i=0; i < numberOfMales; i++){
            seniors.add(aMale());
        }
        seniors.add(aFemale());
        seniors.add(aFemale());
        return new Seniors(seniors);
    }

    private Senior aMale() {
        return new SeniorBuilder()
                .withSex(Sex.MALE)
                .create();
    }

    private Senior aFemale() {
        return new SeniorBuilder()
                .withSex(Sex.FEMALE)
                .create();
    }

    private Reporter aReporterFor(Seniors seniors) {
        return new Reporter(target, new ParsingResult(seniors, null));
    }


    private class StubTarget extends Target {
        @Override
        public void write(String _message) {
            message = _message;
        }
    }
}
