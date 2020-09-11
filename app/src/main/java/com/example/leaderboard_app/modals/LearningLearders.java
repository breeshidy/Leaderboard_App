package com.example.leaderboard_app.modals;

public class LearningLearders {
    public String name;
    public String hours;
    public String country;
    public String score;

    public LearningLearders() {

    }

    public LearningLearders(String name, String hours, String country, String score) {
        this.name = name;
        this.hours = hours;
        this.country = country;
        this.score = score;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

//    public String getImageUrl() {
//        return imageUrl;
//    }
//
//    public void setImageUrl(String imageUrl) {
//        this.imageUrl = imageUrl;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
