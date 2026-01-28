package ssurent.ssurentbe.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ssurent.ssurentbe.common.base.BaseResponse;
import ssurent.ssurentbe.common.status.ErrorStatus;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<BaseResponse<Void>> handleGeneralException(GeneralException e) {
        ErrorStatus status = (ErrorStatus) e.getStatus();
        return ResponseEntity
                .status(status.getHttpStatus())
                .body(BaseResponse.error(status));
    }
}
