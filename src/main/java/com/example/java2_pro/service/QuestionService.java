package com.example.java2_pro.service;

import com.example.java2_pro.entity.normal.Question;
import com.example.java2_pro.mapper.QuestionMapper;
import com.example.java2_pro.mapper.QuestionTagConnectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionTagConnectMapper questionTagConnectMapper;

    public Question questionById(int questionID) {
        return questionMapper.selectById(questionID);
    }


}
