package com.v1gateway.entrace.config.Redis;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Collections;

public class RateLimitFilter  extends OncePerRequestFilter {
       private final StringRedisTemplate redisTemplate;
       public RateLimitFilter(StringRedisTemplate redisTemplate){
           this.redisTemplate=redisTemplate;
       }

       @Override
       public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException , IOException {
                String ip = request.getRemoteAddr();
                String key ="rate_limit" + ip;
           Long count = redisTemplate
                   .opsForValue()
                   .increment(key);

           if (count == 1) {

               redisTemplate.expire(
                       key,
                       Duration.ofMinutes(1)
               );
           }

           if (count > 5) {

               response.setStatus(429);

               response.getWriter()
                       .write("Too Many Requests");

               return;
           }

           filterChain.doFilter(request, response);
       }

       }
