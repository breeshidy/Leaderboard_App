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

public class SkillIqAdapter extends RecyclerView.Adapter<SkillIqAdapter.LeaningLeadersViewHolder>{

    ArrayList<LearningLearders> mLeadersArrayList;
    public SkillIqAdapter(ArrayList<LearningLearders> learningLeaders){
        this.mLeadersArrayList = learningLeaders;
    }

    @NonNull
    @Override
    public LeaningLeadersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflated the book list item
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.list_item_layout_skill_iq, parent,false);
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
        TextView skillIq_country;



        public LeaningLeadersViewHolder(@NonNull View itemView) {
            super(itemView);
            full_name = itemView.findViewById(R.id.tv_name_skill_iq);
            skillIq_country = itemView.findViewById(R.id.tv_skillIq_country);
        }

        public void bind (LearningLearders learningLeaders){
            String text = "Skill IQ Score";
            String iq= learningLeaders.getScore() + " " + text + ", " + learningLeaders.getCountry() ;

//            showImage(learningLeaders.getImageUrl());
            full_name.setText(learningLeaders.getName());
            skillIq_country.setText(iq);
        }
    }
}
