package com.adityaedu.themathwizz.Adapters;

public class RecentScoreList {
    private String title, score, mastery;

    public RecentScoreList(String title, String score, String mastery) {
        this.title = title;
        this.score = score;
        this.mastery = mastery;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getMastery() {
        return mastery;
    }

    public void setMastery(String mastery) {
        this.mastery = mastery;
    }

}
