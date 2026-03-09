package ssurent.ssurentbe.domain.rental.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ssurent.ssurentbe.common.base.BaseResponse;
import ssurent.ssurentbe.common.status.SuccessStatus;
import ssurent.ssurentbe.domain.rental.controller.docs.AdminRentalApiDocs;
import ssurent.ssurentbe.domain.rental.dto.request.AdminRentalReturnRequest;
import ssurent.ssurentbe.domain.rental.service.RentalCommandService;
import ssurent.ssurentbe.domain.rental.service.RentalQueryService;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/admin/rentals")
public class AdminRentalController implements AdminRentalApiDocs {

    private final RentalCommandService rentalCommandService;
    private final RentalQueryService rentalQueryService;

    @Override
    @GetMapping
    public ResponseEntity<BaseResponse<?>> getAllRentalTimeline(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        return ResponseEntity.ok(BaseResponse.success(
                SuccessStatus.ADMIN_RENTAL_TIMELINE_SUCCESS,
                rentalQueryService.getAllRentalTimeline(startDate, endDate)
        ));
    }

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
