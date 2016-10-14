package com.example;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.hazelcast.HazelcastAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
// disable Hazelcast Auto Configuration, and use JCache configuration
@EnableAutoConfiguration(exclude = {HazelcastAutoConfiguration.class})
@EnableCaching
public class Application {

	public static void main(String[] args) {
		//SpringApplication.run(Application.class, args);
		
		// from https://github.com/hazelcast/hazelcast-code-samples/blob/master/hazelcast-integration/springboot-caching-jcache/src/main/java/com/hazelcast/springboot/caching/BootifulMember.java
		new SpringApplicationBuilder()
        	.profiles("member")
        	.sources(Application.class)
        	.run(args);
	}
	
//	@Bean
//	Configuration
}
