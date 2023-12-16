package com.example.java2_pro.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.java2_pro.entity.Question;
import com.example.java2_pro.entity.QuestionTagConnect;
import com.example.java2_pro.entity.Tag;
import com.example.java2_pro.mapper.QuestionMapper;
import com.example.java2_pro.mapper.QuestionTagConnectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class questionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionTagConnectMapper questionTagConnectMapper;

    public Question questionById(int questionID) {
        return questionMapper.selectById(questionID);
    }

    public List<Map<String, Object>> questionIdContainTag(String tag){
        QueryWrapper<QuestionTagConnect> wrapper = new QueryWrapper<>();
        wrapper.select("question_id").eq("tag_name",tag);
//        List<Map<String, Object>> temp = questionTagConnectMapper.selectMaps(wrapper);
//        temp.get(2).get("question_id");
        return questionTagConnectMapper.selectMaps(wrapper);
    }

}
