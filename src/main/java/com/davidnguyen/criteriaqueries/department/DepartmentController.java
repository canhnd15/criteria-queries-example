package com.davidnguyen.criteriaqueries.department;

import com.davidnguyen.criteriaqueries.common.ApiResponseDto;
import com.davidnguyen.criteriaqueries.common.ResponseStatus;
import com.davidnguyen.criteriaqueries.department.api.req.DepartmentRequestFilter;
import com.davidnguyen.criteriaqueries.department.persistence.Department_;
import com.davidnguyen.criteriaqueries.department.service.DepartmentService;
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

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class DepartmentController {
    private final DepartmentService departmentService;

    @GetMapping("/departments")
    ResponseEntity<ApiResponseDto<?>> getDepartments(
            @PageableDefault(
                    size = Integer.MAX_VALUE,
                    sort = Department_.NAME,
                    direction = Sort.Direction.DESC
            ) final Pageable pageable,
            DepartmentRequestFilter filter
    ) {

        ApiResponseDto<?> resp = ApiResponseDto.builder()
                .data(departmentService.findByPageAndFilter(pageable, filter))
                .status(ResponseStatus.SUCCESS.name())
                .message("Get departments successfully!")
                .build();

        return ResponseEntity.ok(resp);
    }

    @GetMapping("/departments/{id}")
    ResponseEntity<ApiResponseDto<?>> getDepartmentDetail(@PathVariable Long id) {

        ApiResponseDto<?> resp = ApiResponseDto.builder()
                .data(departmentService.getDepartmentDetail(id))
                .status(ResponseStatus.SUCCESS.name())
                .message("Get department detail successfully!")
                .build();

        return ResponseEntity.ok(resp);
    }
}
