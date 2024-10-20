package com.davidnguyen.criteriaqueries.department.api.req;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class DepartmentRequestFilter {
    private String name;
}
