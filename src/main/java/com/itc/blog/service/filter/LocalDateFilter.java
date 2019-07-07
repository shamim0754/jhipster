package com.itc.erp.service.filter;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;

public class LocalDateFilter extends RangeFilter<LocalDate> {
    private static final long serialVersionUID = 1L;

    public LocalDateFilter() {
    }

    @DateTimeFormat(
            iso = DATE
    )
    public LocalDateFilter setEquals(LocalDate equals) {
        super.setEquals(equals);
        return this;
    }

    @DateTimeFormat(
            iso = DATE
    )
    public LocalDateFilter setGreaterThan(LocalDate equals) {
        super.setGreaterThan(equals);
        return this;
    }

    @DateTimeFormat(
            iso = DATE
    )
    public LocalDateFilter setGreaterOrEqualThan(LocalDate equals) {
        super.setGreaterOrEqualThan(equals);
        return this;
    }

    @DateTimeFormat(
            iso = DATE
    )
    public LocalDateFilter setLessThan(LocalDate equals) {
        super.setLessThan(equals);
        return this;
    }

    @DateTimeFormat(
            iso = DATE
    )
    public LocalDateFilter setLessOrEqualThan(LocalDate equals) {
        super.setLessOrEqualThan(equals);
        return this;
    }

    @DateTimeFormat(
            iso = DATE
    )
    public LocalDateFilter setIn(List<LocalDate> in) {
        super.setIn(in);
        return this;
    }
}
