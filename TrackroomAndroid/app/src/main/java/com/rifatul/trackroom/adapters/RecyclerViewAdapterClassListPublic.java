package com.rifatul.trackroom.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.rifatul.trackroom.ActivityCourseDetailed;
import com.rifatul.trackroom.AppPrefs;
import com.rifatul.trackroom.R;
import com.rifatul.trackroom.interfaces.ApiInterface;
import com.rifatul.trackroom.models.ItemClass;

import java.util.List;

public class RecyclerViewAdapterClassListPublic extends RecyclerView.Adapter<RecyclerViewAdapterClassListPublic.ViewHolder> {
    private final List<ItemClass> itemClassDataPublic;
    Context context;

    public ApiInterface getApi () { return AppPrefs.getInstance(context).getApi(); }

    public String getAccess() { return AppPrefs.getInstance(context).getAccess(); }

    public String getRefresh() { return AppPrefs.getInstance(context).getRefresh(); }

    //public void showAssignmentList(int classPk, String classTitle, String classCode) { AppPrefs.getInstance(context).showAssignmentList(classPk, classTitle, classCode); }

    public RecyclerViewAdapterClassListPublic(List<ItemClass> itemClassesPublic) {
        this.itemClassDataPublic = itemClassesPublic;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_class_public,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemClassPublic = itemClassDataPublic.get(position);
        holder.className.setText(itemClassDataPublic.get(position).getTitle());
        holder.classRating.setText(itemClassDataPublic.get(position).getRatings());
        holder.classCategory.setText(itemClassDataPublic.get(position).getClassCategory());
        holder.classDescription.setText(itemClassDataPublic.get(position).getDescription());
        holder.creatorName.setText(itemClassDataPublic.get(position).getCreator());




        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detailedCourseView = new Intent(view.getContext(), ActivityCourseDetailed.class);
                view.getContext().startActivity(detailedCourseView);

            }
        });
    }

    @Override
    public int getItemCount() {
        return itemClassDataPublic.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView className;
        TextView classRating;
        TextView classCategory;
        TextView classDescription;
        TextView creatorName;
        CardView cardViewLinearLayout;
        ItemClass itemClassPublic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            className = itemView.findViewById(R.id.item_recom_name);
            classRating = itemView.findViewById(R.id.item_recom_tv_rating);
            classCategory = itemView.findViewById(R.id.item_recom_tv_category);
            classDescription = itemView.findViewById(R.id.item_recom_description);
            creatorName = itemView.findViewById(R.id.item_recom_tv_creator);
            cardViewLinearLayout = itemView.findViewById(R.id.layout_Recom_Card);

        }
    }

}
