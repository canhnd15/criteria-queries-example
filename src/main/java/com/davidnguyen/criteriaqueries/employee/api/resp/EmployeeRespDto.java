package com.davidnguyen.criteriaqueries.employee.api.resp;

import com.davidnguyen.criteriaqueries.department.persistence.Department;
import com.davidnguyen.criteriaqueries.employee.persistence.Employee;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class EmployeeRespDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String fullName;
    private String position;
    private Department department;

    public static EmployeeRespDto of(Employee entity) {
        if(entity == null) return null;

        return EmployeeRespDto.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .fullName(String.join(" ", entity.getFirstName(), entity.getLastName()))
                .position(entity.getPosition())
                .department(entity.getDepartment())
                .build();
    }
}
