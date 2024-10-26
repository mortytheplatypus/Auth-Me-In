package com.mortytheplatypus.auth_me_in_backend.controller;

import com.mortytheplatypus.auth_me_in_backend.model.dto.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class PublicController {

    @GetMapping("/hello")
    public ResponseEntity<Response> hello() {
        return ResponseEntity.ok(new Response(true, "Hello darkness, my old friend"));
    }

}
