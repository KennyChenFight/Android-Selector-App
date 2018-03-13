package com.example.kenny.selector;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class LocationFragment extends Fragment {
    private ImageButton ibCatalog, restaurant, departmentStore, cafe, park, theater;
    private String searchType;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_location, null, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        setSearchButton();
        setJumpToCatalog();
        changeClickColor();
    }

    private void findViews(View view) {
        ibCatalog = view.findViewById(R.id.ibcatalog);
        restaurant = view.findViewById(R.id.restaurant);
        departmentStore = view.findViewById(R.id.departmentStore);
        cafe = view.findViewById(R.id.cafe);
        park = view.findViewById(R.id.park);
        theater = view.findViewById(R.id.theater);
    }

    private void setSearchButton() {
        restaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchType = "restaurant";
                savePreferences();
                Fragment locationFragment = LocationFragment.this;
                Fragment googleMapFragment = new GoogleMapFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                if (!googleMapFragment.isAdded()) {
                    transaction.add(R.id.topicContainer, googleMapFragment, googleMapFragment.getClass().getName());
                    transaction.addToBackStack(googleMapFragment.getClass().getName());
                    Logger.d();
                }
                else {
                    transaction.show(googleMapFragment);
                    Logger.d();
                }
                transaction.hide(locationFragment);
                transaction.commit();
            }
        });
        departmentStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchType = "department_store";
                savePreferences();
                Fragment locationFragment = LocationFragment.this;
                Fragment googleMapFragment = new GoogleMapFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                if (!googleMapFragment.isAdded()) {
                    transaction.add(R.id.topicContainer, googleMapFragment, googleMapFragment.getClass().getName());
                    transaction.addToBackStack(googleMapFragment.getClass().getName());
                    Logger.d();
                }
                else {
                    transaction.show(googleMapFragment);
                    Logger.d();
                }
                transaction.hide(locationFragment);
                transaction.commit();
            }
        });
        cafe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchType = "cafe";
                savePreferences();
                Fragment locationFragment = LocationFragment.this;
                Fragment googleMapFragment = new GoogleMapFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                if (!googleMapFragment.isAdded()) {
                    transaction.add(R.id.topicContainer, googleMapFragment, googleMapFragment.getClass().getName());
                    transaction.addToBackStack(googleMapFragment.getClass().getName());
                    Logger.d();
                }
                else {
                    transaction.show(googleMapFragment);
                    Logger.d();
                }
                transaction.hide(locationFragment);
                transaction.commit();
            }
        });
        park.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchType = "park";
                savePreferences();
                Fragment locationFragment = LocationFragment.this;
                Fragment googleMapFragment = new GoogleMapFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                if (!googleMapFragment.isAdded()) {
                    transaction.add(R.id.topicContainer, googleMapFragment, googleMapFragment.getClass().getName());
                    transaction.addToBackStack(googleMapFragment.getClass().getName());
                    Logger.d();
                }
                else {
                    transaction.show(googleMapFragment);
                    Logger.d();
                }
                transaction.hide(locationFragment);
                transaction.commit();
            }
        });
        theater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchType = "movie_theater";
                savePreferences();
                Fragment locationFragment = LocationFragment.this;
                Fragment googleMapFragment = new GoogleMapFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                if (!googleMapFragment.isAdded()) {
                    transaction.add(R.id.topicContainer, googleMapFragment, googleMapFragment.getClass().getName());
                    transaction.addToBackStack(googleMapFragment.getClass().getName());
                    Logger.d();
                }
                else {
                    transaction.show(googleMapFragment);
                    Logger.d();
                }
                transaction.hide(locationFragment);
                transaction.commit();
            }
        });
    }

    private void savePreferences() {
        SharedPreferences preferences = getActivity().getSharedPreferences("preferences", Context.MODE_PRIVATE);
        preferences.edit().putString("type",  searchType).apply();
        Logger.d();
    }

    // 跳回目錄
    public void setJumpToCatalog() {
        ibCatalog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.executePendingTransactions();
                fragmentManager.popBackStackImmediate();
                Logger.d();
            }
        });
    }
    // 當按下或放開時會改變透明度
    public void changeClickColor() {
        ibCatalog.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {  //按下的時候改變背景及顏色
                    ibCatalog.setImageAlpha(120);
                }
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {  //起來的時候恢復背景與顏色
                    ibCatalog.setImageAlpha(255);
                }
                return false;
            }
        });
        restaurant.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {  //按下的時候改變背景及顏色
                    restaurant.setImageAlpha(120);
                }
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {  //起來的時候恢復背景與顏色
                    restaurant.setImageAlpha(255);
                }
                return false;
            }
        });
        cafe.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {  //按下的時候改變背景及顏色
                    cafe.setImageAlpha(120);
                }
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {  //起來的時候恢復背景與顏色
                    cafe.setImageAlpha(255);
                }
                return false;
            }
        });
        departmentStore.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {  //按下的時候改變背景及顏色
                    departmentStore.setImageAlpha(120);
                }
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {  //起來的時候恢復背景與顏色
                    departmentStore.setImageAlpha(255);
                }
                return false;
            }
        });
        theater.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {  //按下的時候改變背景及顏色
                    theater.setImageAlpha(120);
                }
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {  //起來的時候恢復背景與顏色
                    theater.setImageAlpha(255);
                }
                return false;
            }
        });
        park.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {  //按下的時候改變背景及顏色
                    park.setImageAlpha(120);
                }
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {  //起來的時候恢復背景與顏色
                    park.setImageAlpha(255);
                }
                return false;
            }
        });
        Logger.d();
    }
}
