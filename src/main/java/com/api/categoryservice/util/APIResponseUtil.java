package com.api.categoryservice.util;

import com.api.categoryservice.response.ApiResponse;
import org.springframework.http.HttpStatus;

public class APIResponseUtil <T> {
    public static <T> ApiResponse<T> apiResponse(T payload, HttpStatus status) {
        return ApiResponse.<T>builder()
                .message("Successfully")
                .payload(payload)
                .status(status)
                .code(status.value())
                .build();
    }
}
