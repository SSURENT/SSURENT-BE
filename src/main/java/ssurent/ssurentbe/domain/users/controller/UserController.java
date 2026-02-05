package ssurent.ssurentbe.domain.users.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ssurent.ssurentbe.domain.users.dto.UpdatePhoneNumberRequest;
import ssurent.ssurentbe.domain.users.dto.UserInfoResponse;
import ssurent.ssurentbe.domain.users.dto.UserPenaltyResponse;
import ssurent.ssurentbe.domain.users.entity.Users;
import ssurent.ssurentbe.domain.users.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public UserInfoResponse getMyInfo(@AuthenticationPrincipal UserDetails userDetails) {
        return userService.getMyInfo(userDetails.getUsername());
    }


}
