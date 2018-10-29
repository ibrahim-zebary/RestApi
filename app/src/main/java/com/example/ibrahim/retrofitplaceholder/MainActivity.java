package com.example.ibrahim.retrofitplaceholder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.ibrahim.retrofitplaceholder.rest.api.JsonPlaceHolderApi;
import com.example.ibrahim.retrofitplaceholder.rest.model.Comment;
import com.example.ibrahim.retrofitplaceholder.rest.model.Post;
import com.example.ibrahim.retrofitplaceholder.singleton.JsonPlaceHolderRest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private JsonPlaceHolderApi api;
    private TextView jsonResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        jsonResult = findViewById(R.id.json_result);
//        getPosts();
//        getComments();
//        getMapPosts();
        createPost();
    }

    private void getPosts() {
        api = JsonPlaceHolderRest.getInstance().create(JsonPlaceHolderApi.class);
        Call<List<Post>> call = api.getPosts(1);
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful()) {
                    if (call != null) {
                        List<Post> posts = response.body();
                        for (Post post : posts) {
                            StringBuilder builder = new StringBuilder();
                            builder.append(String.valueOf(post.getUserId()) + "\n");
                            builder.append(String.valueOf(post.getId()) + "\n");
                            builder.append(post.getTitle() + "\n");
                            builder.append(post.getBody() + "\n");

                            jsonResult.append(builder.toString());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });
    }

    private void getComments() {
        api = JsonPlaceHolderRest.getInstance().create(JsonPlaceHolderApi.class);
        Call<List<Comment>> call = api.getComments(2);
        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (response.isSuccessful()) {
                    if (call != null) {
                        List<Comment> comments = response.body();
                        for (Comment comment : comments) {
                            StringBuilder builder = new StringBuilder();
                            builder.append("postId: " + String.valueOf(comment.getPostId()) + "\n");
                            builder.append("Id: " + String.valueOf(comment.getId()) + "\n");
                            builder.append("Name: " + comment.getName() + "\n");
                            builder.append("Email: " + comment.getEmail() + "\n");
                            builder.append("Body" + comment.getText() + "\n\n\n");

                            jsonResult.append(builder.toString());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {

            }
        });
    }

    private void getMapPosts() {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", 1);
        map.put("_sort", "id");
        map.put("_order", "desc");

        api = JsonPlaceHolderRest.getInstance().create(JsonPlaceHolderApi.class);
        Call<List<Post>> call = api.getMapPosts(map);
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (call != null) {
                    if (response.isSuccessful()) {
                        List<Post> posts = response.body();
                        for (Post post : posts) {
                            StringBuilder builder = new StringBuilder();
                            builder.append(String.valueOf(post.getUserId()) + "\n");
                            builder.append(String.valueOf(post.getId()) + "\n");
                            builder.append(post.getTitle() + "\n");
                            builder.append(post.getBody() + "\n\n\n");

                            jsonResult.append(builder.toString());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {

            }
        });

    }

    private void createPost() {
        api = JsonPlaceHolderRest.getInstance().create(JsonPlaceHolderApi.class);
        Post post = new Post(20, "MyTitle", "MyBody");
        Call<Post> postCall = api.createPost(post);
        postCall.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (call != null) {
                    if (response.isSuccessful()) {
                        Post postRequest = response.body();
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(postRequest.getUserId() + "\n");
                        stringBuilder.append(postRequest.getBody() + "\n");
                        stringBuilder.append(postRequest.getTitle() + "\n");
                        stringBuilder.append(postRequest.getId() + "\n");

                        jsonResult.setText(stringBuilder);
                    }
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

            }
        });
    }

}