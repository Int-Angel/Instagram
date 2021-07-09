package com.example.instagram.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.instagram.PostDetailsActivity;
import com.example.instagram.R;
import com.example.instagram.models.Post;
import com.parse.ParseFile;

import java.util.List;

public class ProfilePostAdapter extends RecyclerView.Adapter<ProfilePostAdapter.ViewHolder> {

    private List<Post> post;
    private Context context;

    public ProfilePostAdapter(Context context, List<Post> post) {
        this.post = post;
        this.context = context;
    }

    @NonNull
    @Override
    public ProfilePostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post_image, parent, false);
        return new ProfilePostAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfilePostAdapter.ViewHolder holder, int position) {
        holder.bind(post.get(position));
    }

    @Override
    public int getItemCount() {
        return post.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView ivPostImageUser;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivPostImageUser = itemView.findViewById(R.id.ivPostImageUser);

            itemView.setOnClickListener(this);
        }

        public void bind(Post post) {

            ParseFile image = post.getImage();
            if (image != null) {
                ivPostImageUser.setVisibility(View.VISIBLE);
                Glide.with(context)
                        .load(image.getUrl())
                        .into(ivPostImageUser);

            } else {
                //Toast.makeText(context, "NO IMAGE", Toast.LENGTH_SHORT).show();
                ivPostImageUser.setVisibility(View.GONE);
            }
        }

        private void openDetails() {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Intent intent = new Intent(context, PostDetailsActivity.class);
                intent.putExtra(Post.class.getSimpleName(), post.get(position));
                context.startActivity(intent);
            }
        }

        @Override
        public void onClick(View v) {
            openDetails();
        }
    }
}
