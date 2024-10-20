package com.davidnguyen.criteriaqueries.department.service;

import com.davidnguyen.criteriaqueries.department.api.req.DepartmentRequestFilter;
import com.davidnguyen.criteriaqueries.department.api.resp.DepartmentRespDto;
import com.davidnguyen.criteriaqueries.department.persistence.Department;
import com.davidnguyen.criteriaqueries.department.persistence.DepartmentRepository;
import com.davidnguyen.criteriaqueries.employee.api.resp.EmployeeRespDto;
import com.davidnguyen.criteriaqueries.utils.DomainException;
import com.davidnguyen.criteriaqueries.utils.PageableFilterService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class DepartmentService implements PageableFilterService<DepartmentRequestFilter, Department> {
    private final DepartmentRepository departmentRepository;

    @Transactional(readOnly = true)
    @Override
    public Page<Department> findByPageAndFilter(Pageable pageable, DepartmentRequestFilter filter) {
        return departmentRepository.findAll(
                DepartmentSpec.from(filter),
                pageable
        );
    }

    @Transactional(readOnly = true)
    public DepartmentRespDto getDepartmentDetail(Long departmentId) {

        return departmentRepository.findById(departmentId)
                .map(DepartmentRespDto::of)
                .orElseThrow(() -> DomainException.departmentNotFoundException(departmentId));
    }
}
