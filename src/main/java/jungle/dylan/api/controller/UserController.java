package jungle.dylan.api.controller;

import jakarta.validation.Valid;
import jungle.dylan.api.constant.UserResponseMessage;
import jungle.dylan.api.dto.UserRequest;
import jungle.dylan.api.dto.common.ApiResponse;
import jungle.dylan.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static jungle.dylan.api.constant.UserResponseMessage.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public ApiResponse<Object> join(@RequestBody @Valid UserRequest userRequest) {
        userService.join(userRequest);

        return ApiResponse.builder()
                .status(HttpStatus.OK)
                .message(JOIN_SUCCESS.getMessage())
                .build();
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserRequest userRequest) {
        String token = userService.login(userRequest);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);

        return new ResponseEntity<String>(LOGIN_SUCCESS.getMessage(), headers, HttpStatus.OK);
    }
}
