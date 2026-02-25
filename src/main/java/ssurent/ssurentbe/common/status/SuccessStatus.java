package ssurent.ssurentbe.common.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import ssurent.ssurentbe.common.base.BaseStatus;

@Getter
@AllArgsConstructor
public enum SuccessStatus implements BaseStatus {
    COMM_SUCCESS_STATUS(HttpStatus.OK, "COMM_200", "성공적으로 처리되었습니다."),
    COMM_CREATE_STATUS(HttpStatus.CREATED,"COMM_201", "성공적으로 항목이 생성되었습니다."),

    /**
     * Auth
     */
    LOGIN_SUCCESS(HttpStatus.OK, "AUTH_200", "로그인 성공"),
    LOGOUT_SUCCESS(HttpStatus.OK, "AUTH_200", "로그아웃 성공"),
    REISSUE_TOKEN_SUCCESS(HttpStatus.OK, "AUTH_200", "토큰 재발급 성공"),
    WITHDRAW_SUCCESS(HttpStatus.OK, "AUTH_200", "회원탈퇴 성공"),
    SIGNUP_SUCCESS(HttpStatus.CREATED, "AUTH_201", "회원가입 성공"),

    /**
     *  Rental
     */
    RENTAL_HISTORY_SUCCESS(HttpStatus.OK, "RENTAL_200", "유저 대여 내역 조회 성공"),

    /**
     *  Penalty
     */
    PENALTY_CHECK_SUCCESS(HttpStatus.OK, "PENALTY_200", "징계내역 조회 성공");
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}