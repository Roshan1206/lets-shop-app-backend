package com.example.lets_shop_app.controller;

import com.example.lets_shop_app.constant.Constants;
import com.example.lets_shop_app.dto.UserInfoResponseDto;
import com.example.lets_shop_app.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User Endpoints", description = "Operational REST API endpoints related to User")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "User Info",
            description = "Get information for authenticated user",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful retrieval of user info"),
                    @ApiResponse(responseCode = "404", description = Constants.USER_NOT_FOUND),
                    @ApiResponse(responseCode = "500", description = Constants.INTERNAL_SERVER_ERROR)
            }
    )
    @GetMapping("/info")
//    public ResponseEntity<UserInfoResponseDto> getUserInfo(){
//        UserInfoResponseDto userInfoResponseDto = userService.getUserInfo();
//        return ResponseEntity.status(HttpStatus.OK).body(userInfoResponseDto);
//    }
    @ResponseStatus(HttpStatus.OK)
    public UserInfoResponseDto getUserInfo(){
        return userService.getUserInfo();
//        return ResponseEntity.status(HttpStatus.OK).body(userInfoResponseDto);
    }


    @Operation(
            summary = "Delete user",
            description = "Delete authenticated user",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User deleted successfully"),
                    @ApiResponse(responseCode = "404", description = Constants.USER_NOT_FOUND),
                    @ApiResponse(responseCode = "500", description = Constants.INTERNAL_SERVER_ERROR)
            }
    )
    @DeleteMapping
    public ResponseEntity<String> deleteUser(){
        userService.deleteUser();
        return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully");
    }
}
