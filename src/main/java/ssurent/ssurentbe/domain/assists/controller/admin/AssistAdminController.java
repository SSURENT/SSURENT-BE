package ssurent.ssurentbe.domain.assists.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ssurent.ssurentbe.common.base.BaseResponse;
import ssurent.ssurentbe.common.status.SuccessStatus;
import ssurent.ssurentbe.domain.assists.controller.docs.AssistAdminApiDocs;
import ssurent.ssurentbe.domain.assists.dto.request.AdminAssistCreateRequest;
import ssurent.ssurentbe.domain.assists.dto.response.AdminAssistResponse;
import ssurent.ssurentbe.domain.assists.service.AssistCommandService;
import ssurent.ssurentbe.domain.assists.service.AssistQueryService;

import java.util.List;

@RestController
@RequestMapping("v1/admin/assists")
@RequiredArgsConstructor
public class AssistAdminController implements AssistAdminApiDocs {
    private final AssistCommandService assistCommandService;
    private final AssistQueryService assistQueryService;

    @GetMapping
    @Override
    public ResponseEntity<BaseResponse<?>> getAssists(
            @AuthenticationPrincipal UserDetails userDetails) {
        List<AdminAssistResponse> assists =  assistQueryService.getAssists();
        return ResponseEntity.status(HttpStatus.OK)
                .body(BaseResponse.success(SuccessStatus.COMM_SUCCESS_STATUS,assists));
    }

    @PostMapping
    @Override
    public ResponseEntity<BaseResponse<?>> createAssists(
            @RequestBody AdminAssistCreateRequest request) {
        AdminAssistResponse response = assistCommandService.createAssists(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponse.success(SuccessStatus.COMM_CREATE_STATUS,response));
    }

    @DeleteMapping("/{assistId}")
    @Override
    public ResponseEntity<BaseResponse<?>> deleteAssists(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long assistId) {
        assistCommandService.deleteAssist(assistId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(BaseResponse.success(SuccessStatus.COMM_SUCCESS_STATUS));
    }
}
