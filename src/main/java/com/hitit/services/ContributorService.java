package com.hitit.services;

import com.hitit.dto.Contributors;
import com.hitit.dto.Users;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContributorService {

    private final String APACHE_URI = "https://api.github.com/repos/apache/";
    private final String GITHUB_URI = "https://api.github.com/users/";
    private final String[] REPO_NAME = {"echarts", "superset", "dubbo", "spark", "airflow"};

    public List<List<Contributors>> findContributors() {
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
                        return user;
                    })
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
                System.out.println("Successfully wrote to the file.");
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
            resultAll.add(result);
        }
        return resultAll;
    }

    private Users findUser(String username) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Users> response = restTemplate.exchange(
                GITHUB_URI + "/" + username, HttpMethod.GET, null,
                new ParameterizedTypeReference<Users>() {
                });

        Users result = response.getBody();
        return result;
    }
}