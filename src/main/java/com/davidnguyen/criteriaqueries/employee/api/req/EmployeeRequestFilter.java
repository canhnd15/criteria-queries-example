package com.davidnguyen.criteriaqueries.employee.api.req;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class EmployeeRequestFilter {
    private String firstName;
    private String lastName;
    private String position;
}
