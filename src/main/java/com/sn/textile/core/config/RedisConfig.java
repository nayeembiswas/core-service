//package com.sn.textile.core.config;
//
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
//import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.GenericToStringSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//
//import com.sn.textile.core.base.constants.ENV;
//import com.sn.textile.core.base.util.EnvConfig;
//
//import java.time.Duration;
//
//
//@Configuration
//public class RedisConfig {
//
//    @Bean(name="jedisConnectionFactory")
//    JedisConnectionFactory jedisConnectionFactory() {
//
//        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
//        redisStandaloneConfiguration.setHostName( EnvConfig.getString(ENV.REDIS.HOST, "194.233.76.227") );
//        redisStandaloneConfiguration.setPort( EnvConfig.getInt(ENV.REDIS.PORT, 6379) );
//        redisStandaloneConfiguration.setDatabase(0);
//
//        JedisClientConfiguration.JedisClientConfigurationBuilder jedisClientConfiguration = JedisClientConfiguration.builder();
//        jedisClientConfiguration.connectTimeout(Duration.ofSeconds(60));// 60s connection timeout
//
//        JedisConnectionFactory jedisConFactory = new JedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfiguration.build());
//
//        return jedisConFactory;
//    }
//
//
//    @Bean(name="redisTemplate")
//    RedisTemplate<Object, Object> redisTemplate(@Qualifier("jedisConnectionFactory") RedisConnectionFactory cf) {
//        final RedisTemplate<Object, Object> template =  new RedisTemplate<Object, Object>();
//        template.setConnectionFactory( cf );
//        template.setKeySerializer( new StringRedisSerializer() );
//        template.setHashValueSerializer( new GenericToStringSerializer<Object >( Object.class ) );
//        template.setValueSerializer( new GenericToStringSerializer< Object >( Object.class ) );
//        return template;
//    }
//
//}
