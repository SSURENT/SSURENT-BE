package ssurent.ssurentbe.domain.users.dto.request;


import java.time.LocalDateTime;

    public record AdminUserPenaltyCreateRequest (
            Long userId,
            String itemName,
            String penaltyType
    ){
    }
