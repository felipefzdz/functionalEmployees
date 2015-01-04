package org.olid16.parser;

import org.junit.Before;
import org.junit.Test;
import org.olid16.domain.collections.Seniors;
import org.olid16.domain.collections.Interns;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CsvParserShould {

    private static final String MORE_CONTENTS_THAN_HEADERS_FILE = "src/test/resources/moreContentsThanHeaders.txt";
    private static final String EMPTY_FILE = "src/test/resources/empty.txt";
    private static final String HEADER_NOT_EMPLOYEES_FILE = "src/test/resources/headerNotEmployees.txt";
    private static final String MORE_HEADERS_THAN_CONTENTS_FILE = "src/test/resources/moreHeadersThanContents.txt";
    private static final String ONE_EMPLOYEE_FILE = "src/test/resources/oneEmployee.txt";
    private static final String TWO_EMPLOYEES_FILE = "src/test/resources/twoEmployees.txt";
    private static final String TWO_EMPLOYEES_DUPLICATED_FILE = "src/test/resources/twoEmployeesDuplicated.txt";
    private static final String WRONG_FORMAT_DATE_OF_BIRTH_FILE = "src/test/resources/wrongFormatDateOfBirthFile.txt";
    private static final String ONE_SENIOR_AND_ONE_INTERN_FILE = "src/test/resources/oneSeniorAndOneIntern.txt";
    private CsvParser csvParser;
    private CsvEntitiesFactory csvEntitiesFactory;

    @Before
    public void setUp() throws Exception {
        csvEntitiesFactory = new CsvEntitiesFactory();
        csvParser = new CsvParser(csvEntitiesFactory);
    }

    @Test
    public void parse_one_employee_when_file_contains_info_for_one_employee(){
        Seniors seniors = csvParser.parse(ONE_EMPLOYEE_FILE).seniors();
        assertThat(seniors.count(), is(1));
    }

    @Test
    public void parse_two_employees_when_file_contains_info_for_two_employees(){
        Seniors seniors = csvParser.parse(TWO_EMPLOYEES_FILE).seniors();
        assertThat(seniors.count(), is(2));
    }

    @Test(expected = ParsingException.class)
    public void throw_parsing_exception_when_file_is_empty(){
        csvParser.parse(EMPTY_FILE);
    }

    @Test
    public void parse_no_employees_when_file_has_header_but_not_employees(){
        Seniors seniors = csvParser.parse(HEADER_NOT_EMPLOYEES_FILE).seniors();
        assertThat(seniors.count(), is(0));
    }

    @Test(expected = ParsingException.class)
    public void throw_parsing_exception_when_there_are_more_headers_than_contents(){
        csvParser.parse(MORE_HEADERS_THAN_CONTENTS_FILE);
    }

    @Test(expected = ParsingException.class)
    public void throw_parsing_exception_when_there_are_more_contents_than_headers(){
        csvParser.parse(MORE_CONTENTS_THAN_HEADERS_FILE);
    }

    @Test
    public void parse_one_senior_when_there_are_two_seniors_with_same_full_name(){
        Seniors seniors = csvParser.parse(TWO_EMPLOYEES_DUPLICATED_FILE).seniors();
        assertThat(seniors.count(), is(1));
    }

    @Test(expected = ParsingException.class)
    public void throw_parsing_exception_when_date_of_birth_has_wrong_format(){
        csvParser.parse(WRONG_FORMAT_DATE_OF_BIRTH_FILE);
    }

    @Test
    public void parse_one_senior_and_one_intern_when_file_contains_info_for_one_senior_and_one_intern(){
        ParsingResult parsingResult = csvParser.parse(ONE_SENIOR_AND_ONE_INTERN_FILE);
        Seniors seniors = parsingResult.seniors();
        assertThat(seniors.count(), is(1));

        Interns interns = parsingResult.interns();
        assertThat(interns.count(), is(1));
    }

}
