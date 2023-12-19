package com.example.java2_pro.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.example.java2_pro.entity.normal.Question;
import com.example.java2_pro.entity.special.QuestionWithTags;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface QuestionMapper extends BaseMapper<Question> {
    @Deprecated
    List<Map<String, Object>> selectQuestionWithTagList(@Param(Constants.WRAPPER) Wrapper<Question> wrapper);

    List<QuestionWithTags> getQuestionsWithKeywords(@Param("keyword") String keyword);

    List<Integer> getQuestionsCountByKeyword(@Param("keyword") String keyword);
}
