package me.sqsw.vitasofttest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import me.sqsw.vitasofttest.dto.RequestCreateRequest;
import me.sqsw.vitasofttest.dto.RequestFullResponse;
import me.sqsw.vitasofttest.dto.RequestShortResponse;
import me.sqsw.vitasofttest.service.RequestService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@SecurityRequirement(name = "JWT Authentication")
@Tag(name = "3. User", description = "Contains all the operations that can be performed by a User.")
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final RequestService requestService;

    @Operation(summary = "Get request", description = "Get full information on one specific request created by authenticated user")
    @GetMapping("/requests/{requestId}")
    public RequestFullResponse getRequest(@PathVariable Long requestId) {
        return requestService.getOwnRequest(requestId);
    }

    @Operation(summary = "Get all requests", description = "Get all requests created by authenticated user")
    @GetMapping("/requests")
    public List<RequestShortResponse> getRequests(@RequestParam(defaultValue = "0") Integer page,
                                                  @RequestParam(required = false) String sort) {
        return requestService.getUserOwnRequests(page, sort);
    }

    @Operation(summary = "Create request", description = "Create new request as a draft")
    @PostMapping("/requests/create")
    public RequestFullResponse createRequest(@RequestBody @Valid RequestCreateRequest request) {
        return requestService.create(request);
    }

    @Operation(summary = "Edit request", description = "Edit existing request draft")
    @PostMapping("/requests/{requestId}/edit")
    public RequestFullResponse editRequest(@RequestBody RequestCreateRequest request, @PathVariable Long requestId) {
        return requestService.edit(request, requestId);
    }

    @Operation(summary = "Send request", description = "Submit request draft for review")
    @PostMapping("/requests/{requestId}/send")
    public RequestFullResponse createRequest(@PathVariable Long requestId) {
        return requestService.send(requestId);
    }
}
