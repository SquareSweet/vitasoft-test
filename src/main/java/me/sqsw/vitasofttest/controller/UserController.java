package me.sqsw.vitasofttest.controller;

import lombok.RequiredArgsConstructor;
import me.sqsw.vitasofttest.dto.RequestCreateRequest;
import me.sqsw.vitasofttest.dto.RequestFullResponse;
import me.sqsw.vitasofttest.dto.RequestShortResponse;
import me.sqsw.vitasofttest.service.RequestService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final RequestService requestService;

    @GetMapping("/requests/{requestId}")
    public RequestFullResponse getRequest(@PathVariable Long requestId) {
        return requestService.getOwnRequest(requestId);
    }

    @GetMapping("/requests")
    public List<RequestShortResponse> getRequests(@RequestParam(defaultValue = "0") Integer page,
                                                  @RequestParam(required = false) String sort) {
        return requestService.getUserOwnRequests(page, sort);
    }

    @PostMapping("/requests/create")
    public RequestFullResponse createRequest(@RequestBody @Valid RequestCreateRequest request) {
        return requestService.create(request);
    }

    @PostMapping("/requests/{requestId}/edit")
    public RequestFullResponse editRequest(@RequestBody RequestCreateRequest request, @PathVariable Long requestId) {
        return requestService.edit(request, requestId);
    }

    @PostMapping("/requests/{requestId}/send")
    public RequestFullResponse createRequest(@PathVariable Long requestId) {
        return requestService.send(requestId);
    }
}
