package com.example.instagram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.instagram.adapters.ProfilePostAdapter;
import com.example.instagram.models.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class UserProfileActivity extends AppCompatActivity {

    private final static String TAG = "UserProfileActivity";

    private ProfilePostAdapter adapter;
    private List<Post> posts;

    private ImageView ivProfileImage;
    private TextView tvUsernameProfile;
    private RecyclerView rvUserPosts;
    private TextView tvPosts;

    private ParseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        user = getIntent().getExtras().getParcelable("user");

        posts = new ArrayList<>();

        ivProfileImage = findViewById(R.id.ivProfileImage);
        tvUsernameProfile = findViewById(R.id.tvUsernameProfile);
        rvUserPosts = findViewById(R.id.rvUserPosts);
        tvPosts = findViewById(R.id.tvPosts);

        adapter = new ProfilePostAdapter(this, posts);
        rvUserPosts.setAdapter(adapter);
        rvUserPosts.setLayoutManager(new GridLayoutManager(this, 3));

        tvUsernameProfile.setText(user.getUsername());

        loadProfileImage();

        queryPosts();
    }

    private void loadProfileImage() {
        Glide.with(this)
                .load(user.getParseFile("image").getUrl())
                .transform(new RoundedCorners(200))
                .into(ivProfileImage);
    }

    private void queryPosts() {
        // specify what type of data we want to query - Post.class
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        // include data referred by user key
        query.include(Post.KEY_USER);
        query.whereEqualTo(Post.KEY_USER, user);

        // limit query to latest 20 items
        //query.setLimit(20);
        // order posts by creation date (newest first)
        query.addDescendingOrder("createdAt");
        // start an asynchronous call for posts
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> _posts, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Fail getting posts", e);
                    return;
                }

                posts.addAll(_posts);
                tvPosts.setText("" + posts.size());
                adapter.notifyDataSetChanged();
            }
        });
    }
}