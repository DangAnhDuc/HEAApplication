package com.example.heaapp.api;


import com.example.heaapp.model.Post;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface TestApiServicers {
    @GET("posts")
    Observable<List<Post>> getPost();
}
