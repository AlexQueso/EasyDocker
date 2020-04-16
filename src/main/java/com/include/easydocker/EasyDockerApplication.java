package com.include.easydocker;

import com.hazelcast.config.Config;
import com.hazelcast.config.JoinConfig;
import com.include.easydocker.utils.WebSocketHandler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.session.hazelcast.config.annotation.web.http.EnableHazelcastHttpSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

@SpringBootApplication
@EnableWebSocket
@EnableCaching
@EnableHazelcastHttpSession
public class EasyDockerApplication implements WebSocketConfigurer {

	private static final Log LOG = LogFactory.getLog(EasyDockerApplication.class);

	public static String cacheName = "cache";

	@Autowired
	private ApplicationContext applicationContext;

	public static void main(String[] args) {

		Properties props = new Properties();
		props.put("rabbit.reply.queue", "app.docker.reply.id." + UUID.randomUUID().toString());

		new SpringApplicationBuilder(EasyDockerApplication.class)
				.properties(props).run(args);

//		SpringApplication.run(EasyDockerApplication.class, args);
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

	@Bean
	public Config config() {

		Config config = new Config();

		JoinConfig joinConfig = config.getNetworkConfig().getJoin();

		joinConfig.getMulticastConfig().setEnabled(true);
		//joinConfig.getTcpIpConfig().setEnabled(true).setMembers(Collections.singletonList("127.0.0.1"));

		return config;
	}
}
