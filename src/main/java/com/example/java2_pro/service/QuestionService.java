package com.example.java2_pro.service;

import com.example.java2_pro.entity.normal.Question;
import com.example.java2_pro.entity.special.QuestionWithTags;
import com.example.java2_pro.mapper.QuestionMapper;
import com.example.java2_pro.mapper.QuestionTagConnectMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;

    public List<QuestionWithTags> getQuestionsWithKeywords(String keyword){
        List<QuestionWithTags> temp1 = questionMapper.getQuestionsWithKeywords(keyword);
        return questionMapper.getQuestionsWithKeywords(keyword);
    }

    public List<Integer> getQuestionsCountByKeyword(String keyword){
        return questionMapper.getQuestionsCountByKeyword(keyword);
    }

    public List<Integer> getQuestionsCount(){
        return questionMapper.getQuestionsCountByKeyword(null);
    }
}
