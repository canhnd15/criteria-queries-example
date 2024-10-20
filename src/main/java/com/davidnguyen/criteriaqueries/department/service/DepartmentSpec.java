package com.davidnguyen.criteriaqueries.department.service;

import com.davidnguyen.criteriaqueries.department.api.req.DepartmentRequestFilter;
import com.davidnguyen.criteriaqueries.department.persistence.Department;
import com.davidnguyen.criteriaqueries.department.persistence.Department_;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;

import static com.davidnguyen.criteriaqueries.utils.SpecificationUtils.likeIgnoreCasePredicate;

@RequiredArgsConstructor
public class DepartmentSpec implements Specification<Department> {
    private final DepartmentRequestFilter filter;

    public static Specification<Department> from(final DepartmentRequestFilter filter) {
        return new DepartmentSpec(filter);
    }

    @Override
    public Predicate toPredicate(Root<Department> root,
                                 CriteriaQuery<?> query,
                                 @NotNull CriteriaBuilder criteriaBuilder) {
        query.distinct(true);
        var predicates = new ArrayList<Predicate>();

        likeIgnoreCasePredicate(root.get(Department_.NAME), filter.getName(), criteriaBuilder).ifPresent(predicates::add);

        return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
    }
}
