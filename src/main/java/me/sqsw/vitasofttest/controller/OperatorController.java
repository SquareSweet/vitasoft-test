package me.sqsw.vitasofttest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/oper")
@RequiredArgsConstructor
public class OperatorController {
    @GetMapping
    public String test() {
        return "Seeing operator controller as " + SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
