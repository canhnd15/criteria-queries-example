package com.davidnguyen.criteriaqueries.department.api.resp;

import com.davidnguyen.criteriaqueries.department.persistence.Department;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class DepartmentRespDto {
    private Long id;
    private String name;

    public static DepartmentRespDto of(Department entity) {
        if (entity == null) return null;

        return DepartmentRespDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }
}
