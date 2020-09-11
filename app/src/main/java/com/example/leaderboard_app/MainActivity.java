package com.example.leaderboard_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.leaderboard_app.fragments.LearningLeardersFragment;
import com.example.leaderboard_app.fragments.SkillsIQFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "name of class";
    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    private LearningLeardersFragment mLearningLeardersFragment;
    private SkillsIQFragment mSkillsIQFragment;

    //widgets
    private Button mSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Tab setup
        mViewPager = findViewById(R.id.view_pager);
        mTabLayout = findViewById(R.id.tab_layout);

        mLearningLeardersFragment = new LearningLeardersFragment();
        mSkillsIQFragment = new SkillsIQFragment();

        mTabLayout.setupWithViewPager(mViewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 0);

        viewPagerAdapter.addFragment(mLearningLeardersFragment, "Learning Leaders");
        viewPagerAdapter.addFragment(mSkillsIQFragment, "Skill IQ Leaders")
        ;
        mViewPager.setAdapter(viewPagerAdapter);

        mTabLayout.getTabAt(0).setIcon(R.drawable.fire);
        mTabLayout.getTabAt(1).setIcon(R.drawable.star);

        //submit button
        mSubmit = findViewById(R.id.submit_action);
        Log.d(TAG, "onCreate: started.");

        mSubmit.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Log.d(TAG, "onClick: clicked.");

        if(view.getId() == R.id.submit_action){
            Log.d(TAG, "onClick: logging in the user.");

            Intent intent = new Intent(MainActivity.this, Submit_Form.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    }




    private static class ViewPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragments = new ArrayList<>();
        private List<String> fragmentTitle = new ArrayList<>();

        public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        public void addFragment(Fragment fragment, String title) {
            fragments.add(fragment);
            fragmentTitle.add(title);
        }


        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitle.get(position);
        }
    }
}