package com.ozan.exchange.model.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private T data;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ApiException> errors = new ArrayList<>();

    public static ApiResponse<?> failed(List<ApiException> errors) {
        ApiResponse<?> apiResponseModel = empty();
        apiResponseModel.setErrors(errors);
        return apiResponseModel;
    }

    public static ApiResponse<?> failed(int errorCode, String error, String errorMessage) {
        ApiResponse<?> apiResponseModel = empty();
        ApiException apiErrorModel = new ApiException(errorCode, error, errorMessage);
        apiResponseModel.setErrors(Collections.singletonList(apiErrorModel));
        return apiResponseModel;
    }

    public static <T> ApiResponse<T> success(T responseBody) {
        ApiResponse<T> apiResponse = new ApiResponse<>();
        apiResponse.setData(responseBody);
        return apiResponse;
    }

    public static ApiResponse<Void> empty() {
        return new ApiResponse<>();
    }
}
