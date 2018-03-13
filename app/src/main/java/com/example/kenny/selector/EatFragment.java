package com.example.kenny.selector;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class EatFragment extends Fragment {
    private ImageButton ibCatalog, ibEnter;
    private ImageView ivMusicPlay, ivCircle, ivNeedle;
    private SwipeMenuListView swipeMenuListView;
    private ImageButton ibAdd;
    private List<Item> eatList = new ArrayList<>();
    private ItemAdapter eatAdapter;
    private MediaPlayer mp;
    private final int itemFragment = 1;
    private Animation circleAm, needleAm;
    private int stopNum, startNum;
    private boolean isStartCircleAni = false,  isStartNeedleAni = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_eat, container, false);
        hideBar();
        findViews(view);
        handleListView();
        circleSetImage();
        changeClickColor();
        setEnterListViewClick();
        setMusicClick();
        setJumpToCatalog();
        setJumpToActivityItemPage();
        return view;
    }
    // 隱藏標題列及狀態列
    public void hideBar() {
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
    // 找到元件
    public void findViews(View view) {
        ibCatalog = view.findViewById(R.id.catalog);
        swipeMenuListView = view.findViewById(R.id.listView);
        ibEnter = view.findViewById(R.id.enter);
        ibAdd = view.findViewById(R.id.add);
        ivMusicPlay = view.findViewById(R.id.ivMusicPlay);
        ivCircle = view.findViewById(R.id.ivCircle);
        ivNeedle = view.findViewById(R.id.needle);
    }
    // 處理listView的項目及顯示
    public void handleListView() {
        if(checkAlreadySaveDefault()) {
            loadPreferences();
        }
        else {
            eatList = getPeopleList();
            eatAdapter = new ItemAdapter(getActivity(), eatList);
            swipeMenuListView.setAdapter(eatAdapter);
            savePreferences();
        }
    }
    // 檢查是否已經存入預設的偏好設定
    public boolean checkAlreadySaveDefault() {
        SharedPreferences preferences = getActivity().getSharedPreferences("preferences", Context.MODE_PRIVATE);
        String json = preferences.getString("eatList", null);
        if(json != null) {
            return true;
        }
        else {
            return false;
        }
    }
    // 載入偏好設定
    public void loadPreferences() {
        Type listOfObjects = new TypeToken<List<Item>>(){}.getType();
        SharedPreferences preferences = getActivity().getSharedPreferences("preferences", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString("eatList", "");
        eatList = gson.fromJson(json, listOfObjects);
        eatAdapter = new ItemAdapter(getActivity(), eatList);
        swipeMenuListView.setAdapter(eatAdapter);
        setSwipeMenuCreator();
        setOnMenuItemClick();
    }
    // 儲存偏好設定
    public void savePreferences() {
        Gson gson = new Gson();
        Type listOfObjects = new TypeToken<List<Item>>(){}.getType();
        String strObject = gson.toJson(eatList, listOfObjects);
        SharedPreferences preferences = getActivity().getSharedPreferences("preferences", Context.MODE_PRIVATE);
        preferences.edit().putString("eatList",  strObject).apply();
        setSwipeMenuCreator();
        setOnMenuItemClick();
    }
    // 在listView新增刪除按鍵
    public void setSwipeMenuCreator() {
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getActivity().getApplicationContext());
                deleteItem.setWidth(110);
                deleteItem.setIcon(R.drawable.minus);
                menu.addMenuItem(deleteItem);
            }
        };

        swipeMenuListView.setMenuCreator(creator);
    }
    // 設定listView的滑動刪除
    public void setOnMenuItemClick() {
        swipeMenuListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        if(position > 3) {
                            eatList.remove(position);
                            eatAdapter = new ItemAdapter(getActivity(), eatList);
                            swipeMenuListView.setAdapter(eatAdapter);
                            savePreferences();
                            break;
                        }
                }
                return false;
            }
        });
    }
    // 取得預設people的項目數量
    public List<Item> getPeopleList() {
        eatList.clear();
        eatList.add(new Item("people_item01", "Kenny"));
        eatList.add(new Item("people_item02", "Nicole"));
        eatList.add(new Item("people_item03", "Johnny"));
        eatList.add(new Item("people_item04", "Ted"));
        return eatList;
    }
    // 畫出轉盤
    public void circleSetImage() {
        Type listOfObjects = new TypeToken<List<Item>>(){}.getType();
        SharedPreferences preferences = getActivity().getSharedPreferences("preferences", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString("eatList", "");
        eatList = gson.fromJson(json, listOfObjects);
        Draw draw = new Draw(getActivity(), eatList, true);
        ivCircle.setImageBitmap(draw.getImage());
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
    }
    // 顯示listView
    public void setEnterListViewClick() {
        ibEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 如果listView顯示，則轉盤上的東西全部隱藏
                if(swipeMenuListView.getVisibility() == View.INVISIBLE) {
                    ivCircle.clearAnimation();
                    isStartCircleAni = false;
                    startNum = 0;
                    ivCircle.setVisibility(View.INVISIBLE);
                    ivMusicPlay.setVisibility(View.INVISIBLE);
                    swipeMenuListView.setVisibility(View.VISIBLE);
                    ivNeedle.clearAnimation();
                    isStartNeedleAni = false;
                    ivNeedle.setVisibility(View.INVISIBLE);
                    Logger.d();
                }
                else {
                    swipeMenuListView.setVisibility(View.INVISIBLE);
                    circleSetImage();
                    ivCircle.setVisibility(View.VISIBLE);
                    ivMusicPlay.setVisibility(View.VISIBLE);
                    ivNeedle.setVisibility(View.VISIBLE);
                    isStartNeedleAni = false;
                    mp = MediaPlayer.create(getActivity(), R.raw.eat);
                    Logger.d();
                }
            }
        });
    }
    // 設定音樂撥放及轉盤動畫
    public void setMusicClick() {
        ivMusicPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mp == null) {
                    mp = MediaPlayer.create(getActivity(), R.raw.eat);
                    Logger.d();
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
                                mp = MediaPlayer.create(getActivity(), R.raw.eat);
                                isStartCircleAni = false;
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
                        Logger.d("music start");
                    }
                    else {
                        ivMusicPlay.setImageResource(R.drawable.music_pause);
                        mp.start();
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
            }
        });
    }
    // 跳到新增ActivityItem的頁面
    public void setJumpToActivityItemPage() {
        ibAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment eatFragment = EatFragment.this;
                Fragment eatItemFragment = new EatItemFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                eatItemFragment.setTargetFragment(EatFragment.this, itemFragment);

                if (!eatItemFragment.isAdded()) {
                    transaction.add(R.id.topicContainer, eatItemFragment, eatItemFragment.getClass().getName());
                    transaction.addToBackStack(eatItemFragment.getClass().getName());
                }
                else {
                    transaction.show(eatItemFragment);
                }
                transaction.hide(eatFragment);
                transaction.commit();
            }
        });
    }
    // 讀入item所新增的項目而新的偏好設定
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == itemFragment && resultCode == getActivity().RESULT_OK) {
            loadPreferences();
        }
    }
}
