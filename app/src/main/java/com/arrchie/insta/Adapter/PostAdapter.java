package com.arrchie.insta.Adapter;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arrchie.insta.Model.Post;
import com.arrchie.insta.Model.User;
import com.arrchie.insta.R;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.util.List;
import java.util.Objects;

import static android.app.PendingIntent.getActivity;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private Context mcontext;
    private List<Post> mPost;
    private FirebaseUser firebaseUser;
   private View view;

    public PostAdapter(Context mcontext, List<Post> mPost) {
        this.mcontext = mcontext;
        this.mPost = mPost;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view= LayoutInflater.from(mcontext).inflate(R.layout.post_item,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        Post post = mPost.get(position);
        //System.out.println(post.getPostimage());

        Glide.with(mcontext).load(post.getPostimage()).into(holder.post_image);
        System.out.println("Post Image  "+post.getPostimage());
        System.out.println(post.getPostid());
        System.out.println(post.getPublisher());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if(Objects.equals(post.getDescription(), "")){
                holder.description.setVisibility(View.GONE);
            }
            else{
                holder.description.setVisibility(View.VISIBLE);
                holder.description.setText(post.getDescription());
            }
        }
        publisherInfo(holder.image_profile,holder.username,holder.publisher,post.getPublisher());
    }



    @Override
    public int getItemCount() {
            return mPost.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

     public ImageView image_profile,post_image,like,comment,save;
     public TextView username,likes,publisher,comments,description;

     public ViewHolder(@NonNull View itemView) {
         super(itemView);
         image_profile=itemView.findViewById(R.id.image_profile);
         post_image=itemView.findViewById(R.id.post_image);
         like=itemView.findViewById(R.id.like);
         comment=itemView.findViewById(R.id.comment);
         save=itemView.findViewById(R.id.save);
         username=itemView.findViewById(R.id.username);
         likes=itemView.findViewById(R.id.likes);
         publisher=itemView.findViewById(R.id.publisher);
         comments=itemView.findViewById(R.id.comments);
         description=itemView.findViewById(R.id.description);
     }
 }
 private void publisherInfo(final ImageView image_profile, final TextView username, final TextView publisher, final String userid ){

     DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Users").child(userid);

     reference.addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            User user = dataSnapshot.getValue(User.class);
             if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

                 Glide.with(mcontext).load(Objects.requireNonNull(user).getImageurl()).into(image_profile);
                // Glide.with(getActivity()).load(new File(downloadUrl)).into(image_profile);

                 username.setText(user.getUsername());
                 publisher.setText(user.getUsername());
             }

         }

         @Override
         public void onCancelled(@NonNull DatabaseError databaseError) {

         }
     });
 }
}
