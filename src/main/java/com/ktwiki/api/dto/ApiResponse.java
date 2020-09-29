package com.ktwiki.api.dto;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class ApiResponse<T> {

    private ZonedDateTime timestamp = ZonedDateTime.now();
    private String error = "";
    private String message = "";
    private T payload;

    public static <T> ApiResponse success(T payload) {
        ApiResponse next = new ApiResponse();
        next.payload = payload;
        next.message = "success";
        return next;
    }

    public static <T> ApiResponse error(Throwable t) {
        ApiResponse next = new ApiResponse();
        next.setError("error");
        next.setMessage(t.getMessage());
        return next;
    }

}
