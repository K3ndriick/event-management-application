package com.fit2081.fit2081_a1;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fit2081.fit2081_a1.provider.CategoryClass;

import java.util.ArrayList;
import java.util.List;

public class CategoryRecyclerAdapter extends RecyclerView.Adapter<CategoryRecyclerAdapter.categoryCustomViewHolder>{

    List<CategoryClass> categoryData;

    public void setCategoryData(List<CategoryClass> categoryData) {
        this.categoryData = categoryData;
    }


    @NonNull
    @Override
    public categoryCustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View categoryView = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_card_layout, parent, false);
        categoryCustomViewHolder categoryViewHolder = new categoryCustomViewHolder(categoryView);
        return categoryViewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull categoryCustomViewHolder holder, int position) {
        holder.tvCategoryId.setText(categoryData.get(position).getCategoryId());
//        holder.tvCategoryId.setText(String.valueOf(categoryData.get(position).getCategoryId()));
        holder.tvCategoryName.setText(categoryData.get(position).getCategoryName());
        holder.tvCategoryEventCount.setText(String.valueOf(categoryData.get(position).getCategoryEventCount()));
        if (categoryData.get(position).getCategoryAvailability()) {
            holder.tvCategoryAvailability.setText("Active");
        }
        else {
            holder.tvCategoryAvailability.setText("Inactive");
        }

        holder.categoryCardView.setOnClickListener(v -> {
            String selectedEventLocation = categoryData.get(position).getEventLocation();
            String eventLocationCategoryName = categoryData.get(position).getCategoryName();

            Context context = holder.categoryCardView.getContext();
            Intent intent = new Intent(context, GoogleMapActivity.class);
            intent.putExtra("eventLocation", selectedEventLocation);
            intent.putExtra("eventLocationCategoryName", eventLocationCategoryName);
            context.startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        if (this.categoryData != null) { // if data is not null
            return this.categoryData.size(); // then return the size of ArrayList
        }

        // else return zero if data is null
        return 0;
    }



    public class categoryCustomViewHolder extends RecyclerView.ViewHolder {

        public TextView tvCategoryId;
        public TextView tvCategoryName;
        public TextView tvCategoryEventCount;
        public TextView tvCategoryAvailability;
        public View categoryCardView;

        public categoryCustomViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryCardView = itemView;
            tvCategoryId = itemView.findViewById(R.id.textViewCategoryId);
            tvCategoryName = itemView.findViewById(R.id.textViewCategoryName);
            tvCategoryEventCount = itemView.findViewById(R.id.textViewCategoryEventCount);
            tvCategoryAvailability = itemView.findViewById(R.id.textViewCategoryAvailability);
        }
    }
}
