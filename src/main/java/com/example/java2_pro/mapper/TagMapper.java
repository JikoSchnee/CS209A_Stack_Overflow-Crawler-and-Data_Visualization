package com.example.java2_pro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.java2_pro.entity.normal.Tag;
import com.example.java2_pro.entity.special.TagWithPopularity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TagMapper extends BaseMapper<Tag> {
    List<TagWithPopularity> getTagPopularityAll();
    TagWithPopularity getTagPopularityCertain(@Param("tag_name") String tagName);
    List<String> getRelevantTagAll(@Param("tag_name") String tagName);
    List<TagWithPopularity> getTenTagPopularity();
}
