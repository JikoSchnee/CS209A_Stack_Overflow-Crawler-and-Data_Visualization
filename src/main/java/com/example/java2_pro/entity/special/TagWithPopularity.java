package com.example.java2_pro.entity.special;

public class TagWithPopularity {
    private String topic;
    private Integer totalQuestionCount;
    private Integer totalAnswerCount;
    private Integer totalViewCount;
    private Integer popularity;


    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Integer getTotalQuestionCount() {
        return totalQuestionCount;
    }

    public void setTotalQuestionCount(Integer totalQuestionCount) {
        this.totalQuestionCount = totalQuestionCount;
    }

    public Integer getTotalAnswerCount() {
        return totalAnswerCount;
    }

    public void setTotalAnswerCount(Integer totalAnswerCount) {
        this.totalAnswerCount = totalAnswerCount;
    }

    public Integer getTotalViewCount() {
        return totalViewCount;
    }

    public void setTotalViewCount(Integer totalViewCount) {
        this.totalViewCount = totalViewCount;
    }

    public Integer getPopularity() {
        return popularity;
    }

    public void setPopularity(Integer popularity) {
        this.popularity = popularity;
    }
}
