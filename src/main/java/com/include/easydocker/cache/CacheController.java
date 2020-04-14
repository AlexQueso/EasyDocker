package com.include.easydocker.cache;


import com.include.easydocker.EasyDockerApplication;

import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Objects;

@RestController
public class CacheController {

    private final CacheManager cacheManager;

    public CacheController(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @GetMapping(value = "/cache")
    public Map<Object, Object> getCacheContent() {
        ConcurrentMapCacheManager cm = (ConcurrentMapCacheManager) cacheManager;
        ConcurrentMapCache cache = (ConcurrentMapCache) cm.getCache(EasyDockerApplication.cacheName);
        return Objects.requireNonNull(cache).getNativeCache();
    }

}
