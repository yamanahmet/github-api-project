package com.hitit.services;

import com.hitit.dto.Repos;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
@Service
public class RepoService {

    private final WebClient webClient;


    public RepoService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.github.com/repos").build();
    }

    public Flux<Repos> findRepos(){
        return this.webClient.get().uri("/apache/echarts")
                .retrieve()
                .bodyToFlux(Repos.class);
    }
}
