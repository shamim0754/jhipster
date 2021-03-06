package com.itc.erp.service.filter;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import java.time.ZonedDateTime;
import java.util.List;

public class ZonedDateTimeFilter extends RangeFilter<ZonedDateTime> {
    private static final long serialVersionUID = 1L;

    public ZonedDateTimeFilter() {
    }

    @DateTimeFormat(
            iso = ISO.DATE_TIME
    )
    public ZonedDateTimeFilter setEquals(ZonedDateTime equals) {
        super.setEquals(equals);
        return this;
    }

    @DateTimeFormat(
            iso = ISO.DATE_TIME
    )
    public ZonedDateTimeFilter setGreaterThan(ZonedDateTime equals) {
        super.setGreaterThan(equals);
        return this;
    }

    @DateTimeFormat(
            iso = ISO.DATE_TIME
    )
    public ZonedDateTimeFilter setGreaterOrEqualThan(ZonedDateTime equals) {
        super.setGreaterOrEqualThan(equals);
        return this;
    }

    @DateTimeFormat(
            iso = ISO.DATE_TIME
    )
    public ZonedDateTimeFilter setLessThan(ZonedDateTime equals) {
        super.setLessThan(equals);
        return this;
    }

    @DateTimeFormat(
            iso = ISO.DATE_TIME
    )
    public ZonedDateTimeFilter setLessOrEqualThan(ZonedDateTime equals) {
        super.setLessOrEqualThan(equals);
        return this;
    }

    @DateTimeFormat(
            iso = ISO.DATE_TIME
    )
    public ZonedDateTimeFilter setIn(List<ZonedDateTime> in) {
        super.setIn(in);
        return this;
    }
}
