package ssurent.ssurentbe.domain.users.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PenaltyTypes {
    OVERDUE("반납기한 경과"),
    UNAUTHORIZED_USE("무단 사용"),
    OTHER("기타");

    private final String description;
}
