package com.example.HackerNews.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class CachingService {

    @Autowired
    CacheManager cacheManager;

    @Scheduled(cron = "0 0/15 * * * ?")
    public void evictAllCaches() {
        cacheManager.getCache("topstories").clear();
    }
}
