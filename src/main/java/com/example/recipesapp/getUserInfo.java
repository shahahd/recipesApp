package com.example.recipesapp;

public class getUserInfo {
    private String UserId;
    private String Time;
    private String image;
    private String recipeName;
    public getUserInfo(){

    }

    public String getImage() {
        return image;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public getUserInfo(String UserId,  String Time, String image, String recipeName) {
        this.UserId = UserId;
        this.Time=Time;
        this.image=image;
        this.recipeName=recipeName;
    }
    public String getUserId(){
        return UserId;
    }
    public String getTime() {
        return Time;
    }

}
