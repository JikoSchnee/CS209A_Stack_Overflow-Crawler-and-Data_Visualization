package com.example.java2_pro.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.java2_pro.entity.special.BugWithPopularity;
import com.example.java2_pro.service.QuestionService;
import org.apache.ibatis.javassist.compiler.SyntaxError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
@RestController
@RequestMapping("/api/bug")
public class BugController {
    @Autowired
    private QuestionService questionService;

    // 获取单个bug热度
    @GetMapping("/popularity/keyword/{keyword}")
    public List<Integer> bugPopularity(@PathVariable String keyword){
        return questionService.getQuestionsCountByKeyword(keyword);
    }

    // 获取多个bug热度
    @GetMapping("/popularity/all")
    public List<BugWithPopularity> bugPopularityAll(){
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
        for (int i = 0;i<bugList.length;i++){
            result.add(new BugWithPopularity(bugList[i], this.questionService.getQuestionsCountByKeyword(keyWord[i]).get(0)));
        }
        result.sort(Comparator.comparingInt(BugWithPopularity::getPopularity).reversed());
        return result;
    }

    @GetMapping("/popularity/three/{keyword}")
    public List<Integer> bugPopularityThree(@PathVariable String keyword){
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
        if (keyword.equals("SE")){
            int temp = 0;
            for (int i = 0;i<=1;i++){
                temp+=this.questionService.getQuestionsCountByKeyword(keyWord[i]).get(0);
            }
            ArrayList<Integer> result = new ArrayList<>();
            result.add(temp);
            return result;
        }else if (keyword.equals("FE")){
            int temp = 0;
            for (int i = 2;i<=6;i++){
                temp+=this.questionService.getQuestionsCountByKeyword(keyWord[i]).get(0);
            }
            ArrayList<Integer> result = new ArrayList<>();
            result.add(temp);
            return result;
        }else {
            int temp = 0;
            for (int i = 7;i<=14;i++){
                temp+=this.questionService.getQuestionsCountByKeyword(keyWord[i]).get(0);
            }
            ArrayList<Integer> result = new ArrayList<>();
            result.add(temp);
            return result;
        }

    }
}
