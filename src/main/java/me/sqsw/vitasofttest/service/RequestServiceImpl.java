package me.sqsw.vitasofttest.service;

import lombok.RequiredArgsConstructor;
import me.sqsw.vitasofttest.dto.RequestCreateRequest;
import me.sqsw.vitasofttest.dto.RequestFullResponse;
import me.sqsw.vitasofttest.dto.RequestShortResponse;
import me.sqsw.vitasofttest.exception.RequestNotFoundException;
import me.sqsw.vitasofttest.mapper.RequestMapper;
import me.sqsw.vitasofttest.model.Request;
import me.sqsw.vitasofttest.model.RequestState;
import me.sqsw.vitasofttest.model.User;
import me.sqsw.vitasofttest.repository.RequestRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;
    private final UserService userService;
    private final RequestMapper requestMapper;

    @Override
    @Transactional
    public RequestFullResponse create(RequestCreateRequest requestDto) {

        Request request = requestMapper.toRequest(requestDto, getUserFromContext());
        request = requestRepository.save(request);
        return requestMapper.toRequestFull(request);
    }

    @Override
    public List<RequestShortResponse> geAllRequestsOperator(String username, List<RequestState> states, Integer page, String sort) {
        Sort sortValue;
        if (sort == null) {
            sortValue = Sort.unsorted();
        } else if ("old".equals(sort)) {
            sortValue = Sort.by("sentOn").ascending();
        } else if ("new".equals(sort)) {
            sortValue = Sort.by("sentOn").descending();
        } else {
            throw new IllegalArgumentException("Unknown sort value: " + sort);
        }
        if (username != null) {
            List<Long> userIds = userService.getUsersByPartialUsername(username).stream()
                    .map(User::getId)
                    .collect(Collectors.toList());
            return requestRepository.findByUserIdInAndStateIn(userIds, states,
                            PageRequest.of(page, 5, sortValue)).stream()
                    .map(requestMapper::requestShort)
                    .collect(Collectors.toList());
        } else {
            return requestRepository.findByStateIn(states, PageRequest.of(page, 5, sortValue)).stream()
                    .map(requestMapper::requestShort)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public RequestFullResponse getOwnRequest(Long requestId) {
        User user = getUserFromContext();
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new RequestNotFoundException(requestId));
        if (!request.getUser().equals(user) || request.getState() == RequestState.SENT) {
            throw new AccessDeniedException("Access denied");
        }
        return requestMapper.toRequestFull(request);
    }

    @Override
    public List<RequestShortResponse> getUserOwnRequests(Integer page, String sort) {
        User user = getUserFromContext();
        Sort sortValue;
        if (sort == null) {
            sortValue = Sort.unsorted();
        } else if ("old".equals(sort)) {
            sortValue = Sort.by("createdOn").ascending();
        } else if ("new".equals(sort)) {
            sortValue = Sort.by("createdOn").descending();
        } else {
            throw new IllegalArgumentException("Unknown sort value: " + sort);
        }
        return requestRepository.findByUserId(user.getId(), PageRequest.of(page, 5, sortValue)).stream()
                .map(requestMapper::requestShort)
                .collect(Collectors.toList());
    }

    @Override
    public RequestFullResponse getRequestOperator(Long requestId) {
        Request request = requestRepository.findByIdAndState(requestId, RequestState.SENT)
                .orElseThrow(() -> new RequestNotFoundException(requestId));
        RequestFullResponse response = requestMapper.toRequestFull(request);
        response.setText(response.getText().replaceAll("(?<=.)(?=.)", "-"));
        return response;
    }

    @Override
    @Transactional
    public RequestFullResponse edit(RequestCreateRequest requestDto, Long requestId) {
        User user = getUserFromContext();
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new RequestNotFoundException(requestId));
        if (!request.getUser().equals(user) || request.getState() != RequestState.DRAFT) {
            throw new AccessDeniedException("Access denied");
        }
        if (requestDto.getTitle() != null && !requestDto.getTitle().isBlank()) {
            request.setTitle(requestDto.getTitle());
        }
        if (requestDto.getText() != null && !requestDto.getText().isBlank()) {
            request.setText(requestDto.getText());
        }
        request = requestRepository.save(request);
        return requestMapper.toRequestFull(request);
    }

    @Override
    @Transactional
    public RequestFullResponse send(Long requestId) {
        User user = getUserFromContext();
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new RequestNotFoundException(requestId));
        if (!request.getUser().equals(user) || request.getState() != RequestState.DRAFT) {
            throw new AccessDeniedException("Access denied");
        }
        request.setState(RequestState.SENT);
        request.setSentOn(LocalDateTime.now());
        request = requestRepository.save(request);
        return requestMapper.toRequestFull(request);
    }

    @Override
    @Transactional
    public RequestFullResponse accept(Long requestId) {
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new RequestNotFoundException(requestId));
        if (request.getState() != RequestState.SENT) {
            throw new AccessDeniedException("Access denied");
        }
        request.setState(RequestState.ACCEPTED);
        request = requestRepository.save(request);
        return requestMapper.toRequestFull(request);
    }

    @Override
    @Transactional
    public RequestFullResponse deny(Long requestId) {
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new RequestNotFoundException(requestId));
        if (request.getState() != RequestState.SENT) {
            throw new AccessDeniedException("Access denied");
        }
        request.setState(RequestState.DENIED);
        request = requestRepository.save(request);
        return requestMapper.toRequestFull(request);
    }

    private User getUserFromContext() {
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        return userService.getUserByUsername(username); //throws exception if not found;;
    }
}
