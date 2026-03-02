package ssurent.ssurentbe.domain.assists.controller.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import ssurent.ssurentbe.common.base.BaseResponse;
import ssurent.ssurentbe.domain.assists.dto.request.AdminAssistCreateRequest;
import ssurent.ssurentbe.domain.assists.dto.response.AdminAssistResponse;

@Tag(name = "Assist", description = "대여사업 도우미 API")
public interface AssistAdminApiDocs {
    @Operation(summary = "대여사업 도우미 조회", description = "대여사업 도우미 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "대여사업 도우미 목록 조회 성공",
                    content = @Content(
                            array = @ArraySchema(schema = @Schema(implementation = AdminAssistResponse.class))
                    )),
            @ApiResponse(responseCode = "401", description = "인증 실패",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    ResponseEntity<BaseResponse<?>> getAssists(
    );

    @Operation(summary = "대여사업 도우미 생성", description = "대여사업 도우미를 생성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "대여사업 도우미 생성 성공",
                    content = @Content(
                            schema = @Schema(implementation = AdminAssistResponse.class)
                    )),
            @ApiResponse(responseCode = "401", description = "인증 실패",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    ResponseEntity<BaseResponse<?>> createAssists(
            AdminAssistCreateRequest request
    );

    @Operation(summary = "대여사업 도우미 삭제", description = "대여사업 도우미를 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "대여사업 도우미 삭제 성공",
                    content = @Content(
                            schema = @Schema(implementation = BaseResponse.class)
                    )),
            @ApiResponse(responseCode = "401", description = "인증 실패",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    ResponseEntity<BaseResponse<?>> deleteAssists(
            Long assistId
    );


}
