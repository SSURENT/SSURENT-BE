package ssurent.ssurentbe.domain.users.dto.request;


import java.time.LocalDateTime;

public record AdminUserPenaltyCreateRequest (
        LocalDateTime createdAt,
        String itemName,
        String penaltyType
){
}
