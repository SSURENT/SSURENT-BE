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
import ssurent.ssurentbe.domain.users.controller.docs.UserApiDocs;
import ssurent.ssurentbe.domain.users.service.UserQueryService;
import ssurent.ssurentbe.domain.users.service.UserCommandService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/users")
public class UserController implements UserApiDocs {
    private final UserQueryService userQueryService;
    private final UserCommandService userCommandService;

    @Override
    @GetMapping
    public UserInfoResponse getMyInfo(@AuthenticationPrincipal UserDetails userDetails) {
        return userQueryService.getMyInfo(userDetails.getUsername());
    }

    @Override
    @PatchMapping("/phone-number")
    public ResponseEntity<BaseResponse<Void>> updatePhoneNumber(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody UpdatePhoneNumberRequest request
    ) {
        userCommandService.updatePhoneNumber(
                userDetails.getUsername(),
                request.phoneNum()
        );
        SuccessStatus status = SuccessStatus.PHONE_NUMBER_UPDATE_SUCCESS;

        return ResponseEntity.status(status.getHttpStatus())
                .body(BaseResponse.success(status, null));
    }
    @Override
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
