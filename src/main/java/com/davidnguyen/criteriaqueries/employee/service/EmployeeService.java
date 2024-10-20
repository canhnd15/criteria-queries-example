package com.davidnguyen.criteriaqueries.employee.service;

import com.davidnguyen.criteriaqueries.employee.api.req.EmployeeRequestFilter;
import com.davidnguyen.criteriaqueries.employee.api.resp.EmployeeRespDto;
import com.davidnguyen.criteriaqueries.employee.persistence.Employee;
import com.davidnguyen.criteriaqueries.employee.persistence.EmployeeRepository;
import com.davidnguyen.criteriaqueries.utils.DomainException;
import com.davidnguyen.criteriaqueries.utils.PageableFilterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmployeeService implements PageableFilterService<EmployeeRequestFilter, Employee> {
    private final EmployeeRepository employeeRepository;

    @Transactional(readOnly = true)
    public Page<Employee> findByPageAndFilter(Pageable pageable, EmployeeRequestFilter filter) {
        return employeeRepository.findAll(
                EmployeeSpec.from(filter),
                pageable
        );
    }

    @Transactional(readOnly = true)
    public EmployeeRespDto getEmployeeDetail(Long employeeId) {

        return employeeRepository.findById(employeeId)
                .map(EmployeeRespDto::of)
                .orElseThrow(() -> DomainException.employeeNotFoundException(employeeId));
    }
}
