package com.example.instagram.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.instagram.PostDetailsActivity;
import com.example.instagram.R;
import com.example.instagram.models.Post;
import com.parse.ParseFile;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private List<Post> post;
    private Context context;

    public PostAdapter(Context context, List<Post> post) {
        this.post = post;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.ViewHolder holder, int position) {
        holder.bind(post.get(position));
    }

    @Override
    public int getItemCount() {
        return post.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvUsernamePost;
        private ImageView ivImagePost;
        private TextView tvDescriptionPost;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvUsernamePost = itemView.findViewById(R.id.tvUsernamePost);
            ivImagePost = itemView.findViewById(R.id.ivImagePost);
            tvDescriptionPost = itemView.findViewById(R.id.tvDescriptionPost);

            itemView.setOnClickListener(this);
        }

        public void bind(Post post) {
            tvUsernamePost.setText(post.getUser().getUsername());
            tvDescriptionPost.setText(post.getDescription());

            ParseFile image = post.getImage();
            if (image != null) {
                ivImagePost.setVisibility(View.VISIBLE);
                Glide.with(context)
                        .load(image.getUrl())
                        .into(ivImagePost);

            } else {
                //Toast.makeText(context, "NO IMAGE", Toast.LENGTH_SHORT).show();
                ivImagePost.setVisibility(View.GONE);
            }
        }

        private void openDetails() {
            int position = getAdapterPosition();
            if(position != RecyclerView.NO_POSITION){
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
