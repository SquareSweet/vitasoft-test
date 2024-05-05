package me.sqsw.vitasofttest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import me.sqsw.vitasofttest.dto.RequestFullResponse;
import me.sqsw.vitasofttest.dto.RequestShortResponse;
import me.sqsw.vitasofttest.model.RequestState;
import me.sqsw.vitasofttest.service.RequestService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SecurityRequirement(name = "JWT Authentication")
@Tag(name = "2. Operator", description = "Contains all the operations that can be performed by an Operator.")
@RestController
@RequestMapping("/api/v1/oper")
@RequiredArgsConstructor
public class OperatorController {
    private final RequestService requestService;

    @Operation(summary = "Get all requests", description = "Get all the requests created by every user")
    @GetMapping("/requests")
    public List<RequestShortResponse> getUserRequests(@RequestParam(required = false) String username,
                                                      @RequestParam(defaultValue = "0") Integer page,
                                                      @RequestParam(required = false) String sort) {
        return requestService.geAllRequestsOperator(username, List.of(RequestState.SENT), page, sort);
    }

    @Operation(summary = "Get request", description = "Get full information on one specific request")
    @GetMapping("/requests/{requestId}")
    public RequestFullResponse getRequest(@PathVariable Long requestId) {
        return requestService.getRequestOperator(requestId);
    }

    @Operation(summary = "Accept request", description = "Accept request by id")
    @PostMapping("/requests/{requestId}/accept")
    public RequestFullResponse acceptRequest(@PathVariable Long requestId) {
        return requestService.accept(requestId);
    }

    @Operation(summary = "Deny request", description = "Deny request by id")
    @PostMapping("/requests/{requestId}/deny")
    public RequestFullResponse denyRequest(@PathVariable Long requestId) {
        return requestService.deny(requestId);
    }
}
