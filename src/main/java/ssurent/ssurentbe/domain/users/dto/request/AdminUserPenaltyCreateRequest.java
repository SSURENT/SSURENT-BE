package ssurent.ssurentbe.domain.users.dto.request;


import java.time.LocalDateTime;

public record AdminUserPenaltyCreateRequest (
        String itemName,
        String penaltyType
){
}
