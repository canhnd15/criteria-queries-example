package com.davidnguyen.criteriaqueries.employee.service;

import com.davidnguyen.criteriaqueries.employee.api.req.EmployeeRequestFilter;
import com.davidnguyen.criteriaqueries.employee.persistence.Employee;
import com.davidnguyen.criteriaqueries.employee.persistence.Employee_;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;

import static com.davidnguyen.criteriaqueries.utils.SpecificationUtils.equalPredicate;
import static com.davidnguyen.criteriaqueries.utils.SpecificationUtils.likeIgnoreCasePredicate;

@RequiredArgsConstructor
public class EmployeeSpec implements Specification<Employee> {
    private final EmployeeRequestFilter filter;

    public static Specification<Employee> from(final EmployeeRequestFilter filter) {
        return new EmployeeSpec(filter);
    }

    @Override
    public Predicate toPredicate(Root<Employee> root,
                                 CriteriaQuery<?> query,
                                 @NotNull CriteriaBuilder criteriaBuilder) {
        query.distinct(true);
        var predicates = new ArrayList<Predicate>();

        likeIgnoreCasePredicate(root.get(Employee_.FIRST_NAME), filter.getFirstName(), criteriaBuilder).ifPresent(predicates::add);
        likeIgnoreCasePredicate(root.get(Employee_.LAST_NAME), filter.getLastName(), criteriaBuilder).ifPresent(predicates::add);
        equalPredicate(root.get(Employee_.POSITION), filter.getPosition(), criteriaBuilder).ifPresent(predicates::add);

        return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
    }
}
