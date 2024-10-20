package com.davidnguyen.criteriaqueries.common;

import lombok.*;

@AllArgsConstructor
@Setter
@Getter
@Builder
public class ApiResponseDto<T> {
    private String status;
    private String message;
    private T data;
}
