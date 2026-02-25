package ssurent.ssurentbe.domain.rental.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ssurent.ssurentbe.common.base.BaseResponse;
import ssurent.ssurentbe.common.status.SuccessStatus;
import ssurent.ssurentbe.domain.rental.controller.docs.RentalApiDocs;
import ssurent.ssurentbe.domain.rental.dto.request.RentalRequest;
import ssurent.ssurentbe.domain.rental.dto.response.RentalItemResponse;
import ssurent.ssurentbe.domain.rental.service.RentalCommandService;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/api/rentals")
public class RentalController implements RentalApiDocs {

    private final RentalCommandService rentalCommandService;

    @Override
    @PostMapping
    public ResponseEntity<BaseResponse<?>> createRental(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody RentalRequest request
    ) {
        RentalItemResponse response = rentalCommandService.createRental(userDetails.getUsername(), request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponse.success(SuccessStatus.RENTAL_CREATE_SUCCESS, response));
    }
}