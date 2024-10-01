package com.fit2081.fit2081_a1;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fit2081.fit2081_a1.provider.EventClass;

import java.util.ArrayList;
import java.util.List;

public class EventRecyclerAdapter extends RecyclerView.Adapter<EventRecyclerAdapter.eventCustomViewHolder>{

    List<EventClass> eventData;

    public void setEventData (List<EventClass> eventData) {
        this.eventData = eventData;
    }


    @NonNull
    @Override
    public eventCustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View eventView = LayoutInflater.from(parent.getContext()).inflate(R.layout.events_card_layout, parent, false);
        eventCustomViewHolder eventViewHolder = new eventCustomViewHolder(eventView);
        return eventViewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull eventCustomViewHolder holder, int position) {
        holder.tvEventId.setText(eventData.get(position).getEventId());
//        holder.tvEventId.setText(String.valueOf(eventData.get(position).getEventId()));
        holder.tvEventName.setText(eventData.get(position).getEventName());
        holder.tvEventCategoryId.setText(eventData.get(position).getEventCategoryId());
        holder.tvEventTicketsAvailable.setText(String.valueOf(eventData.get(position).getEventTicketsAvailable()));
        if (eventData.get(position).getEventAvailability()) {
            holder.tvEventAvailability.setText("Active");
        }
        else {
            holder.tvEventAvailability.setText("Inactive");
        }

        holder.eventCardView.setOnClickListener(v -> {
            String selectedEventName = eventData.get(position).getEventName();

            Context context = holder.eventCardView.getContext();
            Intent intent = new Intent(context, EventGoogleResult.class);
            intent.putExtra("googleSearchEventName", selectedEventName);
            context.startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        if (this.eventData != null) {
            return this.eventData.size();
        }
        return 0;
    }



    public class eventCustomViewHolder extends RecyclerView.ViewHolder {

        public TextView tvEventId;
        public TextView tvEventName;
        public TextView tvEventCategoryId;
        public TextView tvEventTicketsAvailable;
        public TextView tvEventAvailability;
        public View eventCardView;

        public eventCustomViewHolder(@NonNull View itemView) {
            super(itemView);
            eventCardView = itemView;
            tvEventId = itemView.findViewById(R.id.textViewEventId);
            tvEventName = itemView.findViewById(R.id.textViewEventName);
            tvEventCategoryId = itemView.findViewById(R.id.textViewEventCategoryId);
            tvEventTicketsAvailable = itemView.findViewById(R.id.textViewEventTicketsAvailable);
            tvEventAvailability = itemView.findViewById(R.id.textViewEventAvailability);
        }
    }
}
