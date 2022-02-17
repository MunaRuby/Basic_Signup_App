package com.nwabundo.mkobo.serviceimpl;

import com.nwabundo.mkobo.api.ApiResponse;
import org.springframework.http.ResponseEntity;

public class ApiResponseBuilder {
    public static <T> ResponseEntity<ApiResponse<T>> buildResponseEntity(ApiResponse<T> response) {
        return new ResponseEntity<>(response, response.getStatus());
    }
}
