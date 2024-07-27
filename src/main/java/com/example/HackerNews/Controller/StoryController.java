package com.example.HackerNews.Controller;

import com.example.HackerNews.Dao.CommentDAO;
import com.example.HackerNews.Dao.StoryDAO;
import com.example.HackerNews.Service.CachingService;
import com.example.HackerNews.Service.CommentService;
import com.example.HackerNews.Service.StoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v0")
public class StoryController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    StoryService storyService;

    @Autowired
    CommentService commentService;

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

//    @CacheEvict(value = "topstories", allEntries = true)
//    @Scheduled(cron = "0 0/1 * * * *")
//    @DeleteMapping("/deleteStories")
//    public void deleteTopStories() {
//
//    }

    @GetMapping("{id}/comments")
    public List<CommentDAO> getTopComments(@PathVariable int id) {
        return commentService.getLatestComments(id).stream().map(post -> modelMapper.map(post, CommentDAO.class))
                .collect(Collectors.toList());
    }


}
