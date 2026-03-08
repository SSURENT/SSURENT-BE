package ssurent.ssurentbe.domain.rental.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProblemType {
    DAMAGE("파손"),
    LOSS("분실"),
    MALFUNCTION("기능불량"),
    OTHER("기타");

    private final String description;
}