package com.rifatul.trackroom.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

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
        holder.classDescription.setText(itemClassData.get(position).getDescription());




        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String classTitle = itemClassData.get(holder.getAdapterPosition()).getTitle();


                //Log.d("Classroom pk on class list recycler view", String.valueOf(classPk));

                //showAssignmentList(classPk, classTitle , classCode);

                //getAccountInfo();

                //Log.d("Recycle View Adapter", classDataC);
                //Log.d("Recycle View Adapter", classDataT);
                //Toast.makeText(view.getContext(), classDataC, Toast.LENGTH_SHORT).show();
                //Toast.makeText(context, "Sucessfull Toast", Toast.LENGTH_SHORT).show();

                //User u = retrieveUser();
                //startAssignmentActivity();


                /*if (u.getProfileType().equals("Teacher")) {
                    detailedClassroomView = new Intent(view.getContext(), ActivityDetailedClassroomViewTeacher.class);
                    detailedClassroomView.putExtra("classroomName", classDataC);
                    view.getContext().startActivity(detailedClassroomView);
                }
                else if (u.getProfileType().equals("Student")) {
                    detailedClassroomView = new Intent(view.getContext(), ActivityDetailedClassroomViewStudent.class);
                    detailedClassroomView.putExtra("classroomName", classDataC);
                    view.getContext().startActivity(detailedClassroomView);
                }
                else {
                    Toast.makeText(context, "Profile Type Not Set", Toast.LENGTH_SHORT).show();
                }*/

                /*detailedClassroomView = new Intent(view.getContext(), ActivityDetailedClassroomViewTeacher.class);
                detailedClassroomView.putExtra("classroomName", classDataC);
                view.getContext().startActivity(detailedClassroomView);*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemClassData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView className;
        TextView classDescription;
        TextView classCategory;
        TextView classCategoryTv;
        CardView cardViewLinearLayout;
        ItemClass itemClass;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            className = itemView.findViewById(R.id.item_class_name);
            classDescription = itemView.findViewById(R.id.item_class_description);
            classCategory = itemView.findViewById(R.id.item_course_category);
            classCategoryTv = itemView.findViewById(R.id.item_course_category);
            cardViewLinearLayout = itemView.findViewById(R.id.layout_Class_Card);

        }
    }

}
