package com.hitit.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(MockitoJUnitRunner.class)
public class ContributorServiceTest {

    @InjectMocks
    private ContributorService contributorService;

    @Mock
    private RestTemplate restTemplate;

    @Test
    public void testFindAndWriteTopContributors() {

    }

    @Test
    public void findUser_returnsUser() {

    }

    @Test
    public void writeTopContributorsToFile(){

    }
}
