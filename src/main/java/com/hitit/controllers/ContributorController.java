package com.hitit.controllers;

import com.hitit.dto.Contributors;
import com.hitit.services.ContributorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ContributorController {
    final ContributorService contributorService;
    final String[] repoNames = {"echarts", "superset", "dubbo", "spark", "airflow"};

    @GetMapping(value = "/contributors", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<List<List<Contributors>>> findAllContributors() {
        return ResponseEntity.ok(contributorService.findAndWriteTopContributors(repoNames, 10));
    }
}
