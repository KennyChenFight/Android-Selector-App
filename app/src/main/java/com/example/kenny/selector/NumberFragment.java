package com.example.kenny.selector;


import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class NumberFragment extends Fragment {
    private TextView tvGo;
    private ImageButton ibCatalog;
    private ImageView ivSlotBall, ivQuestion1, ivQuestion2, ivQuestion3;
    private Wheel wheel1, wheel2, wheel3;
    private boolean isStarted;
    private MediaPlayer mp;

    public static final Random RANDOM = new Random();

    public static long randomLong(long lower, long upper) {
        return lower + (long) (RANDOM.nextDouble() * (upper - lower));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_number, container, false);
        hideBar();
        findViews(view);
        setSlotBallClick();
        changeClickColor();
        setJumpToCatalog();
        return view;
    }
    // 隱藏標題列及狀態列
    public void hideBar() {
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
    // 找到元件
    public void findViews(View view) {
        tvGo = view.findViewById(R.id.tvGo);
        ibCatalog = view.findViewById(R.id.catalog);
        ivSlotBall = view.findViewById(R.id.ivSlotBall);
        ivQuestion1 = view.findViewById(R.id.ivQuestion1);
        ivQuestion2 = view.findViewById(R.id.ivQuestion2);
        ivQuestion3 = view.findViewById(R.id.ivQuestion3);
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
    // 跳回目錄
    public void setJumpToCatalog() {
        ibCatalog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wheel1.stopWheel();
                wheel2.stopWheel();
                wheel3.stopWheel();
                if(mp != null) {
                    mp.stop();
                }
                if(!wheel1.isAlive() && !wheel2.isAlive() && !wheel3.isAlive()) {
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.executePendingTransactions();
                    fragmentManager.popBackStackImmediate();
                }
            }
        });
    }

    public void setSlotBallClick() {
        ivSlotBall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mp == null) {
                    mp = MediaPlayer.create(getActivity(), R.raw.number);
                    mp.setLooping(true);
                }
                if(tvGo.getText().equals("Start")) {
                    tvGo.setText("Stop");
                    mp.start();
                }
                else {
                    tvGo.setText("Start");
                    mp.pause();
                }
                if (isStarted) {
                    wheel1.stopWheel();
                    wheel2.stopWheel();
                    wheel3.stopWheel();

                    if(wheel1.currentIndex + 2 == 10) {
                        wheel1.currentIndex = 0;
                    }
                    isStarted = false;
                } else {
                    wheel1 = new Wheel(new Wheel.WheelListener() {
                        @Override
                        public void newImage(final int img) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ivQuestion1.setImageResource(img);
                                }
                            });
                        }
                    }, 30, randomLong(300, 400));
                    wheel1.start();

                    wheel2 = new Wheel(new Wheel.WheelListener() {
                        @Override
                        public void newImage(final int img) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ivQuestion2.setImageResource(img);
                                }
                            });
                        }
                    }, 30, randomLong(300, 790));
                    wheel2.start();

                    wheel3 = new Wheel(new Wheel.WheelListener() {
                        @Override
                        public void newImage(final int img) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ivQuestion3.setImageResource(img);
                                }
                            });
                        }
                    }, 30, randomLong(300, 900));
                    wheel3.start();
                    isStarted = true;
                }
            }
        });
    }
}
