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

public class YesOrNoFragment extends Fragment {
    private ImageButton ibCatalog, ibText;
    private ImageView ivMusicPlay, ivCircle, ivNeedle;
    private List<Item> yesOrNoList = new ArrayList<>();
    private MediaPlayer mp;
    private int textButtonflag = 0;
    private Animation circleAm, needleAm;
    private int stopNum, startNum;
    private boolean isStartCircleAni = false,  isStartNeedleAni = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_yes_or_no, container, false);
        hideBar();
        findViews(view);
        circleSetImage();
        changeClickColor();
        setMusicClick();
        setJumpToCatalog();
        setCircleTextVisible();
        return view;
    }
    // 隱藏標題列及狀態列
    public void hideBar() {
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
    // 找到元件
    public void findViews(View view) {
        ibCatalog = view.findViewById(R.id.catalog);
        ibText = view.findViewById(R.id.ibText);
        ivMusicPlay = view.findViewById(R.id.ivMusicPlay);
        ivCircle = view.findViewById(R.id.ivCircle);
        ivNeedle = view.findViewById(R.id.needle);
    }
    // 畫出轉盤
    public void circleSetImage() {
        Draw draw = new Draw(getActivity(), yesOrNoList, true);
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
    // 設定音樂撥放及轉盤動畫
    public void setMusicClick() {
        ivMusicPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mp == null) {
                    mp = MediaPlayer.create(getActivity(), R.raw.yes);
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
                                mp = MediaPlayer.create(getActivity(), R.raw.yes);
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
    public void setCircleTextVisible() {
        ibText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ivCircle.getVisibility() == View.VISIBLE && textButtonflag == 0) {
                    Draw draw = new Draw(getActivity(), yesOrNoList, false);
                    ivCircle.setImageBitmap(draw.getImage());
                    textButtonflag = 1;
                }
                else {
                    Draw draw = new Draw(getActivity(), yesOrNoList, true);
                    ivCircle.setImageBitmap(draw.getImage());
                    textButtonflag = 0;
                }
            }
        });
    }
}
