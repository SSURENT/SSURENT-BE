package ssurent.ssurentbe.domain.assists.dto.response;

import ssurent.ssurentbe.domain.assists.entity.Assists;

public record AdminAssistResponse(
        Long assistId,
        String assistName
) {
    public static AdminAssistResponse from(Assists assists){
        return new AdminAssistResponse(assists.getId(), assists.getName());
    }
}
