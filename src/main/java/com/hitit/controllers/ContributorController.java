package com.hitit.controllers;

import com.hitit.GithubApiProjectApplication;
import com.hitit.dto.Contributors;
import com.hitit.services.ContributorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ContributorController {
    final ContributorService contributorService;

    @GetMapping(value = "/contributors", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<List<List<Contributors>>> findAllContributors() {
        return ResponseEntity.ok(contributorService.findContributors());
    }
}
