package com.example.java2_pro.entity.normal;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("connection_tag_and_tag")
public class RelatedTagConnect {
    @TableField("related_tag")
    private String relatedTag;

    @TableField("tag")
    private String tag;

    @TableField("count")
    private int count;

    public String getRelatedTag() {
        return relatedTag;
    }

    public void setRelatedTag(String relatedTag) {
        this.relatedTag = relatedTag;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
