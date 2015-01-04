package org.olid16;

import org.olid16.parser.CsvEntitiesFactory;
import org.olid16.parser.CsvParser;
import org.olid16.parser.ParsingResult;
import org.olid16.report.Reporter;
import org.olid16.report.Target;

public class App {

    public static final String OLD_FORMAT_FILE = "src/main/resources/oldFormat.txt";
    private static final String NEW_FORMAT_FILE = "src/main/resources/newFormat.txt";
    private final CsvParser csvParser = new CsvParser(new CsvEntitiesFactory());
    private final Target target = new Target();

    public void run() {
        reportFirstFile();
        reportSecondFile();
    }

    private void reportFirstFile() {
        ParsingResult parsingResult = csvParser.parse(OLD_FORMAT_FILE);
        Reporter reporter = new Reporter(target, parsingResult);

        reporter.countMales();
        reporter.averageAge();
        reporter.howManyDaysOlder("Carla Satriani", "Dave Mustaine");
    }

    private void reportSecondFile() {
        ParsingResult parsingResult = csvParser.parse(NEW_FORMAT_FILE);
        Reporter reporter = new Reporter(target, parsingResult);

        reporter.howManySeniorsHaveInterns();
        reporter.whatInternsPeterHas();

    }
}
