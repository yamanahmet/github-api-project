package com.hitit.services;

import com.hitit.GithubApiProjectApplication;
import com.hitit.dto.Contributors;
import com.hitit.dto.Users;
import org.apache.catalina.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContributorService {

    //    private final WebClient webClient;
    private final String APACHE_URI = "https://api.github.com/repos/apache/"; //https://api.github.com/repos/apache/echarts
    private final String[] REPO_NAME = {"echarts", "superset", "dubbo", "spark", "airflow"};
    private final String GITHUB_URI = "https://api.github.com/users/";

//    private GithubApiProjectApplication githubApiProjectApplication;

//    public ContributorService(WebClient.Builder webClientBuilder) {
//        this.webClient = webClientBuilder.baseUrl("https://api.github.com/repos/apache/echarts").build();  //{echarts, superset, dubbo, spark, airflow}
//    }

    //    public Flux<Contributors> findContributors(){
//        return this.webClient.get().uri("/contributors")
//                .retrieve()
//                .bodyToFlux(Contributors.class);
//    }
    public List<List<Contributors>> findContributors(){
        RestTemplate restTemplate = new RestTemplate();
        List<List<Contributors>> resultAll = new ArrayList<>();
        for (String repoName : REPO_NAME) {
            ResponseEntity<List<Contributors>> response =
                    restTemplate.exchange(
                            APACHE_URI + repoName + "/contributors", HttpMethod.GET, null,
                            new ParameterizedTypeReference<List<Contributors>>() {
                            });

            List<Contributors> result = response.getBody();

            List<Users> userList = result.stream()
                    .limit(5)
                    .map(contributors -> {
                        Users user = findUser(contributors.getLogin());
                        user.setContributions(contributors.getContributions());
//                    githubApiProjectApplication.write(APACHE_URI, user.getClass().isArray());
                        return user;
                    })
//                .peek(System.out::println)
                    .collect(Collectors.toList());
            BufferedWriter writer = null;
            try {
                writer = new BufferedWriter(new FileWriter("test.txt", true));
                for (Users user : userList) {
                    String line = "repo: " + repoName + ", user: " + user.getLogin() + ", location: " + user.getLocation() + ", company: " + user.getCompany() + ", contributions: " + user.getContributions() + "\n";
                    writer.append(line);
                    System.out.println(line);
                }
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            resultAll.add(result);
        }
        try {
//            if (CollectionUtils.isEmpty(resultAll)) {
//                FileWriter myWriter = new FileWriter("C:\\Users\\ayaman\\Downloads\\users.txt");
//                for (List<Contributors> contributors : resultAll) {
//                     myWriter.append(contributors.toString());
//                }
//                myWriter.close();
////                return
//                // Error resultAll
//            }
// write txt
            // OK
            return resultAll;
        } catch (Exception e) {
// ERROR
            return resultAll;
        }
    }

//    private String login; //username
//    private String location;
//    private String company;
//    private int contributions;

//    https://api.github.com/users/pissang


    // repo:commons-lang ,user:garydgregory , location: Denver, CO, USA, company: Rocket Software, contributions: 845

    private Users findUser(String username) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Users> response = restTemplate.exchange(
                GITHUB_URI + "/" + username, HttpMethod.GET, null,
                new ParameterizedTypeReference<Users>() {
                });

        Users result = response.getBody();

//        githubApiProjectApplication.write(APACHE_URI, (List<Users>) findUser("pissang"));
        return result;
    }


}
