package com.example.java2_pro.controller;

import com.example.java2_pro.entity.special.QuestionWithTags;
import com.example.java2_pro.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/question")
public class QuestionController {
    private static final Logger LOGGER = LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    private QuestionService questionService;

    // 获得某关键词相关的问题
    @GetMapping("/keyword/{keyword}")
    public List<QuestionWithTags> getQuestionByKeyword(@PathVariable String keyword) {
        try {
            LOGGER.info("function: \"getQuestionByKeyword\": " + keyword);
            return questionService.getQuestionsWithKeywords(keyword);
        } catch (Exception e) {
            LOGGER.error("Error in getQuestionByKeyword", e);
            return null; // or handle the error in an appropriate way
        }
    }

    // 获得所有问题的记数
    @GetMapping("/count")
    public List<Integer> getQuestionCount() {
        try {
            LOGGER.info("function: \"getQuestionCount\"");
            return questionService.getQuestionsCount();
        } catch (Exception e) {
            LOGGER.error("Error in getQuestionCount", e);
            return null; // or handle the error in an appropriate way
        }
    }

    // 获得某关键词相关问题的记数
    @GetMapping("/count/keyword/{keyword}")
    public List<Integer> getQuestionCountByKeyword(@PathVariable String keyword) {
        try {
            LOGGER.info("function: \"getQuestionCountByKeyword\": " + keyword);
            return questionService.getQuestionsCountByKeyword(keyword);
        } catch (Exception e) {
            LOGGER.error("Error in getQuestionCountByKeyword", e);
            return null; // or handle the error in an appropriate way
        }
    }
}
