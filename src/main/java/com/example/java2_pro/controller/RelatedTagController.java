package com.example.java2_pro.controller;

import com.example.java2_pro.entity.normal.RelatedTagConnect;
import com.example.java2_pro.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/relatedTag")
public class RelatedTagController {

    @Autowired
    TagService tagService;
    // 返回某个Tag相关的Tag及其热度
    @GetMapping("/{tag}")
    public List<RelatedTagConnect> getRelevantTagWithPopAll(@PathVariable String tag){
//        LOGGER.info("function: \"getRelevantTagWithPopAll\"");
        List<RelatedTagConnect> temp = tagService.getRelevantTagWithPopAll(tag);
        return tagService.getRelevantTagWithPopAll(tag);
    }
}
