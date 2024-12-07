package com.design.order;

import io.jaegertracing.Configuration;
import io.opentracing.Tracer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;

@SpringBootApplication
@EnableCaching
@EnableMongoRepositories(basePackages = "com.design.order.repository")
@EnableElasticsearchRepositories(basePackages = "com.design.order.repository")
public class OrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderApplication.class, args);
	}

	@Bean
	public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
		RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
				.entryTtl(Duration.ofMinutes(60)) // Set TTL for cached entries
				.disableCachingNullValues()
				.serializeValuesWith(
						RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));

		return RedisCacheManager.builder(connectionFactory)
				.cacheDefaults(config)
				.build();
	}

	@Bean
	public Tracer getTracer() {
		Configuration.SamplerConfiguration samplerConfig = Configuration
				.SamplerConfiguration.fromEnv()
				.withType("const").withParam(1);
		Configuration.ReporterConfiguration reporterConfig = Configuration
				.ReporterConfiguration.fromEnv()
				.withLogSpans(true);
		Configuration config = new Configuration("order-service")
				.withSampler(samplerConfig)
				.withReporter(reporterConfig);
		return config.getTracer();
	}

}
