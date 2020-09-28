package develop.bluedot.server.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Data
public class ApiResponse<B,T> {

    public static ApiResponse<Integer,String> OK = new ApiResponse<>(200, "", "OK");

    private int code;
    private String message;
    private T data;

    static <B,T> ApiResponse<B,T> of(T data) {
        return new ApiResponse<>(200, "upload 성공", data);
    }

}