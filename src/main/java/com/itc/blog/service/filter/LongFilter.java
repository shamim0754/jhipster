package com.itc.erp.service.filter;

public class LongFilter extends RangeFilter<Long> {
    private static final long serialVersionUID = 1L;

    public LongFilter() {
    }
    public LongFilter(LongFilter filter) {
        super(filter);
    }

    public LongFilter copy() {
        return new LongFilter(this);
    }	

}
