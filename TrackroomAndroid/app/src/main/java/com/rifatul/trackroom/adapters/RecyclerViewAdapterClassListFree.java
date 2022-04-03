package com.rifatul.trackroom.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
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

public class RecyclerViewAdapterClassListFree extends RecyclerView.Adapter<RecyclerViewAdapterClassListFree.ViewHolder> {
    private final List<ItemClass> itemClassDataFree;
    Context context;

    public ApiInterface getApi () { return AppPrefs.getInstance(context).getApi(); }

    public String getAccess() { return AppPrefs.getInstance(context).getAccess(); }

    public String getRefresh() { return AppPrefs.getInstance(context).getRefresh(); }

    //public void showAssignmentList(int classPk, String classTitle, String classCode) { AppPrefs.getInstance(context).showAssignmentList(classPk, classTitle, classCode); }

    public RecyclerViewAdapterClassListFree(List<ItemClass> itemClassesFree) {
        this.itemClassDataFree = itemClassesFree;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_class,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemClassFree = itemClassDataFree.get(position);
        holder.className.setText(itemClassDataFree.get(position).getTitle());
        holder.classType.setText(itemClassDataFree.get(position).getClassType());
        holder.classCategory.setText(itemClassDataFree.get(position).getClassCategory());
        holder.creatorName.setText(itemClassDataFree.get(position).getCreator());
        //holder.classDescription.setText(itemClassDataFree.get(position).getDescription());





        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int classPk = itemClassDataFree.get(holder.getAdapterPosition()).getPk();
                String classTitle = itemClassDataFree.get(holder.getAdapterPosition()).getTitle();
                String classRating = itemClassDataFree.get(holder.getAdapterPosition()).getRatings();
                String classCategory = itemClassDataFree.get(holder.getAdapterPosition()).getClassCategory();
                String classDescription = itemClassDataFree.get(holder.getAdapterPosition()).getDescription();
                Log.d("Classroom pk on class list recycler view : ", String.valueOf(classPk));
                Log.d("Classroom title on class list recycler view : ", classTitle);
                Log.d("Classroom rating on class list recycler view : ", classRating);
                Log.d("Classroom category on class list recycler view : ", classCategory);
                Log.d("Classroom description on class list recycler view : ", classDescription);
                Intent detailedCourseView = new Intent(view.getContext(), ActivityCourseDetailed.class);
                detailedCourseView.putExtra("classPk", classPk);
                detailedCourseView.putExtra("classTitle", classTitle);
                detailedCourseView.putExtra("classRating", classRating);
                detailedCourseView.putExtra("classCategory", classCategory);
                detailedCourseView.putExtra("classDescription", classDescription);
                view.getContext().startActivity(detailedCourseView);

            }
        });
    }

    @Override
    public int getItemCount() {
        return itemClassDataFree.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView className;
        TextView classType;
        TextView classCategory;
        TextView creatorName;
        TextView classDescription;
        CardView cardViewLinearLayout;
        ItemClass itemClassFree;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            className = itemView.findViewById(R.id.item_class_name);
            classType = itemView.findViewById(R.id.item_tv_class_type);
            classCategory = itemView.findViewById(R.id.item_tv_category);
            creatorName = itemView.findViewById(R.id.item_tv_creator);
            cardViewLinearLayout = itemView.findViewById(R.id.layout_Class_Card);

        }
    }

}
