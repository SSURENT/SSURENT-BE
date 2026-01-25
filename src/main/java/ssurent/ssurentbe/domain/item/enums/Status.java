package ssurent.ssurentbe.domain.item.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Status {
    ACTIVE("대여 가능"),
    FIXING("수리 중"),
    INACTIVE("대여 불가");

    private String description;
}
