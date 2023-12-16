package com.example.java2_pro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.java2_pro.service.TagService;

@RestController
@RequestMapping("api/related_topic")
public class RelatedTopicController {
    @Autowired
    private TagService tagService;

//    @GetMapping("all")
//    public List<Map<String, Object>> tagByPop(){
//
//    }
}
