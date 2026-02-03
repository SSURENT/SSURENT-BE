package ssurent.ssurentbe.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ssurent.ssurentbe.common.base.BaseResponse;
import ssurent.ssurentbe.common.base.BaseStatus;
import ssurent.ssurentbe.common.status.ErrorStatus;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<BaseResponse<Void>> handleGeneralException(GeneralException e) {
        ErrorStatus status = e.getStatus();
        if (status.getHttpStatus().is5xxServerError()) {
            log.error("[*] GeneralException :", e);
        } else {
            log.error("[*] GeneralException : {}", e.getMessage());
        }
        return ResponseEntity
                .status(status.getHttpStatus())
                .body(BaseResponse.error(status));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<BaseResponse<Void>> handleIllegalArgumentException(IllegalArgumentException e) {
        String errorMessage = "잘못된 요청입니다: " + e.getMessage();
        log.error("[*] IllegalArgumentException :", e);
        return ResponseEntity
                .status(ErrorStatus.BAD_REQUEST.getHttpStatus())
                .body(BaseResponse.error(ErrorStatus.BAD_REQUEST, errorMessage));
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<BaseResponse<Void>> handleNullPointerException(NullPointerException e) {
        String errorMessage = "서버에서 예기치 않은 오류가 발생했습니다. 요청을 처리하는 중에 Null 값이 참조되었습니다.";
        log.error("[*] NullPointerException :", e);
        return ResponseEntity
                .status(ErrorStatus.INTERNAL_SERVER_ERROR.getHttpStatus())
                .body(BaseResponse.error(ErrorStatus.INTERNAL_SERVER_ERROR, errorMessage));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<Void>> handleException(Exception e) {
        log.error("[*] Internal Server Error :", e);
        return ResponseEntity
                .status(ErrorStatus.INTERNAL_SERVER_ERROR.getHttpStatus())
                .body(BaseResponse.error(ErrorStatus.INTERNAL_SERVER_ERROR));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException e,
            HttpHeaders headers,
            HttpStatusCode statusCode,
            WebRequest webRequest
    ) {
        BaseStatus errorStatus = ErrorStatus.BAD_REQUEST;
        String errorMessage = e.getBindingResult().getFieldErrors().isEmpty()
                ? errorStatus.getMessage()
                : e.getBindingResult().getFieldErrors().get(0).getDefaultMessage();

        BaseResponse<Void> body = BaseResponse.error(ErrorStatus.BAD_REQUEST, errorMessage);
        return handleExceptionInternal(e, body, headers, statusCode, webRequest);
    }
}
