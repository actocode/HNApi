package com.example.HackerNews.Service;

import com.example.HackerNews.Entity.Comment;
import com.example.HackerNews.Entity.Story;
import com.example.HackerNews.Repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    RestTemplate restTemplate;

    public List<Comment> getLatestComments(int id) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
        Story story = restTemplate.exchange("https://hacker-news.firebaseio.com/v0/item/" + id + ".json",
                HttpMethod.GET, entity, Story.class).getBody();
        int[] kids = story.getKids();
        List<Comment> comments = Arrays.stream(kids).mapToObj(i -> {
            return restTemplate.exchange("https://hacker-news.firebaseio.com/v0/item/" + i + ".json",
                    HttpMethod.GET, entity, Comment.class).getBody();
        }).toList();

        List<Comment> commentList = comments.stream()
                .sorted(Comparator.comparingInt(Comment::getKidsLength).reversed())
                .limit(10)
                .collect(Collectors.toList());
        commentRepository.saveAll(commentList);

        return commentList;
    }
}
