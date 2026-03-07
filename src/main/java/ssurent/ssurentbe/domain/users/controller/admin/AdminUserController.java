package ssurent.ssurentbe.domain.users.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssurent.ssurentbe.common.base.BaseResponse;
import ssurent.ssurentbe.common.status.SuccessStatus;
import ssurent.ssurentbe.domain.users.controller.docs.AdminUserApiDocs;
import ssurent.ssurentbe.domain.users.dto.request.AdminUserPenaltyCreateRequest;
import ssurent.ssurentbe.domain.users.dto.request.AdminUserStatusUpdateRequest;
import ssurent.ssurentbe.domain.users.dto.response.AdminUserDetailResponse;
import ssurent.ssurentbe.domain.users.dto.response.AdminUserResponse;
import ssurent.ssurentbe.domain.users.service.UserCommandService;
import ssurent.ssurentbe.domain.users.service.UserPenaltyCommandService;
import ssurent.ssurentbe.domain.users.service.UserQueryService;

import java.util.List;

@RestController
@RequestMapping("v1/admin/users")
@RequiredArgsConstructor
public class AdminUserController implements AdminUserApiDocs {
    private final UserQueryService userQueryService;
    private final UserCommandService userCommandService;
    private final UserPenaltyCommandService userPenaltyCommandService;

    @Override
    @GetMapping()
    public ResponseEntity<BaseResponse<List<AdminUserResponse>>> getUsersByStatus(
            @RequestParam String status
    ) {
        List<AdminUserResponse> responses = userQueryService.getUsersByStatus(status);
        return ResponseEntity.ok(BaseResponse.success(SuccessStatus.COMM_SUCCESS_STATUS, responses));
    }

    @Override
    @GetMapping("/{userId}")
    public ResponseEntity<BaseResponse<AdminUserDetailResponse>> getUserDetails(
            @PathVariable Long userId) {
        AdminUserDetailResponse response = userQueryService.getUserDetails(userId);
        return ResponseEntity.ok(BaseResponse.success(SuccessStatus.COMM_SUCCESS_STATUS, response));
    }

    @Override
    @PatchMapping("/status")
    public ResponseEntity<BaseResponse<?>> changeStatus(@RequestBody AdminUserStatusUpdateRequest request) {
        userCommandService.changeStatus(request);
        return ResponseEntity.ok(BaseResponse.success(SuccessStatus.COMM_SUCCESS_STATUS));
    }

    @Override
    @PostMapping("/penalties")
    public ResponseEntity<BaseResponse<?>> createPenalty(
            @RequestBody AdminUserPenaltyCreateRequest request) {
        userPenaltyCommandService.createPenalty(request);
        return ResponseEntity.ok(BaseResponse.success(SuccessStatus.COMM_CREATE_STATUS));
    }

    @Override
    @DeleteMapping("/penalties/{penaltyId}")
    public ResponseEntity<BaseResponse<?>> deletePenalty(
            @PathVariable Long penaltyId) {
        userPenaltyCommandService.deletePenalty(penaltyId);
        return ResponseEntity.ok(BaseResponse.success(SuccessStatus.COMM_SUCCESS_STATUS));
    }
}
