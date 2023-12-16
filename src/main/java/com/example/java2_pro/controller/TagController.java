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
@RequestMapping("/api/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    // 返回所有的Tag与其热度
    @GetMapping("/popularity/all")
    public List<TagWithPopularity> getTagPopularityAllQuery(){
        return tagService.getTagPopularityAll();
    }

    // 返回number个Tag与其热度
    @GetMapping("/popularity/all/{number}")
    public List<TagWithPopularity> getTagPopularityPartQuery(@PathVariable int number){
        List<TagWithPopularity> allTag = tagService.getTagPopularityAll();
        if (number <= allTag.size()){
            return allTag.subList(0,number);
        }else {
            return allTag;
        }
    }

    // 返回某个Tag及其热度
    @GetMapping("/popularity/{tag}")
    public TagWithPopularity getTagPopularityQuery(@PathVariable String tag){
        return tagService.getTagPopularityCertain(tag);
    }

    // 返回某个Tag相关的Tag
    @GetMapping("/relevant/{tag}")
    public List<String> getRelevantTagAll(@PathVariable String tag){
        return tagService.getRelevantTagAll(tag);
    }

    // 返回某个Tag相关的前number个Tag
    @GetMapping("/relevant/{tag}/{number}")
    public List<String> getRelevantTagPart(@PathVariable String tag, @PathVariable int number){
        return tagService.getRelevantTagPart(tag,number);
    }
}
