package com.example.java2_pro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.java2_pro.service.questionService;
@RestController
@RequestMapping("/api/question")
public class questionController {
    @Autowired
    private questionService questionService;
}
