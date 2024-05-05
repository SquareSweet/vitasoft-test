package me.sqsw.vitasofttest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import me.sqsw.vitasofttest.dto.UserInfoResponse;
import me.sqsw.vitasofttest.service.AdminService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SecurityRequirement(name = "JWT Authentication")
@Tag(name = "2. Admin", description = "Contains all the operations that can be performed by an Administrator.")
@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @Operation(summary = "Get all users", description = "Get list of all users")
    @GetMapping("users")
    public List<UserInfoResponse> getAllUsers() {
        return adminService.getAllUsers();
    }

    @Operation(summary = "Get user", description = "Get info on one specific user by user id")
    @GetMapping("users/{userId}")
    public UserInfoResponse getUser(@PathVariable Long userId) {
        return adminService.getUser(userId);
    }

    @Operation(summary = "Search users", description = "Search users by full or partial username")
    @GetMapping("users/search")
    public List<UserInfoResponse> getUser(@RequestParam String username) {
        return adminService.searchByUsername(username);
    }

    @Operation(summary = "Grant operator permissions", description = "Grant operator permissions to user by id")
    @PostMapping("users/{userId}/permissions/grant")
    public UserInfoResponse grantPermissions(@PathVariable Long userId) {
        return adminService.grantOperatorPermissions(userId);
    }

    @Operation(summary = "Revoke operator permissions", description = "Revoke operator permissions to user by id")
    @PostMapping("users/{userId}/permissions/revoke")
    public UserInfoResponse revokePermissions(@PathVariable Long userId) {
        return adminService.revokeOperatorPermissions(userId);
    }
}
