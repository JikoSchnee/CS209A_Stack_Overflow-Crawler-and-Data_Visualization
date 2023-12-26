package com.example.java2_pro.entity.special;

public class BugWithPopularity {

    private String bug;
    private int popularity;
    public BugWithPopularity(String name, int popularity){
        this.bug = name;
        this.popularity = popularity;
    }

    public String getBug() {
        return bug;
    }

    public void setBug(String bug) {
        this.bug = bug;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }
}
