package com.davidnguyen.criteriaqueries.utils;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.lang3.time.DateUtils;

import java.time.temporal.ChronoUnit;
import java.util.*;

public interface SpecificationUtils {

    String WILDCARD = "%";

    static <T> Optional<Predicate> equalPredicate(
            Path<T> path,
            T value,
            CriteriaBuilder criteriaBuilder
    ) {

        if (Objects.isNull(value) || ((value instanceof String) && ((String) value).isEmpty())) {
            return Optional.empty();
        }

        Predicate predicate = criteriaBuilder.equal(path, value);
        return Optional.of(predicate);
    }

    static <T> Optional<Predicate> notEqualPredicate(
            Path<T> path,
            T value,
            CriteriaBuilder criteriaBuilder
    ) {

        if (Objects.isNull(value) || ((value instanceof String) && ((String) value).isEmpty())) {
            return Optional.empty();
        }

        Predicate predicate = criteriaBuilder.notEqual(path, value);
        return Optional.of(predicate);
    }

    static Optional<Predicate> likeIgnoreCasePredicate(
            Path<?> path,
            String value,
            CriteriaBuilder criteriaBuilder
    ) {

        return likeIgnoreCasePredicate(path, value, criteriaBuilder, true, true);
    }

    static Optional<Predicate> likeIgnoreCasePredicate(
            Path<?> path,
            String value,
            CriteriaBuilder criteriaBuilder,
            boolean likeBefore,
            boolean likeAfter
    ) {

        if (Objects.isNull(value) || value.isEmpty()) {
            return Optional.empty();
        }

        StringBuilder likeStringBuilder = new StringBuilder();
        if (likeBefore) {
            likeStringBuilder.append(WILDCARD);
        }
        likeStringBuilder.append(value.toLowerCase());
        if (likeAfter) {
            likeStringBuilder.append(WILDCARD);
        }
        Predicate predicate = criteriaBuilder.like(criteriaBuilder.lower(path.as(String.class)),
                likeStringBuilder.toString());
        return Optional.of(predicate);
    }

    static <T> Optional<Predicate> isNullPredicate(
            Path<T> path,
            CriteriaBuilder criteriaBuilder
    ) {

        Predicate predicate = criteriaBuilder.isNull(path);
        return Optional.of(predicate);
    }

    static <T> Optional<Predicate> notNullPredicate(
            Path<T> path,
            CriteriaBuilder criteriaBuilder
    ) {

        Predicate predicate = criteriaBuilder.isNotNull(path);
        return Optional.of(predicate);
    }

    /**
     * Filter for multiple values in a column. <br> If the filter value list is empty, search empty values in database
     * (null fields). <br> If the filter value list is null, no filter is set <br> If the filter value contains null
     * alongside a real value, the table is filtered for empty values and the real value <br>
     */
    static <T> Optional<Predicate> inPredicate(
            Path<T> path,
            Collection<?> values,
            CriteriaBuilder criteriaBuilder
    ) {

        if (Objects.isNull(values)) {
            return Optional.empty();
        }
        if (values.isEmpty()) {
            return Optional.of(criteriaBuilder.isNull(path));
        }
        return Optional.ofNullable(values.stream().map(value -> Objects.isNull(value) || "".equals(value) ?
                criteriaBuilder.isNull(path) : criteriaBuilder.equal(path, value))
                .reduce(criteriaBuilder.disjunction(), criteriaBuilder::or));
    }

    static <T> Optional<Predicate> inPredicate(
            Path<T> path,
            Collection<?> values
    ) {

        if (Objects.isNull(values) || values.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(path.in(values));
    }

    static <T> Optional<Predicate> notInPredicate(
            Path<T> path,
            Collection<?> values,
            CriteriaBuilder criteriaBuilder
    ) {

        if (Objects.isNull(values) || values.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(criteriaBuilder.not(path.in(values)));
    }

    static <T> Optional<Predicate> nullCheckPredicate(
            Path<T> path,
            Collection<Boolean> values,
            CriteriaBuilder criteriaBuilder
    ) {

        if (Objects.isNull(values)) {
            return Optional.empty();
        }
        var predicates = new ArrayList<Predicate>();
        if (values.contains(Boolean.TRUE)) {
            predicates.add(criteriaBuilder.isNotNull(path));
        }
        if (values.contains(Boolean.FALSE)) {
            predicates.add(criteriaBuilder.isNull(path));
        }
        return predicates.stream().reduce(criteriaBuilder::or);
    }

    static <T> Optional<Predicate> beforeDaysPredicate(
            Path<T> path,
            Collection<Date> values,
            CriteriaBuilder criteriaBuilder
    ) {

        if (Objects.isNull(values)) {
            return Optional.empty();
        }
        var predicates = new ArrayList<Predicate>();
        values.forEach(date -> {
            Date truncated = DateUtils.truncate(date, Calendar.DAY_OF_MONTH);
            predicates.add(criteriaBuilder.lessThanOrEqualTo(
                    criteriaBuilder.function(
                            "date_trunc",
                            Date.class,
                            criteriaBuilder.literal("days"),
                            path
                    ), truncated));
        });
        return predicates.stream().reduce(criteriaBuilder::and);
    }

    static <T> Optional<Predicate> onSameDaysPredicate(
            Path<T> path,
            Collection<Date> values,
            CriteriaBuilder criteriaBuilder
    ) {

        if (Objects.isNull(values)) {
            return Optional.empty();
        }
        var predicates = new ArrayList<Predicate>();
        values.forEach(date -> {
            Date truncated = DateUtils.truncate(date, Calendar.DAY_OF_MONTH);
            predicates.add(criteriaBuilder.equal(
                    criteriaBuilder.function(
                            "date_trunc",
                            Date.class,
                            criteriaBuilder.literal("days"),
                            path
                    ), truncated));
        });
        return predicates.stream().reduce(criteriaBuilder::and);
    }

    static <T> Optional<Predicate> onSameMinutesPredicate(
            Path<T> path,
            Date value,
            CriteriaBuilder criteriaBuilder
    ) {

        if (Objects.isNull(value)) {
            return Optional.empty();
        }
        Date truncated = Date.from(value.toInstant().truncatedTo(ChronoUnit.MINUTES));
        return Optional.of(criteriaBuilder.equal(
                criteriaBuilder.function(
                        "date_trunc",
                        Date.class,
                        criteriaBuilder.literal("minutes"),
                        path
                ), truncated));
    }
}
