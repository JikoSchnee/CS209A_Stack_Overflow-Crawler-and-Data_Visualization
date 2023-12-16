package com.example.java2_pro.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.java2_pro.entity.QuestionTagConnect;
import com.example.java2_pro.entity.RelatedTagConnect;
import com.example.java2_pro.entity.Tag;
import com.example.java2_pro.mapper.QuestionTagConnectMapper;
import com.example.java2_pro.mapper.RelatedTagConnectMapper;
import com.example.java2_pro.mapper.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class tagService {
    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private QuestionTagConnectMapper questionTagConnectMapper;

    @Autowired
    private RelatedTagConnectMapper relatedTagConnectMapper;
    public List<Tag> getAllTag(){
        return tagMapper.selectList(new QueryWrapper<>());
    }

    //计算tag在采到的Question中出现了几次
    public List<Map<String, Object>> tagCounts() {
        QueryWrapper<QuestionTagConnect> wrapper = new QueryWrapper<>();
        wrapper.select("tag_name", "count(*) as count").groupBy("tag_name");
        return questionTagConnectMapper.selectMaps(wrapper);
    }
    //Question对应的tag组
    public List<Map<String, Object>> tagGroups() {
        QueryWrapper<QuestionTagConnect> wrapper = new QueryWrapper<>();
        wrapper
            .select("question_id", "string_agg(tag_name, ',' ORDER BY tag_name) as tag_group")
            .groupBy("question_id");
        return questionTagConnectMapper.selectMaps(wrapper);
    }

    public List<QuestionTagConnect> tagsByQuestionId(int questionID) {
        QueryWrapper<QuestionTagConnect> wrapper = new QueryWrapper<>();
        wrapper.eq("question_id", questionID);
        return questionTagConnectMapper.selectList(wrapper);
    }
}
