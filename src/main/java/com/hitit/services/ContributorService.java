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

    //A method to find and write top contributors for a list of repositories
    public List<List<Contributors>> findAndWriteTopContributors(String[] repoNames, int top) {
        RestTemplate restTemplate = new RestTemplate();
        List<List<Contributors>> resultAll = new ArrayList<>();
        for (String repoName : repoNames) {
            //Make a GET request to the repository's contributors endpoint using RestTemplate
            ResponseEntity<List<Contributors>> response =
                    restTemplate.exchange(
                            APACHE_URI + repoName + "/contributors", HttpMethod.GET, null,
                            new ParameterizedTypeReference<List<Contributors>>() {
                            });
            //Get the list of contributors from the response
            List<Contributors> result = response.getBody();
            //Map the contributors list to a list of Users, setting their contributions
            List<Users> userList = result.stream()
                    .limit(top)
                    .map(contributors -> {
                        Users user = findUser(contributors.getLogin());
                        user.setContributions(contributors.getContributions());
                        return user;
                    })
                    .collect(Collectors.toList());
            //Write the top contributors to a file
            writeTopContributorsToFile(repoName, userList);
            //Add the contributors list to the overall results list
            resultAll.add(result);
        }
        return resultAll;
    }

    //A method to find a user on GitHub by username
    private Users findUser(String username) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Users> response = restTemplate.exchange(
                GITHUB_URI + "/" + username, HttpMethod.GET, null,
                new ParameterizedTypeReference<Users>() {
                });

        return response.getBody();
    }

    //A method to write the top contributors for a repository to a file
    private void writeTopContributorsToFile(String repoName, List<Users> users) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("file.txt", true))) {
            //Transform the list of Users to a list of strings
            users.stream()
                    .map(user -> "repo: " + repoName + ", user: " + user.getLogin() + ", location: " + user.getLocation() + ", company: " + user.getCompany() + ", contributions: " + user.getContributions() + "\n")
                    .forEach(line -> {
                        try {
                            //Write each line to the file
                            writer.append(line);
                            System.out.println(line);
                        } catch (IOException e) {
                            System.out.println("An error occurred.");
                            e.printStackTrace();
                        }
                    });
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}