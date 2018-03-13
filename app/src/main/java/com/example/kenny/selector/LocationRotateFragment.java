package com.example.kenny.selector;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LocationRotateFragment extends Fragment {
    private ImageButton ibCatalog, ibNavigation;
    private ImageView ivMusicPlay, ivCircle, ivNeedle;
    private MediaPlayer mp;
    private Animation circleAm, needleAm;
    private int stopNum, startNum;
    private boolean isStartCircleAni = false,  isStartNeedleAni = false;
    private ArrayList<Place> restaurant;
    private ArrayList<Place> theater;
    private ArrayList<Place> park;
    private ArrayList<Place> store;
    private ArrayList<Place> cafe;
    private String searchType;
    private int number;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_location_rotate, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        hideBar();
        findViews(view);
        changeClickColor();
        setMusicClick();
        setJumpToCatalog();
        loadPreferences();
    }

    // 隱藏標題列及狀態列
    public void hideBar() {
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Logger.d();
    }
    // 找到元件
    public void findViews(View view) {
        ibCatalog = view.findViewById(R.id.catalog);
        ibNavigation = view.findViewById(R.id.ibNavigation);
        ivMusicPlay = view.findViewById(R.id.ivMusicPlay);
        ivCircle = view.findViewById(R.id.ivCircle);
        ivNeedle = view.findViewById(R.id.needle);
        Logger.d();
    }
    // 畫出轉盤
    public void circleSetImage(ArrayList<Place> type) {
        Draw draw = new Draw(getActivity(), type);
        ivCircle.setImageBitmap(draw.getImage());
        Logger.d();
    }
    // 當按下或放開時會改變透明度
    public void changeClickColor() {
        ibCatalog.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {  //按下的時候改變背景及顏色
                    ibCatalog.setImageAlpha(120);
                    ibNavigation.setImageAlpha(120);
                }
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {  //起來的時候恢復背景與顏色
                    ibCatalog.setImageAlpha(255);
                    ibNavigation.setImageAlpha(255);
                }
                return false;
            }
        });
        ibNavigation.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {  //按下的時候改變背景及顏色
                    ibNavigation.setImageAlpha(120);
                }
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {  //起來的時候恢復背景與顏色
                    ibNavigation.setImageAlpha(255);
                }
                return false;
            }
        });
        Logger.d();
    }
    // 設定音樂撥放及轉盤動畫
    public void setMusicClick() {
        ivMusicPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mp == null) {
                    mp = MediaPlayer.create(getActivity(), R.raw.location);
                }
                if(mp.isPlaying()) {
                    ivMusicPlay.setImageResource(R.drawable.music_play);
                    ivCircle.getAnimation().setDuration(5000);
                    Logger.d("music pause");
                }
                else {
                    if (!isStartCircleAni) {
                        stopNum = (int)(Math.random() * 360);
                        circleAm = new RotateAnimation(startNum, stopNum + 3600,
                                Animation.RELATIVE_TO_SELF, 0.5f,
                                Animation.RELATIVE_TO_SELF, 0.5f);
                        startNum = stopNum;

                        ivMusicPlay.setImageResource(R.drawable.music_pause);
                        mp.start();
                        circleAm.setDuration(20000);
                        circleAm.setFillAfter(true);
                        circleAm.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                ivMusicPlay.setImageResource(R.drawable.music_play);
                                mp.stop();
                                mp = MediaPlayer.create(getActivity(), R.raw.location);
                                isStartCircleAni = false;
                                setNavigationClick(decideWhichPlace());
                                Logger.d("music stop");
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                        if(!isStartNeedleAni) {
                            needleAm = new RotateAnimation(0, 20,
                                    Animation.RELATIVE_TO_SELF, 0.5f,
                                    Animation.RELATIVE_TO_PARENT, 0.5f);
                            needleAm.setDuration(2000);
                            needleAm.setFillAfter(true);
                            needleAm.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    isStartNeedleAni = true;
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });
                            ivNeedle.startAnimation(needleAm);
                        }
                        ivCircle.startAnimation(circleAm);
                        isStartCircleAni = true;
                        Logger.d(String.valueOf(isStartNeedleAni));
                        Logger.d("music start and circle animation start");
                    }
                    else {
                        ivMusicPlay.setImageResource(R.drawable.music_pause);
                        mp.start();
                        Logger.d("music start but circle animation still on");
                    }
                }
            }
        });
    }
    // 跳回目錄
    public void setJumpToCatalog() {
        ibCatalog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mp != null) {
                    mp.stop();
                }
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.executePendingTransactions();
                fragmentManager.popBackStackImmediate();
                fragmentManager.popBackStackImmediate();
                fragmentManager.popBackStackImmediate();
                Logger.d();
            }
        });
    }
    // 載入偏好設定
    public void loadPreferences() {
        // 存取使用者想要搜尋附近的哪一類型的景點
        SharedPreferences preferences = getActivity().getSharedPreferences("preferences", Context.MODE_PRIVATE);
        searchType = preferences.getString("type", "");
        Logger.d(searchType);

        Type listOfObjects = new TypeToken<List<Place>>(){}.getType();
        Gson gson = new Gson();
        String json;

        switch (searchType) {
            case "restaurant":
                json = preferences.getString("restaurantList", "");
                restaurant = gson.fromJson(json, listOfObjects);
                number = restaurant.size();
                circleSetImage(restaurant);
                break;
            case "movie_theater":
                json = preferences.getString("movieList", "");
                theater = gson.fromJson(json, listOfObjects);
                number = theater.size();
                circleSetImage(theater);
                break;
            case "park":
                json = preferences.getString("parkList", "");
                park = gson.fromJson(json, listOfObjects);
                number = park.size();
                circleSetImage(park);
                break;
            case "department_store":
                json = preferences.getString("storeList", "");
                store = gson.fromJson(json, listOfObjects);
                number = store.size();
                circleSetImage(store);
                break;
            case "cafe":
                json = preferences.getString("cafeList", "");
                cafe = gson.fromJson(json, listOfObjects);
                number = cafe.size();
                circleSetImage(cafe);
                break;
        }
    }
    private int decideWhichPlace() {
        switch (number) {
            case 2:
                if(stopNum >= 0 && stopNum <= 180) {
                    Logger.d("1");
                    return 1;
                }
                else {
                    Logger.d("2");
                    return 2;
                }
            case 3:
                if(stopNum >= 0 && stopNum <= 120) {
                    Logger.d("1");
                    return 1;
                }
                else if(stopNum > 120 && stopNum <= 240) {
                    Logger.d("2");
                    return 2;
                }
                else {
                    Logger.d("3");
                    return 3;
                }
            case 4:
                if(stopNum >= 0 && stopNum <= 90) {
                    Logger.d("1");
                    return 1;
                }
                else if(stopNum > 90 && stopNum <= 180) {
                    Logger.d("4");
                    return 4;
                }
                else if(stopNum > 180 && stopNum <= 270) {
                    Logger.d("3");
                    return 3;
                }
                else {
                    Logger.d("2");
                    return 2;
                }
            case 5:
                if(stopNum >= 0 && stopNum <= 72) {
                    Logger.d("1");
                    return 1;
                }
                else if(stopNum > 72 && stopNum <= 144) {
                    Logger.d("5");
                    return 5;
                }
                else if(stopNum > 144 && stopNum <= 216) {
                    Logger.d("4");
                    return 4;
                }
                else if(stopNum > 216 && stopNum <= 288) {
                    Logger.d("3");
                    return 3;
                }
                else {
                    Logger.d("2");
                    return 2;
                }
            default:
                return -1;
        }
    }

    public void setNavigationClick(final int place) {
        ibNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = getActivity().getSharedPreferences("preferences", Context.MODE_PRIVATE);
                String myLatitude = preferences.getString("myLatitude", "");
                String myLongitude = preferences.getString("myLongitude", "");
                int placeIndex = place - 1;
                String toLatitude = "";
                String toLongitude = "";
                switch (searchType) {
                    case "restaurant":
                        toLatitude = restaurant.get(placeIndex).getLat();
                        toLongitude = restaurant.get(placeIndex).getLng();
                        break;
                    case "movie_theater":
                        toLatitude = theater.get(placeIndex).getLat();
                        toLongitude = theater.get(placeIndex).getLng();
                        break;
                    case "park":
                        toLatitude = park.get(placeIndex).getLat();
                        toLongitude = park.get(placeIndex).getLng();
                        break;
                    case "department_store":
                        toLatitude = store.get(placeIndex).getLat();
                        toLongitude = store.get(placeIndex).getLng();
                        break;
                    case "cafe":
                        toLatitude = cafe.get(placeIndex).getLat();
                        toLongitude = cafe.get(placeIndex).getLng();
                        break;
                }
                String url = String.format("http://maps.google.com/maps?saddr=%s,%s&daddr=%s,%s", myLatitude,
                        myLongitude, toLatitude, toLongitude);
                Intent intent = new Intent();
                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });
    }
}
