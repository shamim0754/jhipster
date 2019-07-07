package com.itc.erp.service.filter;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import java.time.Instant;

public class InstantFilter extends RangeFilter<Instant> {
    private static final long serialVersionUID = 1L;

    public InstantFilter() {
    }

    @DateTimeFormat(
            iso = ISO.DATE_TIME
    )
    public InstantFilter setEquals(Instant equals) {
        super.setEquals(equals);
        return this;
    }

    @DateTimeFormat(
            iso = ISO.DATE_TIME
    )
    public InstantFilter setGreaterThan(Instant equals) {
        super.setGreaterThan(equals);
        return this;
    }

    @DateTimeFormat(
            iso = ISO.DATE_TIME
    )
    public InstantFilter setGreaterOrEqualThan(Instant equals) {
        super.setGreaterOrEqualThan(equals);
        return this;
    }

    @DateTimeFormat(
            iso = ISO.DATE_TIME
    )
    public InstantFilter setLessThan(Instant equals) {
        super.setLessThan(equals);
        return this;
    }

    @DateTimeFormat(
            iso = ISO.DATE_TIME
    )
    public InstantFilter setLessOrEqualThan(Instant equals) {
        super.setLessOrEqualThan(equals);
        return this;
    }


}
