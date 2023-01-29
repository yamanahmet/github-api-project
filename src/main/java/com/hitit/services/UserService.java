package com.hitit.services;

import com.hitit.dto.Users;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class UserService {

    private final WebClient webClient;

    public UserService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.github.com/users").build();
    }

    public Mono<Users> findUserById(String login){
        return this.webClient.get().uri("/{login}", login)
                .retrieve()
                .bodyToMono(Users.class);
    }

}
