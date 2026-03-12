package ssurent.ssurentbe.domain.rental.controller.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ssurent.ssurentbe.common.base.BaseResponse;
import ssurent.ssurentbe.common.status.SuccessStatus;
import ssurent.ssurentbe.domain.rental.controller.admin.docs.RentalAdminApiDocs;
import ssurent.ssurentbe.domain.rental.dto.request.AdminRentalReturnRequest;
import ssurent.ssurentbe.domain.rental.dto.response.AdminPeriodRentalStatisticsResponse;
import ssurent.ssurentbe.domain.rental.dto.response.AdminUserRentalHistoryResponse;
import ssurent.ssurentbe.domain.rental.service.RentalCommandService;
import ssurent.ssurentbe.domain.rental.service.RentalQueryService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/admin/rentals")
public class RentalAdminController implements RentalAdminApiDocs {

    private final RentalQueryService rentalQueryService;
    private final RentalCommandService rentalCommandService;

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

    @Override
    @GetMapping("/user")
    public ResponseEntity<BaseResponse<?>> getUserRentalHistory(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam("userId") Long userId,
            @RequestParam(name = "startDate", required = false) LocalDate startDate,
            @RequestParam(name = "endDate", required = false) LocalDate endDate,
            @RequestParam(name = "itemName", required = false) String itemName
    ) {
        List<AdminUserRentalHistoryResponse> responses =
                rentalQueryService.getUserRentalHistory(userId, startDate, endDate, itemName);
        return ResponseEntity.ok(BaseResponse.success(SuccessStatus.RENTAL_HISTORY_SUCCESS, responses));
    }

    @Override
    @GetMapping("/item-statistics")
    public ResponseEntity<BaseResponse<?>> getRentalItemStatistics(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam("categoryId") String categoryId,
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        List<?> responses = rentalQueryService.getRentalItemStatistics(categoryId, startDate, endDate);
        return ResponseEntity.ok(BaseResponse.success(SuccessStatus.RENTAL_ITEM_STATISTICS_SUCCESS, responses));
    }

    @Override
    @GetMapping("/period-statistics")
    public ResponseEntity<BaseResponse<?>> getRentalPeriodStatistics(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam("categoryId") String categoryId,
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        List<AdminPeriodRentalStatisticsResponse> responses =
                rentalQueryService.getRentalPeriodStatistics(categoryId, startDate, endDate);
        return ResponseEntity.ok(BaseResponse.success(SuccessStatus.RENTAL_PERIOD_STATISTICS_SUCCESS, responses));
    }
}
