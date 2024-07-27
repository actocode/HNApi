package com.example.HackerNews.Service;

import com.example.HackerNews.Entity.Comment;
import com.example.HackerNews.Entity.Story;
import com.example.HackerNews.Repository.StoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StoryService {

    @Autowired
    StoryRepository storyRepository;

    @Autowired
    RestTemplate restTemplate;

    public List<Story> getLatestStories() {

        long unixTime = Instant.now().getEpochSecond();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
        int[] itemIds = restTemplate.exchange("https://hacker-news.firebaseio.com/v0/topstories.json",
                HttpMethod.GET, entity, int[].class).getBody();
        List<Story> storyList = Arrays.stream(itemIds).mapToObj(s -> {
                return restTemplate.exchange("https://hacker-news.firebaseio.com/v0/item/" + s + ".json",
                        HttpMethod.GET, entity, Story.class).getBody();
                }).toList();
        List<Story> stories = storyList.stream().filter(r -> r.getTime() >= unixTime - 15000)
                .sorted(Comparator.comparingInt(Story::getScore).reversed())
                .limit(10)
                .collect(Collectors.toList());
        storyRepository.saveAll(stories);
        return stories;
    }


    public List<Story> getLastStories() {
        return storyRepository.findAll();
    }

}
