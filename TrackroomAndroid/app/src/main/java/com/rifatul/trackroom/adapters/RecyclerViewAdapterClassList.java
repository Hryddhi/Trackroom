package com.rifatul.trackroom.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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

public class RecyclerViewAdapterClassList extends RecyclerView.Adapter<RecyclerViewAdapterClassList.ViewHolder> {
    private final List<ItemClass> itemClassData;
    Context context;

    public ApiInterface getApi () { return AppPrefs.getInstance(context).getApi(); }

    public String getAccess() { return AppPrefs.getInstance(context).getAccess(); }

    public String getRefresh() { return AppPrefs.getInstance(context).getRefresh(); }

    public void showAssignmentList(int classPk, String classTitle, String classCode) { AppPrefs.getInstance(context).showAssignmentList(classPk, classTitle, classCode); }

    public RecyclerViewAdapterClassList(List<ItemClass> itemClasses) {
        this.itemClassData = itemClasses;
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
        holder.itemClass = itemClassData.get(position);
        holder.className.setText(itemClassData.get(position).getTitle());
        holder.classType.setText(itemClassData.get(position).getClassType());
        holder.classCategory.setText(itemClassData.get(position).getClassCategory());
        holder.creatorName.setText(itemClassData.get(position).getCreator());




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
        return itemClassData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView className;
        TextView creatorName;
        TextView classType;
        TextView classCategory;
        CardView cardViewLinearLayout;
        ItemClass itemClass;

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
