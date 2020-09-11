package com.example.leaderboard_app.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.leaderboard_app.modals.LearningLearders;
import com.example.leaderboard_app.R;
import com.example.leaderboard_app.adapters.LearningLeadersAdapter;
import com.example.leaderboard_app.utils.ApiUtil;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;


public class LearningLeardersFragment extends Fragment {

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private View mFragmentView;
    private View mMainView;
    private RecyclerView mRecyclerView;
    private TextView mErrorMesssge;
    private ProgressBar mLoadingProgress;


    public LearningLeardersFragment() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LearningLeaders.
     */
    // TODO: Rename and change types and number of parameters
    public static LearningLeardersFragment newInstance(String param1, String param2) {
        LearningLeardersFragment fragment = new LearningLeardersFragment();
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
            URL url = ApiUtil.buildUrl("hours");
            new QueryTask().execute(url);
        }
        catch (Exception e){
            e.printStackTrace();
            Log.d(e.getMessage(), "onCreate: error for getting JSon ");
        }

}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layouts
        mFragmentView = inflater.inflate(R.layout.fragment_learning_learders, container, false);

        //get access to views
        mRecyclerView = mFragmentView.findViewById(R.id.rv_learning_leaders);
        mErrorMesssge = mFragmentView.findViewById(R.id.error_message);
        mLoadingProgress = mFragmentView.findViewById(R.id.data_loading);

        initialize(mRecyclerView);

        return mFragmentView;
    }

    public void initialize(RecyclerView recyclerView) {
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
        protected void onPreExecute() {
            super.onPreExecute();
//             mLoadingProgress.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            //mProgressBar.setVisibility(View.GONE);

            if (s == null) {
                mRecyclerView.setVisibility(View.INVISIBLE);
                // mErrorMessage.setVisibility(View.VISIBLE);
            } else {
                mRecyclerView.setVisibility(View.VISIBLE);
                // mErrorMessage.setVisibility(View.VISIBLE);

            }

            ArrayList<LearningLearders> learners = ApiUtil.getLearnersFromJson(s);

           LearningLeadersAdapter learningLeadersAdapter = new LearningLeadersAdapter(learners);
            mRecyclerView.setAdapter(learningLeadersAdapter);


        }

    }

}

