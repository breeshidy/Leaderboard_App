package com.example.leaderboard_app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.leaderboard_app.modals.LearningLearders;
import com.example.leaderboard_app.R;

import java.util.ArrayList;

public class LearningLeadersAdapter extends RecyclerView.Adapter<LearningLeadersAdapter.LeaningLeadersViewHolder> {

    ArrayList<LearningLearders> mLeadersArrayList;
    public LearningLeadersAdapter(ArrayList<LearningLearders> learningLeaders){
        this.mLeadersArrayList = learningLeaders;
    }

    @NonNull
    @Override
    public LeaningLeadersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflated the book list item
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.list_item_leaders, parent,false);
        return new LeaningLeadersViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaningLeadersViewHolder holder, int position) {

        //get the leaders's current position and binds the view
        LearningLearders learningLeaders = mLeadersArrayList.get(position);
        holder.bind(learningLeaders);

    }

    @Override
    public int getItemCount() {
        return mLeadersArrayList.size();
    }

    public class LeaningLeadersViewHolder extends RecyclerView.ViewHolder{
        TextView full_name;
        TextView hours_country;



        public LeaningLeadersViewHolder(@NonNull View itemView) {
            super(itemView);
            full_name = itemView.findViewById(R.id.tv_name_leaders);
            hours_country = itemView.findViewById(R.id.hours_country);

        }

        public void bind (LearningLearders learningLeaders){
            String text = "learning hours";
            String time_country = learningLeaders.getHours() + " " + text+  ", " + learningLeaders.getCountry();

//            showImage(learners.getImageUrl());
            full_name.setText(learningLeaders.getName());
            hours_country.setText(time_country);

        }
    }
}
