package ssurent.ssurentbe.common.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import ssurent.ssurentbe.common.status.ErrorStatus;
import ssurent.ssurentbe.common.status.SuccessStatus;

@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse<T> {

    private final String code;
    private final String message;
    private final T data;

    public static <T> BaseResponse<T> success(SuccessStatus status, T data) {
        return new BaseResponse<>(status.getCode(), status.getMessage(), data);
    }

    public static <T> BaseResponse<T> success(SuccessStatus status) {
        return new BaseResponse<>(status.getCode(), status.getMessage(), null);
    }

    public static <T> BaseResponse<T> error(ErrorStatus status) {
        return new BaseResponse<>(status.getCode(), status.getMessage(), null);
    }
}
