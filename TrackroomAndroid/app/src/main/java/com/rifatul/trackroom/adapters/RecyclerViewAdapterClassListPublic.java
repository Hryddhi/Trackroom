package com.rifatul.trackroom.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.rifatul.trackroom.ActivityCourseDetailed;
import com.rifatul.trackroom.ActivityTrackroom;
import com.rifatul.trackroom.AppPrefs;
import com.rifatul.trackroom.R;
import com.rifatul.trackroom.interfaces.ApiInterface;
import com.rifatul.trackroom.models.ItemClass;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecyclerViewAdapterClassListPublic extends RecyclerView.Adapter<RecyclerViewAdapterClassListPublic.ViewHolder> {
    private  List<ItemClass> itemClassDataPublic;
    Context context;
    AppCompatButton btn_add_create_public;

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
                int classPk = itemClassDataPublic.get(holder.getAdapterPosition()).getPk();
                Log.d("Classroom pk on class list recycler view : ", String.valueOf(classPk));
                btn_add_create_public = view.findViewById(R.id.btn_add_create_public);
                btn_add_create_public.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("TAG", "onClick: Button");

                        Call<ResponseBody> joinPublicClass = getApi().joinPublicClass(getAccess(), classPk);

                        joinPublicClass.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                Log.d("TAG", "Response " + response.code());

                                if (response.isSuccessful()) {
                                    Intent startTrackroom = new Intent(view.getContext(), ActivityTrackroom.class);
                                    view.getContext().startActivity(startTrackroom);
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Log.d("TAG", "onFailure: " + t.toString());
                                //Toast.makeText(getContext(), "Server Not Found", Toast.LENGTH_SHORT).show();

                            }
                        });


                    }
                });



            }
        });
    }

    @Override
    public int getItemCount() {
        return itemClassDataPublic.size();
    }

    /*public void filterList(ArrayList<ItemClass> filteredList) {
        itemClassDataPublic = filteredList;
        notifyDataSetChanged();
    }*/

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
