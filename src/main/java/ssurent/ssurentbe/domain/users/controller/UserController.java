package ssurent.ssurentbe.domain.users.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ssurent.ssurentbe.common.base.BaseResponse;
import ssurent.ssurentbe.common.status.SuccessStatus;
import ssurent.ssurentbe.domain.users.dto.request.UpdatePhoneNumberRequest;
import ssurent.ssurentbe.domain.users.dto.response.UserInfoResponse;
import ssurent.ssurentbe.domain.users.dto.response.UserPenaltyResponse;
import ssurent.ssurentbe.domain.users.service.UserQueryService;
import ssurent.ssurentbe.domain.users.service.UserCommandService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/users")
public class UserController {
    private final UserQueryService userQueryService;
    private final UserCommandService userCommandService;

    @GetMapping
    public UserInfoResponse getMyInfo(@AuthenticationPrincipal UserDetails userDetails) {
        return userQueryService.getMyInfo(userDetails.getUsername());
    }

    @PatchMapping("/phone-number")
    public ResponseEntity<Void> updatePhoneNumber(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody UpdatePhoneNumberRequest request
    ) {
        userCommandService.updatePhoneNumber(
                userDetails.getUsername(),
                request.phoneNum()
        );
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/penalties")
    public ResponseEntity<BaseResponse<List<UserPenaltyResponse>>> getMyPenalties(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        List<UserPenaltyResponse> data = userQueryService.getMyPenalties(userDetails.getUsername());
        SuccessStatus status = SuccessStatus.PANELTY_CHECK_SUCCESS;
        return ResponseEntity.status(status.getHttpStatus())
                .body(BaseResponse.success(status, data));
    }

}
