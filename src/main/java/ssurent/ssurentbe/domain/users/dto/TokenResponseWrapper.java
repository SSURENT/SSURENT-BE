package ssurent.ssurentbe.domain.users.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import ssurent.ssurentbe.common.base.BaseResponse;

@Schema(description = "토큰 응답")
public class TokenResponseWrapper extends BaseResponse<TokenResponse> {
    public TokenResponseWrapper(String code, String message, TokenResponse data) {
        super(code, message, data);
    }
}
