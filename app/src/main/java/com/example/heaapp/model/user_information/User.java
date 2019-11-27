package com.example.heaapp.model.user_information;

import io.realm.RealmObject;

public class User extends RealmObject {
    String userId;
    String username;
    String imageURl;

    public User() {
    }

    public User(String userId, String username, String imageURl) {
        this.userId = userId;
        this.username = username;
        this.imageURl = imageURl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImageURl() {
        return imageURl;
    }

    public void setImageURl(String imageURl) {
        this.imageURl = imageURl;
    }
}
