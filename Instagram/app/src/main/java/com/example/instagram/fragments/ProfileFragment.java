package com.example.instagram.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.instagram.R;
import com.example.instagram.adapters.PostAdapter;
import com.example.instagram.adapters.ProfilePostAdapter;
import com.example.instagram.models.Post;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


public class ProfileFragment extends Fragment {

    private final static String TAG = "ProfileFragment";

    private ProfilePostAdapter adapter;
    private List<Post> posts;

    private ImageView ivProfileImage;
    private AppCompatButton btnChangeImage;
    private TextView tvUsernameProfile;
    private RecyclerView rvUserPosts;
    private TextView tvPosts;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        posts = new ArrayList<>();

        ivProfileImage = view.findViewById(R.id.ivProfileImage);
        btnChangeImage = view.findViewById(R.id.btnChangeImage);
        tvUsernameProfile = view.findViewById(R.id.tvUsernameProfile);
        rvUserPosts = view.findViewById(R.id.rvUserPosts);
        tvPosts = view.findViewById(R.id.tvPosts);

        adapter = new ProfilePostAdapter(getContext(), posts);
        rvUserPosts.setAdapter(adapter);
        rvUserPosts.setLayoutManager(new GridLayoutManager(getContext(), 3));

        tvUsernameProfile.setText(ParseUser.getCurrentUser().getUsername());


        loadProfileImage();


        btnChangeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImage();
            }
        });

        queryPosts();
    }

    private void changeImage() {

    }

    private void loadProfileImage() {
        Glide.with(getContext())
                .load(ParseUser.getCurrentUser().getParseFile("image").getUrl())
                .transform(new RoundedCorners(200))
                .into(ivProfileImage);
    }

    private void queryPosts() {
        // specify what type of data we want to query - Post.class
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        // include data referred by user key
        query.include(Post.KEY_USER);
        query.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());

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