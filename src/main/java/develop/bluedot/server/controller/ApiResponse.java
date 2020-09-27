package develop.bluedot.server.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Data
public class ApiResponse<T> {

    public static ApiResponse<String> OK = new ApiResponse<>("", "", "OK");

    private String code;
    private String message;
    private T data;

    static <T> ApiResponse<T> of(T data) {
        return new ApiResponse<>("", "", data);
    }

}