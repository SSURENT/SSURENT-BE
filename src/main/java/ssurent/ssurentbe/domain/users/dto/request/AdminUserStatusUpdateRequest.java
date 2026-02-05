package ssurent.ssurentbe.domain.users.dto.request;

import ssurent.ssurentbe.domain.users.enums.Status;

public record AdminUserStatusUpdateRequest(
        Status status
) {
}
