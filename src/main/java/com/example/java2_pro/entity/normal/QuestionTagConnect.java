package com.example.java2_pro.entity.normal;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("connection_tag_and_question")
public class QuestionTagConnect {
    @TableField("tag")
    private String tag;

    @TableField("question_id")
    private int questionId;
}
