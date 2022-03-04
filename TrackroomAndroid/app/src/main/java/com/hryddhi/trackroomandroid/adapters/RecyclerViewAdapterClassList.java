package com.hryddhi.trackroomandroid.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hryddhi.trackroomandroid.AppPrefs;
import com.hryddhi.trackroomandroid.interfaces.ApiInterface;
import com.hryddhi.trackroomandroid.models.ItemClass;
import com.hryddhi.trackroomandroid.R;

import java.util.List;

public class RecyclerViewAdapterClassList extends RecyclerView.Adapter<RecyclerViewAdapterClassList.ViewHolder>{
    private final List<ItemClass> itemClassData;
    Context context;

    public ApiInterface getApi () { return AppPrefs.getInstance(context).getApi(); }

    public String getAccess() { return AppPrefs.getInstance(context).getAccess(); }

    public String getRefresh() { return AppPrefs.getInstance(context).getRefresh(); }

    public void showAssignmentList(int classPk, String classTitle, String classCode) { AppPrefs.getInstance(context).showAssignmentList(classPk, classTitle, classCode); }

    public RecyclerViewAdapterClassList(List<ItemClass> itemClassData) {
        this.itemClassData = itemClassData;
    }

    @NonNull
    @Override
    public RecyclerViewAdapterClassList.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_class,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterClassList.ViewHolder holder, int position) {
        holder.itemClass = itemClassData.get(position);
        holder.className.setText(itemClassData.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView className;
        TextView classDescription;
        LinearLayout cardViewLinearLayout;
        ItemClass itemClass;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
