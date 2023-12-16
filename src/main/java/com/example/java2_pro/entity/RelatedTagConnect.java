package com.example.java2_pro.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("connection_tag_and_tag")
public class RelatedTagConnect {
    @TableField("related_tag")
    private String related_tag;

    @TableField("tag")
    private String tag;
}
