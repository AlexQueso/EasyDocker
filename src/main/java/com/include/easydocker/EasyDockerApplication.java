package com.include.easydocker;

import com.include.easydocker.utils.WebSocketHandler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@SpringBootApplication
@EnableWebSocket
@EnableCaching
public class EasyDockerApplication implements WebSocketConfigurer {

	private static final Log LOG = LogFactory.getLog(EasyDockerApplication.class);

	public static String cacheName = "cache";

	@Autowired
	private ApplicationContext applicationContext;

	public static void main(String[] args) {
		SpringApplication.run(EasyDockerApplication.class, args);
	}

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
		webSocketHandlerRegistry.addHandler(new WebSocketHandler(), "/ws");
	}

	@Bean
	public CacheManager cacheManager(){
		LOG.info("Activating cache...");
		return new ConcurrentMapCacheManager(cacheName);
	}
}
