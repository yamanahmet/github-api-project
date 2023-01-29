package com.hitit.services;

import com.hitit.dto.Contributors;
import com.hitit.dto.Users;
import org.apache.catalina.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContributorService {

    private final WebClient webClient;
    private final String APACHE_URI = "https://api.github.com/repos/apache/echarts";
    private final String GITHUB_URI = "https://api.github.com/users/";

    public ContributorService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.github.com/repos/apache/echarts").build();  //{echarts, superset, dubbo, spark, airflow}
    }

//    public Flux<Contributors> findContributors(){
//        return this.webClient.get().uri("/contributors")
//                .retrieve()
//                .bodyToFlux(Contributors.class);
//    }
    public List<Contributors> findContributors(){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<Contributors>> response = restTemplate.exchange(
                APACHE_URI + "/contributors", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Contributors>>(){});

        List<Contributors> result = response.getBody();

        List<Users> UserList = result.stream()
                .limit(5)
                .map(contributors -> {
                    Users user = findUser(contributors.getLogin());
                    user.setContributions(contributors.getContributions());
                    return user;
                })
                .peek(System.out::println)
                .collect(Collectors.toList());

        return result;
    }

//    private String login; //username
//    private String location;
//    private String company;
//    private int contributions;

//    https://api.github.com/users/pissang


    // repo:commons-lang ,user:garydgregory , location: Denver, CO, USA, company: Rocket Software, contributions: 845

    private Users findUser(String username){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Users> response = restTemplate.exchange(
                GITHUB_URI +"/"+ username, HttpMethod.GET, null,
                new ParameterizedTypeReference<Users>(){});

        Users result = response.getBody();
        return result;

    }



}
