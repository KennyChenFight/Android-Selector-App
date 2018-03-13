package com.example.kenny.selector;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public ImageButton ibPeople, ibActivity, ibEat, ibYesOrNo, ibNumber, ibLocation;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hideBar();
        findViews();
        setJumpToPage();
        changeClickColor();
    }
    // 找到元件
    public void findViews() {
        ibPeople = (ImageButton) findViewById(R.id.people);
        ibActivity = (ImageButton) findViewById(R.id.activity);
        ibEat = (ImageButton) findViewById(R.id.eat);
        ibNumber = (ImageButton) findViewById(R.id.number);
        ibYesOrNo = (ImageButton) findViewById(R.id.yes);
        ibLocation = (ImageButton) findViewById(R.id.location);
        fragmentManager = getSupportFragmentManager();
        Logger.d();
    }
    // 當按下或放開時會改變透明度
    public void changeClickColor() {
        ibPeople.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {  //按下的時候改變背景及顏色
                    ibPeople.setImageAlpha(120);
                }
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {  //起來的時候恢復背景與顏色
                    ibPeople.setImageAlpha(255);
                }
                return false;
            }
        });

        ibActivity.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {  //按下的時候改變背景及顏色
                    ibActivity.setImageAlpha(120);
                }
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {  //起來的時候恢復背景與顏色
                    ibActivity.setImageAlpha(255);
                }
                return false;
            }
        });

        ibEat.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {  //按下的時候改變背景及顏色
                    ibEat.setImageAlpha(120);
                }
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {  //起來的時候恢復背景與顏色
                    ibEat.setImageAlpha(255);
                }
                return false;
            }
        });

        ibYesOrNo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {  //按下的時候改變背景及顏色
                    ibYesOrNo.setImageAlpha(120);
                }
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {  //起來的時候恢復背景與顏色
                    ibYesOrNo.setImageAlpha(255);
                }
                return false;
            }
        });

        ibNumber.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {  //按下的時候改變背景及顏色
                    ibNumber.setImageAlpha(120);
                }
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {  //起來的時候恢復背景與顏色
                    ibNumber.setImageAlpha(255);
                }
                return false;
            }
        });

        ibLocation.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {  //按下的時候改變背景及顏色
                    ibLocation.setImageAlpha(120);
                }
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {  //起來的時候恢復背景與顏色
                    ibLocation.setImageAlpha(255);
                }
                return false;
            }
        });
        Logger.d();
    }
    //隱藏標題
    public void hideBar() {
        getSupportActionBar().hide();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Logger.d();
    }
    //跳轉六大主題的頁面
    public void setJumpToPage() {
        ibPeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                PeopleFragment peopleFragment = new PeopleFragment();
                if(!peopleFragment.isAdded()) {
                    fragmentTransaction.add(R.id.topicContainer, peopleFragment, peopleFragment.getClass().getName());
                    fragmentTransaction.addToBackStack(peopleFragment.getClass().getName());
                }
                else {
                    fragmentTransaction.show(peopleFragment);
                }

                fragmentTransaction.commit();
                fragmentManager.executePendingTransactions();
            }
        });

        ibActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ActivityFragment activityFragment = new ActivityFragment();
                if(!activityFragment.isAdded()) {
                    fragmentTransaction.add(R.id.topicContainer, activityFragment, activityFragment.getClass().getName());
                    fragmentTransaction.addToBackStack(activityFragment.getClass().getName());
                }
                else {
                    fragmentTransaction.show(activityFragment);
                }
                fragmentTransaction.commit();
                fragmentManager.executePendingTransactions();
            }
        });

        ibEat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                EatFragment eatFragment = new EatFragment();
                if(!eatFragment.isAdded()) {
                    fragmentTransaction.add(R.id.topicContainer, eatFragment, eatFragment.getClass().getName());
                    fragmentTransaction.addToBackStack(eatFragment.getClass().getName());
                }
                else {
                    fragmentTransaction.show(eatFragment);
                }
                fragmentTransaction.commit();
                fragmentManager.executePendingTransactions();
            }
        });

        ibYesOrNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                YesOrNoFragment yesOrNoFragment = new YesOrNoFragment();
                if(!yesOrNoFragment.isAdded()) {
                    fragmentTransaction.add(R.id.topicContainer, yesOrNoFragment, yesOrNoFragment.getClass().getName());
                    fragmentTransaction.addToBackStack(yesOrNoFragment.getClass().getName());
                }
                else {
                    fragmentTransaction.show(yesOrNoFragment);
                }
                fragmentTransaction.commit();
                fragmentManager.executePendingTransactions();
            }
        });

        ibNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                NumberFragment numberFragment = new NumberFragment();
                if(!numberFragment.isAdded()) {
                    fragmentTransaction.add(R.id.topicContainer, numberFragment, numberFragment.getClass().getName());
                    fragmentTransaction.addToBackStack(numberFragment.getClass().getName());
                }
                else {
                    fragmentTransaction.show(numberFragment);
                }
                fragmentTransaction.commit();
                fragmentManager.executePendingTransactions();
            }
        });

        ibLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                LocationFragment locationFragment = new LocationFragment();
                if(!locationFragment.isAdded()) {
                    fragmentTransaction.add(R.id.topicContainer, locationFragment, locationFragment.getClass().getName());
                    fragmentTransaction.addToBackStack(locationFragment.getClass().getName());
                }
                else {
                    fragmentTransaction.show(locationFragment);
                }
                fragmentTransaction.commit();
                fragmentManager.executePendingTransactions();
            }
        });
        Logger.d();
    }
}
