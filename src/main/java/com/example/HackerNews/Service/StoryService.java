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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StoryService {

    @Autowired
    StoryRepository storyRepository;

    @Autowired
    RestTemplate restTemplate;

    List<Story> storyList;

    public List<Story> getLatestStories() {
        long unixTime = Instant.now().getEpochSecond();
        System.out.println(unixTime);
        storyList = new ArrayList<>();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
        int[] itemIds = restTemplate.exchange(
                "https://hacker-news.firebaseio.com/v0/topstories.json", HttpMethod.GET, entity, int[].class
        ).getBody();
        for (int itemId : itemIds) {
            Story story = restTemplate.exchange(
                    "https://hacker-news.firebaseio.com/v0/item/" + itemId + ".json",
                    HttpMethod.GET,
                    entity,
                    Story.class
            ).getBody();
            storyList.add(story);
        }
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

    public List<Comment> getLatestComments(int id) {
        List<Comment> comments = new ArrayList<>();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
        Story story = restTemplate.exchange(
                "https://hacker-news.firebaseio.com/v0/item/" + id + ".json",
                HttpMethod.GET,
                entity,
                Story.class
        ).getBody();
        int[] kids = story.getKids();
        for (int i : kids) {
            Comment comment = restTemplate.exchange(
                    "https://hacker-news.firebaseio.com/v0/item/" + i + ".json",
                    HttpMethod.GET,
                    entity,
                    Comment.class
            ).getBody();
            comments.add(comment);
        }
        System.out.println(new Comment().getKidsLength());
        return comments.stream()
                .sorted(Comparator.comparingInt(Comment::getKidsLength).reversed())
                .limit(10)
                .collect(Collectors.toList());
    }
}
