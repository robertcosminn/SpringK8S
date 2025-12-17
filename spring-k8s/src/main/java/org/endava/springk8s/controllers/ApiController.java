package org.endava.springk8s.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@RestController
public class ApiController {

    @GetMapping("/api/info")
    public Map<String, Object> getInfo() {
        return Map.of(
                "app", "spring-k8s-demo",
                "version", "1.0.0",
                "timestamp", Instant.now().toString()
        );
    }

    @GetMapping("/api/random")
    public Map<String, Object> getRandom() {
        int number = ThreadLocalRandom.current().nextInt(1, 100);
        return Map.of(
                "randomNumber", number,
                "uuid", UUID.randomUUID().toString(),
                "message", "Kubernetes test endpoint"
        );
    }
}

