package com.example.HackerNews.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class CachingService {

    @Autowired
    CacheManager cacheManager;

    @Scheduled(fixedDelay = 60000)
    public void evictAllCaches() {
        cacheManager.getCache("topstories").clear();
    }
}
