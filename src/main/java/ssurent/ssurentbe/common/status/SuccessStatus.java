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
    SMS_SEND_SUCCESS(HttpStatus.OK, "AUTH_200", "인증번호가 발송되었습니다."),
    SMS_VERIFY_SUCCESS(HttpStatus.OK, "AUTH_200", "인증번호 확인 성공"),
    PASSWORD_RESET_SUCCESS(HttpStatus.OK, "AUTH_200", "비밀번호가 재설정되었습니다."),
    /**
     * User
     */
    USER_INFO_SUCCESS(HttpStatus.OK, "USER_200", "사용자 정보 조회 성공"),
    PHONE_NUMBER_UPDATE_SUCCESS(HttpStatus.OK, "UPDATE_204", "전화번호 변경 성공"),

    /**
     *  Rental
     */
    RENTAL_CREATE_SUCCESS(HttpStatus.CREATED, "RENTAL_201", "물품 대여 성공"),
    MY_RENTAL_SUCCESS(HttpStatus.OK, "RENTAL_200", "내 대여 목록 조회 성공"),
    RENTAL_HISTORY_SUCCESS(HttpStatus.OK, "RENTAL_200", "유저 대여 내역 조회 성공"),
    RENTAL_EXTEND_SUCCESS(HttpStatus.OK, "RENTAL_200", "대여 기한 연장 성공"),
    RENTAL_RETURN_SUCCESS(HttpStatus.OK, "RENTAL_200", "물품 반납 성공"),
    RENTAL_ITEM_STATISTICS_SUCCESS(HttpStatus.OK, "RENTAL_200", "물품 대여 통계 조회 성공"),
    RENTAL_PERIOD_STATISTICS_SUCCESS(HttpStatus.OK, "RENTAL_200", "월별 물품 대여 현황 조회 성공"),
    RENTAL_REPORT_SUCCESS(HttpStatus.CREATED, "RENTAL_201", "문제 신고가 접수되었습니다."),
    RENTAL_REPORT_LIST_SUCCESS(HttpStatus.OK, "RENTAL_200", "미해결 문제 신고 목록 조회 성공"),
    RENTAL_REPORT_COUNT_SUCCESS(HttpStatus.OK, "RENTAL_200", "미해결 문제 신고 건수 조회 성공"),
    RENTAL_REPORT_CHECK_SUCCESS(HttpStatus.OK, "RENTAL_200", "문제 해결 처리 성공"),
    ADMIN_RENTAL_TIMELINE_SUCCESS(HttpStatus.OK, "RENTAL_200", "전체 대여 타임라인 조회 성공"),
    ADMIN_RENTAL_FORCE_RETURN_SUCCESS(HttpStatus.OK, "RENTAL_200", "강제 반납 처리 성공"),

    /**
     *  Penalty
     */
    PENALTY_CHECK_SUCCESS(HttpStatus.OK, "PENALTY_200", "징계내역 조회 성공");
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}