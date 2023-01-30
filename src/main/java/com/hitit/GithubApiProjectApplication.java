package com.hitit;

import com.hitit.services.ContributorService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class GithubApiProjectApplication {

	public static void main(String[] args) {
		//An array of repository names to find the top contributors for
		final String[] repoNames = {"echarts", "superset", "dubbo", "spark", "airflow"};
		SpringApplication.run(GithubApiProjectApplication.class, args);
		final ContributorService contributorService = new ContributorService();
		//Find and write the top 5 contributors for each repository
		contributorService.findAndWriteTopContributors(repoNames, 5);
	}
}
