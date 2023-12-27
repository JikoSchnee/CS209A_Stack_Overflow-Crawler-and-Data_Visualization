package com.example.java2_pro.controller;

import com.example.java2_pro.entity.special.BugWithPopularity;
import com.example.java2_pro.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/bug")
public class BugController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BugController.class);

    @Autowired
    private QuestionService questionService;

    // 获取单个bug热度
    @GetMapping("/popularity/keyword/{keyword}")
    public List<Integer> bugPopularity(@PathVariable String keyword) {
        try {
            LOGGER.info("function: \"bugPopularity\": " + keyword);
            return questionService.getQuestionsCountByKeyword(keyword);
        } catch (Exception e) {
            LOGGER.error("Error in bugPopularity", e);
            return new ArrayList<>(); // or handle the error in an appropriate way
        }
    }

    // 获取多个bug热度
    @GetMapping("/popularity/all")
    public List<BugWithPopularity> bugPopularityAll() {
        try {
            LOGGER.info("function: \"bugPopularityAll\"");
            String[] bugList = new String[]{
                "IndentationError", "SyntaxError",
                "Segmentation Fault", "Stack Overflow", "Bus Error",
                "ZeroDivisionError", "TypeError",
                "ValueError", "FileNotFoundError", "IndexError", "KeyError", "AssertionError",
                "IOError", "RuntimeError", "NameError"
            };
            String[] keyWord = new String[]{
                "IndentationError", "SyntaxError",
                "segmentation-fault", "overflow", "bus-error",
                "ZeroDivisionError", "TypeError",
                "ValueError", "file-not-found", "IndexError", "KeyError", "AssertionError",
                "IOError", "RuntimeError", "NameError"
            };
            List<BugWithPopularity> result = new ArrayList<>();
            for (int i = 0; i < bugList.length; i++) {
                result.add(new BugWithPopularity(bugList[i], this.questionService.getQuestionsCountByKeyword(keyWord[i]).get(0)));
            }
            result.sort(Comparator.comparingInt(BugWithPopularity::getPopularity).reversed());
            return result;
        } catch (Exception e) {
            LOGGER.error("Error in bugPopularityAll", e);
            return new ArrayList<>(); // or handle the error in an appropriate way
        }
    }

    // 返回三个bug大类的热度
    @GetMapping("/popularity/three/{keyword}")
    public List<Integer> bugPopularityThree(@PathVariable String keyword) {
        try {
            LOGGER.info("function: \"bugPopularityThree\": " + keyword);
            String[] bugList = new String[]{
                "IndentationError", "SyntaxError",
                "Segmentation Fault", "Stack Overflow", "Bus Error",
                "ZeroDivisionError", "TypeError",
                "ValueError", "FileNotFoundError", "IndexError", "KeyError", "AssertionError",
                "IOError", "RuntimeError", "NameError"
            };
            String[] keyWord = new String[]{
                "IndentationError", "SyntaxError",
                "segmentation-fault", "overflow", "bus-error",
                "ZeroDivisionError", "TypeError",
                "ValueError", "file-not-found", "IndexError", "KeyError", "AssertionError",
                "IOError", "RuntimeError", "NameError"
            };
            if (keyword.equals("SE")) {
                int temp = 0;
                for (int i = 0; i <= 1; i++) {
                    temp += this.questionService.getQuestionsCountByKeyword(keyWord[i]).get(0);
                }
                ArrayList<Integer> result = new ArrayList<>();
                result.add(temp);
                return result;
            } else if (keyword.equals("FE")) {
                int temp = 0;
                for (int i = 2; i <= 6; i++) {
                    temp += this.questionService.getQuestionsCountByKeyword(keyWord[i]).get(0);
                }
                ArrayList<Integer> result = new ArrayList<>();
                result.add(temp);
                return result;
            } else {
                int temp = 0;
                for (int i = 7; i <= 14; i++) {
                    temp += this.questionService.getQuestionsCountByKeyword(keyWord[i]).get(0);
                }
                ArrayList<Integer> result = new ArrayList<>();
                result.add(temp);
                return result;
            }
        } catch (Exception e) {
            LOGGER.error("Error in bugPopularityThree", e);
            return new ArrayList<>(); // or handle the error in an appropriate way
        }
    }
}
