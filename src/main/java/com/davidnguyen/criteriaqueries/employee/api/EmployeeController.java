package com.davidnguyen.criteriaqueries.employee.api;

import com.davidnguyen.criteriaqueries.common.ApiResponseDto;
import com.davidnguyen.criteriaqueries.common.ResponseStatus;
import com.davidnguyen.criteriaqueries.employee.api.req.EmployeeRequestFilter;
import com.davidnguyen.criteriaqueries.employee.persistence.Employee_;
import com.davidnguyen.criteriaqueries.employee.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping("/employees")
    ResponseEntity<ApiResponseDto<?>> getEmployees(
            @PageableDefault(
                    size = Integer.MAX_VALUE,
                    sort = Employee_.FIRST_NAME,
                    direction = Sort.Direction.DESC
            ) final Pageable pageable,
            EmployeeRequestFilter filter
    ) {

        ApiResponseDto<?> resp = ApiResponseDto.builder()
                .data(employeeService.findByPageAndFilter(pageable, filter))
                .status(ResponseStatus.SUCCESS.name())
                .message("Get employees successfully!")
                .build();

        return ResponseEntity.ok(resp);
    }

    @GetMapping("/employees/{id}")
    ResponseEntity<ApiResponseDto<?>> getEmployeeDetail(@PathVariable Long id) {

        ApiResponseDto<?> resp = ApiResponseDto.builder()
                .data(employeeService.getEmployeeDetail(id))
                .status(ResponseStatus.SUCCESS.name())
                .message("Get employee detail successfully!")
                .build();

        return ResponseEntity.ok(resp);
    }
}
