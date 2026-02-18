package ssurent.ssurentbe.domain.item.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Status {
    ACTIVE("활성화"),
    INACTIVE("비활성화");

    private String description;
}
