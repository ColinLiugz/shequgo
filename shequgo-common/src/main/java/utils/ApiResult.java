package utils;

import lombok.Data;

/**
 * @Author: Colin
 * @Date: 2019/3/23 9:49
 */
@Data
public class ApiResult {

    private int code;
    private Object data;

    public ApiResult(int code, Object data) {
        this.code =  code;
        this.data = data;
    }

    public static ApiResult ok() {
        return new ApiResult(1,null);
    }

    public static ApiResult ok(Object data) {
        return new ApiResult(1,data);
    }

    public static ApiResult error() {
        return new ApiResult(0, null);
    }

    public static ApiResult error(Object data) {
        return new ApiResult(0, data);
    }
}
