package com.davidnguyen.criteriaqueries.utils;

import com.davidnguyen.criteriaqueries.common.ErrorCode;
import lombok.Getter;

import java.util.UUID;

@Getter
public class DomainException extends RuntimeException {

    private final ErrorCode errorCode;

    public DomainException(
            final ErrorCode errorCode,
            final String message
    ) {
        super(message);
        this.errorCode = errorCode;
    }

    public DomainException(
            final ErrorCode errorCode,
            final Throwable cause
    ) {
        super(cause);
        this.errorCode = errorCode;
    }

    public static DomainException employeeNotFoundException(
            final Long productId
    ) {
        return new DomainException(
                ErrorCode.EMPLOYEE_NOT_FOUND,
                "Employee not found with id: " + productId
        );
    }

    public static DomainException departmentNotFoundException(
            final Long vendorId
    ) {
        return new DomainException(
                ErrorCode.DEPARTMENT_NOT_FOUND,
                "Department not found with id: " + vendorId
        );
    }
}
