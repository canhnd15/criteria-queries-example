package com.davidnguyen.criteriaqueries.utils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PageableFilterService <F, D> {

    Page<D> findByPageAndFilter(final Pageable pageable, final F filter);
}
