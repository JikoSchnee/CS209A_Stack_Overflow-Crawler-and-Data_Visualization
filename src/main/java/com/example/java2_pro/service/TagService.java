package com.example.java2_pro.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.java2_pro.entity.normal.RelatedTagConnect;
import com.example.java2_pro.entity.normal.Tag;
import com.example.java2_pro.entity.special.RelatedTagWithPopularity;
import com.example.java2_pro.entity.special.TagWithPopularity;
import com.example.java2_pro.mapper.QuestionTagConnectMapper;
import com.example.java2_pro.mapper.RelatedTagConnectMapper;
import com.example.java2_pro.mapper.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {
    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private QuestionTagConnectMapper questionTagConnectMapper;

    @Autowired
    private RelatedTagConnectMapper relatedTagConnectMapper;
    // 返回所有的Tag
    public List<Tag> getAllTag(){
        return tagMapper.selectList(new QueryWrapper<>());
    }
    // 返回十个课上相关Tag的热度
    public List<TagWithPopularity> getTenTagPopularity() {
        return tagMapper.getTenTagPopularity();
    }

    // 返回所有的tag，相关问题总数、回答总数、浏览总数以及三者的总合
    public List<TagWithPopularity> getTagPopularityAll(){
        return tagMapper.getTagPopularityAll();
    }

    // 返回某一Tag，及其相关问题总数、回答总数、浏览总数以及三者的总合
    public TagWithPopularity getTagPopularityCertain(String tagName){
        return tagMapper.getTagPopularityCertain(tagName);
    }

    // 返回某一Tag相关的Tag
    public List<String> getRelevantTagAll(String tagName){
        return tagMapper.getRelevantTagAll(tagName);
    }

    // 返回某一Tag相关的Tag及其相关次数
    public List<RelatedTagConnect> getRelevantTagWithPopAll(String tagName) {
//        return tagMapper.getRelevantTagWithPopAll(tagName);
        QueryWrapper<RelatedTagConnect> wrapper = new QueryWrapper<RelatedTagConnect>();
        wrapper.eq("tag",tagName);
        return relatedTagConnectMapper.selectList(wrapper);
    }
    // 返回某一Tag相关的Tag的前number个
    public List<String> getRelevantTagPart(String tagName, int number){
        List<String> temp = tagMapper.getRelevantTagAll(tagName);
        if (number <= temp.size()){
            return temp.subList(0,number);
        }else {
            return temp;
        }
    }

}
