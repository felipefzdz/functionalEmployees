package org.olid16.utils;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

import java.util.List;

public class Strings {

    private static final String COMMA = ",";

    public static List<String> splitByComma(String line) {
        Iterable<String> iterable = Splitter.on(COMMA)
                .trimResults()
                .split(line);
        return Lists.newArrayList(iterable);
    }
}
