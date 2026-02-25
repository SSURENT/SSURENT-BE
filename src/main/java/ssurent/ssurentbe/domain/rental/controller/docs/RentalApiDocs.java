package ssurent.ssurentbe.domain.rental.controller.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import ssurent.ssurentbe.common.base.BaseResponse;
import ssurent.ssurentbe.domain.rental.dto.request.RentalRequest;

@Tag(name = "Rental", description = "물품 대여 API")
public interface RentalApiDocs {

    @Operation(
            summary = "물품 대여",
            description = "물품을 대여합니다. 대여 기간은 7일이며, 대여 즉시 물품 상태가 비활성화됩니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "물품 대여 성공",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "401", description = "인증 실패",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "404", description = "물품 또는 도우미를 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "409", description = "이미 대여 중인 물품",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    ResponseEntity<BaseResponse<?>> createRental(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody RentalRequest request
    );
}
