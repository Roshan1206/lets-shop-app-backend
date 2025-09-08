package com.example.lets_shop_app.controller;

import com.example.lets_shop_app.constant.Constants;
import com.example.lets_shop_app.dto.request.UpdatePasswordRequest;
import com.example.lets_shop_app.dto.response.UserInfoResponseDto;
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
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

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
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserInfoResponseDto> getUserInfo(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserInfo());
    }


    public ResponseEntity<String> updatePassword(@RequestBody UpdatePasswordRequest passwordRequest) {

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
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("User deleted successfully");
    }
}
