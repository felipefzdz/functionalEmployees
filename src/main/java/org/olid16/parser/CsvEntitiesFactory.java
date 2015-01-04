package org.olid16.parser;

import java.io.BufferedReader;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static org.olid16.utils.Strings.splitByComma;

public class CsvEntitiesFactory {

    public List<CsvEntity> create(BufferedReader reader, List<String> header){
        return reader.lines()
                .map(line -> csvEntity(header, splitByComma(line)))
                .collect(toList());
    }

    private CsvEntity csvEntity(List<String> header, List<String> content) {
        if (header.size() != content.size()){
            throw new ParsingException();
        }

        List<CsvEntity.Entry> entries = IntStream.range(0, header.size())
                .mapToObj(i -> new CsvEntity.Entry(header.get(i), content.get(i)))
                .collect(toList());

        return new CsvEntity(entries);
    }
}
