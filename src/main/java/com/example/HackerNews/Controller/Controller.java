package com.example.HackerNews.Controller;

import com.example.HackerNews.DAO.CommentDAO;
import com.example.HackerNews.DAO.StoryDAO;
import com.example.HackerNews.Service.CachingService;
import com.example.HackerNews.Service.StoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v0")
public class Controller {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    StoryService storyService;

    @Autowired
    CachingService cachingService;


    @Cacheable(value = "topstories")
    @GetMapping("/top-stories")
    public List<StoryDAO> getTopStories() {
        return storyService.getLatestStories().stream().map(post -> modelMapper.map(post, StoryDAO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/past-stories")
    public List<StoryDAO> getLastStories() {
        return storyService.getLastStories().stream().map(post -> modelMapper.map(post, StoryDAO.class))
                .collect(Collectors.toList());
    }

    @CacheEvict(value = "topstories", allEntries = true)
    @Scheduled(cron = "0 0/15 * * * ?")
    @DeleteMapping("/deleteStories")
    public void deleteTopStories() {

    }

    @GetMapping("{id}/comments")
    public List<CommentDAO> getTopComments(@PathVariable int id) {
        return storyService.getLatestComments(id).stream().map(post -> modelMapper.map(post, CommentDAO.class))
                .collect(Collectors.toList());
    }


}
