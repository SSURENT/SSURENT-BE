package ssurent.ssurentbe.domain.item.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum Condition {
    RENT("대여 중"),
    KEEP("보관 중"),
    OVERDUE("연체 중");

    private String description;
}