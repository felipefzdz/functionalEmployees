package org.olid16.parser;

import java.io.IOException;

public class ParsingException extends RuntimeException {

    public ParsingException() {
        super();
    }

    public ParsingException(IOException e) {
        super(e);
    }
}
