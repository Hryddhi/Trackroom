package com.rifatul.trackroom.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rifatul.trackroom.ActivityDetailedPost;
import com.rifatul.trackroom.AppPrefs;
import com.rifatul.trackroom.R;
import com.rifatul.trackroom.interfaces.ApiInterface;
import com.rifatul.trackroom.models.ItemAssignments;
import com.rifatul.trackroom.models.ItemComments;
import com.rifatul.trackroom.models.User;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecyclerViewAdapterCommentList extends RecyclerView.Adapter<RecyclerViewAdapterCommentList.ViewHolder> {

    Context context;
    CircleImageView imgProfile;

    public ApiInterface getApi () { return AppPrefs.getInstance(context).getApi(); }

    public String getAccess() { return AppPrefs.getInstance(context).getAccess(); }

    public String getRefresh() { return AppPrefs.getInstance(context).getRefresh(); }

    //public void showAssignment(int taskPk, String taskName, String taskMaterialLink) { AppPrefs.getInstance(context).showAssignment(taskPk, taskName, taskMaterialLink); }

    private List<ItemComments> commentList;
    public RecyclerViewAdapterCommentList(List<ItemComments> assignmentList) { this.commentList = commentList; }

    @NonNull
    @Override
    public RecyclerViewAdapterCommentList.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterCommentList.ViewHolder holder, int position) {
        String taskName = commentList.get(position).getTitle();
        int taskPk = commentList.get(position).getPk();

        holder.setData(taskName);

        if (position%1 == 0)
            holder.cardViewConstraintLayout.setBackgroundResource(R.drawable.item_class_bg1);
        if (position%2 == 1)
            holder.cardViewConstraintLayout.setBackgroundResource(R.drawable.item_class_bg2);
        if (position%3 == 2)
            holder.cardViewConstraintLayout.setBackgroundResource(R.drawable.item_class_bg3);
        if (position%4 == 3)
            holder.cardViewConstraintLayout.setBackgroundResource(R.drawable.item_class_bg4);
        if (position%5 == 4)
            holder.cardViewConstraintLayout.setBackgroundResource(R.drawable.item_class_bg5);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* int postPk = commentList.get(holder.getAdapterPosition()).getPk();
                String postTitle = commentList.get(holder.getAdapterPosition()).getTitle();
                Log.d("Classroom pk on assignment list recycler view : ", String.valueOf(postPk));
                Log.d("Classroom title on assignment list recycler view : ", postTitle);
                Intent detailedPostView = new Intent(v.getContext(), ActivityDetailedPost.class);
                detailedPostView.putExtra("postPk", postPk);
                detailedPostView.putExtra("postTitle", postTitle);
                detailedPostView.putExtra("postDate", postDate);
                detailedPostView.putExtra("postDescription", postDescription);
                v.getContext().startActivity(detailedPostView);*/
                //showAssignment(taskPk, taskName, taskMaterialLink);
            }
        });

    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView commentTitle;
        CircleImageView imageProfileComment;
        ConstraintLayout cardViewConstraintLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            commentTitle = itemView.findViewById(R.id.post_comment_item);
            imageProfileComment = itemView.findViewById(R.id.img_Profile_Photo_comment_item);
            cardViewConstraintLayout = itemView.findViewById(R.id.layout_post_comment);

            imgProfile = imageProfileComment;

        }
        public void setData(String taskName) {
            commentTitle.setText(taskName);
            getAccountInfo();
        }
    }

    private void getAccountInfo() {
        Call<User> getUserInfo = getApi().account(getAccess());

        getUserInfo.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User u = response.body();
                    if (u.getProfileImage() != null)
                        displayProfilePicture(u.getProfileImage());
                }
                else {
                    Toast.makeText(context, "Unable To Get Profile Information.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("Function getUserInfo", "Failure");
                Log.d("Function getUserInfo Throwable T", t.toString());
            }
        });
    }

    private void displayProfilePicture(String url) {
        Log.d("Function displayProfilePicture name detailed post", url);
        Glide.with(context).load(url).into(imgProfile);
    }
}
