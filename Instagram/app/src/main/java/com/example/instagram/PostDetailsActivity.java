package com.example.instagram;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.instagram.models.Post;

public class PostDetailsActivity extends AppCompatActivity {

    private Post post;

    private TextView tvUsername;
    private ImageView ivImage;
    private TextView tvDescription;
    private TextView tvTime;
    private TextView tvUsername2;
    private ImageView ivProfileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        post = getIntent().getExtras().getParcelable(Post.class.getSimpleName());

        tvUsername = findViewById(R.id.tvUsernameDetails);
        ivImage = findViewById(R.id.ivImageDetails);
        tvDescription = findViewById(R.id.tvDescriptionDetails);
        tvTime = findViewById(R.id.tvTimeDetails);
        tvUsername2 = findViewById(R.id.tvUsernameDetails2);
        ivProfileImage = findViewById(R.id.ivUserDetails);

        bind();
    }

    private void bind() {
        tvUsername.setText(post.getUser().getUsername());
        tvUsername2.setText(post.getUser().getUsername());
        Glide.with(this)
                .load(post.getUser().getParseFile("image").getUrl())
                .transform(new RoundedCorners(200))
                .into(ivProfileImage);

        Glide.with(this)
                .load(post.getImage().getUrl())
                .into(ivImage);
        tvDescription.setText(post.getDescription());
        tvTime.setText(post.getCalculatedTimeAgo());
    }
}