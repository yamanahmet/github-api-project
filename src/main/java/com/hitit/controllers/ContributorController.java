package com.hitit.controllers;

import com.hitit.GithubApiProjectApplication;
import com.hitit.dto.Contributors;
import com.hitit.services.ContributorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ContributorController {

    final ContributorService contributorService;

    @GetMapping(value = "/contributors", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<Contributors> findAllContributors(){
        return contributorService.findContributors();
    }

//    @Autowired
//    WebClientService webClientService;
//


//    @GetMapping("/user/{id}")
//    @ResponseStatus(HttpStatus.ACCEPTED)
//    public Mono<Users> getUserById(@PathVariable Long id){
//        return webClientService.findUserById(id);
//    }
//
//    @GetMapping(value = "/users", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//    @ResponseStatus(HttpStatus.ACCEPTED)
//    public Flux<Users> findAllUsers(){
//        return webClientService.findUsers();
//    }


//    @PostMapping("/save")
//    @ResponseStatus(HttpStatus.CREATED)
//    public void saveUser(@RequestBody Users users){
//        webClientService.saveUser(users);
//    }
//
//    @PutMapping("/update")
//    public Mono<Users> updateUser(@RequestBody Users user){
//        return webClientService.updateUser(user);
//    }
//
//    @DeleteMapping("/user/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public Mono<Void> deleteUser(@PathVariable Long id){
//        return webClientService.deleteUser(id);
//    }



}
