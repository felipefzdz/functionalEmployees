package org.olid16.domain.values;

import org.joda.time.DateTime;

public class DateOfBirth {
    private final DateTime value;
    public DateOfBirth(DateTime value) {
        this.value = value;
    }

    public DateTime value() {
        return value;
    }
}
