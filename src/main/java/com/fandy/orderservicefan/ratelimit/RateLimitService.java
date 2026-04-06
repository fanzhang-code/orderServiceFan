package com.fandy.orderservicefan.ratelimit;

import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class RateLimitService {

    private static final int MAX_REQUESTS = 5;
    private static final long WINDOW_MILLIS = 10_000; // 10 seconds

    private final ConcurrentHashMap<String, Counter> counters = new ConcurrentHashMap<>();

    public boolean allowRequest(String clientKey) {
        long now = System.currentTimeMillis();

        Counter counter = counters.computeIfAbsent(clientKey, key -> new Counter(0, now));

        synchronized (counter) {
            // if window expired, reset
            if (now - counter.windowStart >= WINDOW_MILLIS) {
                counter.requestCount = 0;
                counter.windowStart = now;
            }

            if (counter.requestCount >= MAX_REQUESTS) {
                return false;
            }

            counter.requestCount++;
            return true;
        }
    }

    private static class Counter {
        private int requestCount;
        private long windowStart;

        public Counter(int requestCount, long windowStart) {
            this.requestCount = requestCount;
            this.windowStart = windowStart;
        }
    }
}