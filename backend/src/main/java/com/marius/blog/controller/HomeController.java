package com.marius.blog.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HomeController {
    
    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> home() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Welcome to About Music in Movies Blog API");
        response.put("version", "1.0.0");
        response.put("swagger-ui", "/swagger-ui.html");
        response.put("api-docs", "/api-docs");
        response.put("h2-console", "/h2-console");
        return ResponseEntity.ok(response);
    }
}
