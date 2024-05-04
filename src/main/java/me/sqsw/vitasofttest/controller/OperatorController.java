package me.sqsw.vitasofttest.controller;

import lombok.RequiredArgsConstructor;
import me.sqsw.vitasofttest.dto.RequestFullResponse;
import me.sqsw.vitasofttest.dto.RequestShortResponse;
import me.sqsw.vitasofttest.model.RequestState;
import me.sqsw.vitasofttest.service.RequestService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/oper")
@RequiredArgsConstructor
public class OperatorController {
    private final RequestService requestService;

    @GetMapping("/requests")
    public List<RequestShortResponse> getUserRequests(@RequestParam(required = false) String username,
                                                      @RequestParam(defaultValue = "0") Integer page,
                                                      @RequestParam(required = false) String sort) {
        return requestService.geAllRequestsOperator(username, List.of(RequestState.SENT), page, sort);
    }

    @GetMapping("/requests/{requestId}")
    public RequestFullResponse getRequest(@PathVariable Long requestId) {
        return requestService.getRequestOperator(requestId);
    }

    @PostMapping("/requests/{requestId}/accept")
    public RequestFullResponse acceptRequest(@PathVariable Long requestId) {
        return requestService.accept(requestId);
    }

    @PostMapping("/requests/{requestId}/deny")
    public RequestFullResponse denyRequest(@PathVariable Long requestId) {
        return requestService.deny(requestId);
    }
}
