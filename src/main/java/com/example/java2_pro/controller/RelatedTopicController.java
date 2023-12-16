package com.example.java2_pro.controller;

import com.example.java2_pro.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.java2_pro.service.tagService;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("api/related_topic")
public class RelatedTopicController {
    @Autowired
    private tagService tagService;

//    @GetMapping("all")
//    public List<Map<String, Object>> tagByPop(){
//
//    }
}
