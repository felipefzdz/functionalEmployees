package org.olid16.parser;

import org.olid16.domain.collections.Seniors;
import org.olid16.domain.collections.Interns;

public class ParsingResult {

    private final Seniors seniors;
    private final Interns interns;

    public ParsingResult(Seniors seniors, Interns interns) {
        this.seniors = seniors;
        this.interns = interns;
    }

    public Seniors seniors() {
        return seniors;
    }

    public Interns interns() {
        return interns;
    }
}
