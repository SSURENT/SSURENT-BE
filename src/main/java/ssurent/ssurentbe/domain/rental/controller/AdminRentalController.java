package ssurent.ssurentbe.domain.rental.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ssurent.ssurentbe.common.base.BaseResponse;
import ssurent.ssurentbe.common.status.SuccessStatus;
import ssurent.ssurentbe.domain.rental.controller.docs.AdminRentalApiDocs;
import ssurent.ssurentbe.domain.rental.dto.request.AdminRentalReturnRequest;
import ssurent.ssurentbe.domain.rental.service.RentalCommandService;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/admin/rentals")
public class AdminRentalController implements AdminRentalApiDocs {

    private final RentalCommandService rentalCommandService;

    @Override
    @PatchMapping
    public ResponseEntity<BaseResponse<?>> adminForceReturn(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody AdminRentalReturnRequest request
    ) {
        rentalCommandService.adminForceReturn(request);
        return ResponseEntity.ok(BaseResponse.success(SuccessStatus.ADMIN_RENTAL_FORCE_RETURN_SUCCESS, null));
    }
}
