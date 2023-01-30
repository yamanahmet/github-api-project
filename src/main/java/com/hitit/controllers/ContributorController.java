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

    RestTemplate restTemplate;

    final ContributorService contributorService;


    @GetMapping(value = "/contributors", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<List<List<Contributors>>> findAllContributors() {
        return ResponseEntity.ok(contributorService.findContributors());
    }

//    @GetMapping(value = "/ccc")
//    public String getContributors(){
//        HttpHeaders headers = new HttpHeaders();
//        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//        HttpEntity<String> entity = new HttpEntity<String>(headers);
//
//        return restTemplate.exchange("" +
//                "https://api.github.com/users/yamanhamet", HttpMethod.GET, entity, String.class).getBody();
//    }

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
