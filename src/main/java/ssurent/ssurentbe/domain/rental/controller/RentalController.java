package ssurent.ssurentbe.domain.rental.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ssurent.ssurentbe.common.base.BaseResponse;
import ssurent.ssurentbe.common.status.SuccessStatus;
import ssurent.ssurentbe.domain.rental.controller.docs.RentalApiDocs;
import ssurent.ssurentbe.domain.rental.dto.request.RentalExtendRequest;
import ssurent.ssurentbe.domain.rental.dto.request.RentalRequest;
import ssurent.ssurentbe.domain.rental.dto.request.RentalReturnRequest;
import ssurent.ssurentbe.domain.rental.dto.response.RentalItemResponse;
import ssurent.ssurentbe.domain.rental.service.RentalCommandService;
import ssurent.ssurentbe.domain.rental.service.RentalQueryService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/api/rentals")
public class RentalController implements RentalApiDocs {

    private final RentalCommandService rentalCommandService;
    private final RentalQueryService rentalQueryService;

    @Override
    @PostMapping
    public ResponseEntity<BaseResponse<?>> createRental(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody RentalRequest request
    ) {
        RentalItemResponse response = rentalCommandService.createRental(userDetails.getUsername(), request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponse.success(SuccessStatus.RENTAL_CREATE_SUCCESS, response));
    }

    @Override
    @GetMapping("/my")
    public ResponseEntity<BaseResponse<?>> getMyRentals(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        List<RentalItemResponse> responses = rentalQueryService.getMyRentals(userDetails.getUsername());
        return ResponseEntity.ok(BaseResponse.success(SuccessStatus.MY_RENTAL_SUCCESS, responses));
    }

    @Override
    @PatchMapping("/extend")
    public ResponseEntity<BaseResponse<?>> extendRental(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody RentalExtendRequest request
    ) {
        rentalCommandService.extendRental(userDetails.getUsername(), request);
        return ResponseEntity.ok(BaseResponse.success(SuccessStatus.RENTAL_EXTEND_SUCCESS, null));
    }

    @Override
    @PostMapping("/return")
    public ResponseEntity<BaseResponse<?>> returnRental(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody RentalReturnRequest request
    ) {
        rentalCommandService.returnRental(userDetails.getUsername(), request);
        return ResponseEntity.ok(BaseResponse.success(SuccessStatus.RENTAL_RETURN_SUCCESS, null));
    }
}