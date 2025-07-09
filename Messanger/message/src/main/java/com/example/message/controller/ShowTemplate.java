package com.example.message.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ShowTemplate {

    @GetMapping("/chat")
    public String index() {
        return "Chat";
    }

}
