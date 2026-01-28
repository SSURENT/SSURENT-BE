package ssurent.ssurentbe.common.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import ssurent.ssurentbe.common.base.BaseStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseStatus {

    COMM_ERROR_STATUS(HttpStatus.BAD_REQUEST, "COMM_400", "잘못된 요청입니다."),

    /**
     * Common
     */
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMM_400", "잘못된 요청입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMM_401", "인증이 필요합니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "COMM_403", "접근 권한이 없습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "COMM_404", "요청한 자원을 찾을 수 없습니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "COMM_405", "허용되지 않은 메소드입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMM_500", "서버 내부 오류입니다."),

    /**
     * Auth
     */
    DUPLICATE_STUDENT_NUM(HttpStatus.CONFLICT, "AUTH_409", "이미 가입된 학번입니다."),
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "AUTH_401", "학번 또는 비밀번호가 일치하지 않습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "AUTH_404", "사용자를 찾을 수 없습니다."),
    USER_WITHDRAWN(HttpStatus.FORBIDDEN, "AUTH_403", "탈퇴한 사용자입니다."),
    INVALID_VERIFICATION_CODE(HttpStatus.UNAUTHORIZED, "AUTH_401", "인증 코드가 일치하지 않습니다."),
    VERIFICATION_CODE_EXPIRED(HttpStatus.UNAUTHORIZED, "AUTH_401", "인증 코드가 만료되었습니다."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "AUTH_401", "비밀번호가 올바르지 않습니다."),

    /**
     * JWT
     */
    JWT_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "JWT_401", "토큰이 존재하지 않습니다."),
    JWT_INVALID_SIGNATURE(HttpStatus.UNAUTHORIZED, "JWT_401", "잘못된 JWT 서명입니다."),
    JWT_MALFORMED(HttpStatus.UNAUTHORIZED, "JWT_401", "잘못된 JWT 형식입니다."),
    JWT_EXPIRED(HttpStatus.UNAUTHORIZED, "JWT_401", "만료된 JWT 토큰입니다."),
    JWT_UNSUPPORTED(HttpStatus.UNAUTHORIZED, "JWT_401", "지원되지 않는 JWT 토큰입니다."),
    JWT_INVALID(HttpStatus.UNAUTHORIZED, "JWT_401", "JWT 토큰이 잘못되었습니다."),
    JWT_EXTRACT_ID_FAILED(HttpStatus.UNAUTHORIZED, "JWT_401", "토큰에서 사용자 정보를 추출할 수 없습니다."),
    JWT_GENERAL_ERROR(HttpStatus.UNAUTHORIZED, "JWT_401", "JWT 토큰 처리 중 알 수 없는 오류가 발생했습니다."),
    JWT_INVALID_TYPE(HttpStatus.UNAUTHORIZED, "JWT_401", "토큰 타입이 유효하지 않습니다."),
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "JWT_401", "DB에 저장된 토큰과 일치하지 않습니다."),
    REFRESH_TOKEN_MISMATCH(HttpStatus.UNAUTHORIZED, "JWT_401", "리프레시 토큰 정보가 사용자 정보와 일치하지 않습니다."),
    JWT_EXTRACT_ROLE_FAILED(HttpStatus.UNAUTHORIZED, "JWT_401", "토큰에서 사용자 Role을 추출할 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
