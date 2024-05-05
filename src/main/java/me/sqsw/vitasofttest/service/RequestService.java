package me.sqsw.vitasofttest.service;

import me.sqsw.vitasofttest.dto.RequestCreateRequest;
import me.sqsw.vitasofttest.dto.RequestFullResponse;
import me.sqsw.vitasofttest.dto.RequestShortResponse;
import me.sqsw.vitasofttest.model.RequestState;

import java.util.List;

public interface RequestService {
    RequestFullResponse create(RequestCreateRequest request);

    List<RequestShortResponse> geAllRequestsOperator(String username, List<RequestState> states, Integer page, String sort);

    RequestFullResponse getOwnRequest(Long requestId);

    List<RequestShortResponse> getUserOwnRequests(Integer page, String sort);

    RequestFullResponse getRequestOperator(Long requestId);

    RequestFullResponse edit(RequestCreateRequest request, Long requestId);

    RequestFullResponse send(Long requestId);

    RequestFullResponse accept(Long requestId);

    RequestFullResponse deny(Long requestId);
}
