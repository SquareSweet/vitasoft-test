package me.sqsw.vitasofttest.controller;

import lombok.RequiredArgsConstructor;
import me.sqsw.vitasofttest.dto.UserInfoResponse;
import me.sqsw.vitasofttest.service.AdminService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @GetMapping("users")
    public List<UserInfoResponse> getAllUsers() {
        return adminService.getAllUsers();
    }

    @GetMapping("users/{userId}")
    public UserInfoResponse getUser(@PathVariable Long userId) {
        return adminService.getUser(userId);
    }

    @GetMapping("users/search")
    public List<UserInfoResponse> getUser(@RequestParam String username) {
        return adminService.searchByUsername(username);
    }

    @PostMapping("users/{userId}/permissions/grant")
    public UserInfoResponse grantPermissions(@PathVariable Long userId) {
        return adminService.grantOperatorPermissions(userId);
    }

    @PostMapping("users/{userId}/permissions/revoke")
    public UserInfoResponse revokePermissions(@PathVariable Long userId) {
        return adminService.revokeOperatorPermissions(userId);
    }
}
