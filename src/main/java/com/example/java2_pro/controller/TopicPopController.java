package com.example.java2_pro.controller;

import com.example.java2_pro.entity.special.TagWithPopularity;
import com.example.java2_pro.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/topic")
public class TopicPopController {
    @Autowired
    private TagService tagService;

    @GetMapping("/popularity/all")
    public List<TagWithPopularity> getTagPopularityAllQuery(){
        return tagService.getTagPopularityAll();
    }

//    @GetMapping("/popularity/{tag}")
//    public TagWithPopularity getTagPopularityQuery(@PathVariable String tag){
//
//    }
}
