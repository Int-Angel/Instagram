package com.example.instagram.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.instagram.R;
import com.example.instagram.models.Post;


public class PostDetailsFragment extends Fragment {

    private Post post;

    private TextView tvUsername;
    private ImageView ivImage;
    private TextView tvDescription;
    private TextView tvTime;

    public PostDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvUsername = view.findViewById(R.id.tvUsernameDetails);
        ivImage = view.findViewById(R.id.ivImageDetails);
        tvDescription = view.findViewById(R.id.tvDescriptionDetails);
        tvTime = view.findViewById(R.id.tvTimeDetails);

        bind();
    }

    private void bind() {
        tvUsername.setText(post.getUser().getUsername());
        Glide.with(this)
                .load(post.getImage().getUrl())
                .into(ivImage);
        tvDescription.setText(post.getDescription());
        tvTime.setText(post.getCalculatedTimeAgo());
    }
}