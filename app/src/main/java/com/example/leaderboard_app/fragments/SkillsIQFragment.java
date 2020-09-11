package com.example.leaderboard_app.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.leaderboard_app.modals.LearningLearders;
import com.example.leaderboard_app.R;
import com.example.leaderboard_app.adapters.SkillIqAdapter;
import com.example.leaderboard_app.utils.ApiUtil;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SkillsIQFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SkillsIQFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View mFragmentView;
    private View mMainView;
    private RecyclerView mRecyclerView;
    private TextView mErrorMesssge;

    public SkillsIQFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SkillsIQFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SkillsIQFragment newInstance(String param1, String param2) {
        SkillsIQFragment fragment = new SkillsIQFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        try{
            URL url = ApiUtil.buildUrl("skilliq");
            new SkillsIQFragment.QueryTask().execute(url);
        }
        catch (Exception e){
            e.printStackTrace();
            Log.d(e.getMessage(), "onCreate: error for getting JSon ");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mFragmentView = inflater.inflate(R.layout.fragment_skills_iq, container, false);
        mMainView = inflater.inflate(R.layout.activity_main,container,false);

        //get access to views
        mRecyclerView = mFragmentView.findViewById(R.id.rv_skill_iq);
        mErrorMesssge = mMainView.findViewById(R.id.error_message);

        initialize(mRecyclerView);

        return mFragmentView;
    }

    public void initialize(RecyclerView recyclerView){
        //creating the layout manager for the recycler view
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
    }

    public class QueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... urls) {
            URL searchUrl = urls[0];
            String searchResult = null;
            try {
                searchResult = ApiUtil.getJson(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return searchResult;
        }

        @Override
        protected void onPostExecute(String s) {

            if (s == null) {
                mRecyclerView.setVisibility(View.INVISIBLE);
                // mProgressBar.setVisibility(View.VISIBLE);
            } else {
                mRecyclerView.setVisibility(View.VISIBLE);
//                mProgressBar.setVisibility(View.GONE);

            }

            ArrayList<LearningLearders> learners = ApiUtil.getLearnersFromJson(s);

            SkillIqAdapter skillIqAdapter = new SkillIqAdapter(learners);
            mRecyclerView.setAdapter(skillIqAdapter);


        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // mProgressBar.setVisibility(View.VISIBLE);
        }
    }
}