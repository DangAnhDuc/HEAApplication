package com.example.heaapp.model.user_information;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class CurrentUserInfo extends RealmObject {
    @PrimaryKey
    private long id;
    private long weight;
    private long height;
    private int age;
    private String sex;
}
