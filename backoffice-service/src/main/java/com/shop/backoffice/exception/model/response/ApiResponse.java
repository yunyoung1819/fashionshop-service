package com.shop.backoffice.exception.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 공통 API 응답 구조
 *
 * @param <T> 실제 응답 데이터 타입
 */
public record ApiResponse<T>(
    boolean success,
    String message,
    @JsonInclude(Include.NON_NULL)
    T data
) {
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, "OK", data);
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, data);
    }

    public static <T> ApiResponse<T> fail(String message) {
        return new ApiResponse<>(false, message, null);
    }
}