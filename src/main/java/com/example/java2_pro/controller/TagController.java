package com.example.java2_pro.controller;

import com.example.java2_pro.entity.normal.RelatedTagConnect;
import com.example.java2_pro.entity.special.TagWithPopularity;
import com.example.java2_pro.service.TagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tag")
public class TagController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TagController.class);

    @Autowired
    private TagService tagService;

    // 返回10个课上相关的Tag的热度
    @GetMapping("/popularity/ten")
    public List<TagWithPopularity> getTenTagPopularity() {
        try {
            LOGGER.info("function: \"getTenTagPopularity\"");
            return tagService.getTenTagPopularity();
        } catch (Exception e) {
            LOGGER.error("Error in getTenTagPopularity", e);
            return null; // or handle the error in an appropriate way
        }
    }

    // 返回所有的Tag与其热度
    @GetMapping("/popularity/all")
    public List<TagWithPopularity> getTagPopularityAllQuery() {
        try {
            LOGGER.info("function: \"getTagPopularityAllQuery\"");
            return tagService.getTagPopularityAll();
        } catch (Exception e) {
            LOGGER.error("Error in getTagPopularityAllQuery", e);
            return null; // or handle the error in an appropriate way
        }
    }

    // 返回number个Tag与其热度
    @GetMapping("/popularity/all/{number}")
    public List<TagWithPopularity> getTagPopularityPartQuery(@PathVariable int number) {
        try {
            LOGGER.info("function: \"getTagPopularityPartQuery\"");
            List<TagWithPopularity> allTag = tagService.getTagPopularityAll();
            if (number <= allTag.size()) {
                return allTag.subList(0, number);
            } else {
                return allTag;
            }
        } catch (Exception e) {
            LOGGER.error("Error in getTagPopularityPartQuery", e);
            return null; // or handle the error in an appropriate way
        }
    }

    // 返回某个Tag及其热度
    @GetMapping("/popularity/{tag}")
    public TagWithPopularity getTagPopularityQuery(@PathVariable String tag) {
        try {
            LOGGER.info("function: \"getTagPopularityQuery\": " + tag);
            tag = tag.toLowerCase();
            return tagService.getTagPopularityCertain(tag);
        } catch (Exception e) {
            LOGGER.error("Error in getTagPopularityQuery", e);
            return null; // or handle the error in an appropriate way
        }
    }

    // 返回某个Tag相关的Tag
    @GetMapping("/relevant/{tag}")
    public List<String> getRelevantTagAll(@PathVariable String tag) {
        try {
            LOGGER.info("function: \"getRelevantTagAll\": " + tag);
            tag = tag.toLowerCase();
            return tagService.getRelevantTagAll(tag);
        } catch (Exception e) {
            LOGGER.error("Error in getRelevantTagAll", e);
            return null; // or handle the error in an appropriate way
        }
    }

    // 返回某个Tag相关的前number个Tag
    @GetMapping("/relevant/{tag}/{number}")
    public List<String> getRelevantTagPart(@PathVariable String tag, @PathVariable int number) {
        try {
            LOGGER.info("function: \"getRelevantTagAll\": " + tag + " number = " + number);
            tag = tag.toLowerCase();
            return tagService.getRelevantTagPart(tag, number);
        } catch (Exception e) {
            LOGGER.error("Error in getRelevantTagPart", e);
            return null; // or handle the error in an appropriate way
        }
    }
}
