package com.example.kenny.selector;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class EatItemFragment extends Fragment {
    private ImageButton btAddItem, btExit, btPeopleItem01, btPeopleItem02,
            btPeopleItem03, btPeopleItem04, btPeopleItem05, btPeopleItem06,
            btPeopleItem07, btPeopleItem08, btPeopleItem09, btPeopleItem10;
    private ImageView square;
    private EditText enterName;
    private List<Item> eatList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_eat_item, container, false);
        findViews(view);
        hideBar();
        setPeopleItemClick();
        setFinish();
        setExit();
        return view;
    }
    // 找到元件
    public void findViews(View view) {
        btAddItem = view.findViewById(R.id.additem);
        square = view.findViewById(R.id.square);
        enterName = view.findViewById(R.id.enterName);
        btExit = view.findViewById(R.id.exit);
        btPeopleItem01 = view.findViewById(R.id.people_item01);
        btPeopleItem02 = view.findViewById(R.id.people_item02);
        btPeopleItem03 = view.findViewById(R.id.people_item03);
        btPeopleItem04 = view.findViewById(R.id.people_item04);
        btPeopleItem05 = view.findViewById(R.id.people_item05);
        btPeopleItem06 = view.findViewById(R.id.people_item06);
        btPeopleItem07 = view.findViewById(R.id.people_item07);
        btPeopleItem08 = view.findViewById(R.id.people_item08);
        btPeopleItem09 = view.findViewById(R.id.people_item09);
        btPeopleItem10 = view.findViewById(R.id.people_item10);
    }
    // 隱藏標題列及狀態列
    public void hideBar() {
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
    // 點擊icon並將之顯示在輸入框
    public void setPeopleItemClick() {
        btPeopleItem01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                square.setTag("people_item01");
                square.setImageResource(R.drawable.people_item01);
            }
        });

        btPeopleItem02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                square.setTag("people_item02");
                square.setImageResource(R.drawable.people_item02);
            }
        });

        btPeopleItem03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                square.setTag("people_item03");
                square.setImageResource(R.drawable.people_item03);
            }
        });

        btPeopleItem04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                square.setTag("people_item04");
                square.setImageResource(R.drawable.people_item04);
            }
        });

        btPeopleItem05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                square.setTag("people_item05");
                square.setImageResource(R.drawable.people_item05);
            }
        });

        btPeopleItem06.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                square.setTag("people_item06");
                square.setImageResource(R.drawable.people_item06);
            }
        });

        btPeopleItem07.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                square.setTag("people_item07");
                square.setImageResource(R.drawable.people_item07);
            }
        });

        btPeopleItem08.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                square.setTag("people_item08");
                square.setImageResource(R.drawable.people_item08);
            }
        });

        btPeopleItem09.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                square.setTag("people_item09");
                square.setImageResource(R.drawable.people_item09);
            }
        });

        btPeopleItem10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                square.setTag("people_item10");
                square.setImageResource(R.drawable.people_item10);
            }
        });
    }
    // 新增Item後跳回ActivityFragment
    public void setFinish() {
        btAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePreferences();
                Intent intent = new Intent();
                getTargetFragment().onActivityResult(getTargetRequestCode(), getActivity().RESULT_OK, intent);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment sourceFragment = EatItemFragment.this;
                fragmentTransaction.remove(sourceFragment);
                fragmentTransaction.commit();
                fragmentManager.executePendingTransactions();
                fragmentManager.popBackStackImmediate();
            }
        });
    }
    // 沒有新增item並跳回ActivityFragment
    public void setExit() {
        btExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.executePendingTransactions();
                fragmentManager.popBackStackImmediate();
            }
        });
    }
    // 取得該圖片的名稱
    private String getDrawableName(ImageView iv) {
        return (String) iv.getTag();
    }
    // 儲存偏好設定
    public void savePreferences() {
        loadPreferences();
        SharedPreferences preferences = getActivity()
                .getSharedPreferences("preferences", MODE_PRIVATE);
        String name = enterName.getText().toString();
        String imageName = getDrawableName(square);
        Item people = new Item(imageName, name);
        eatList.add(people);
        Gson gson = new Gson();
        Type listOfObjects = new TypeToken<List<Item>>(){}.getType();
        String strObject = gson.toJson(eatList, listOfObjects);
        preferences.edit().putString("eatList",  strObject).apply();
    }
    // 載入偏好設定
    public void loadPreferences() {
        Type listOfObjects = new TypeToken<List<Item>>(){}.getType();
        SharedPreferences preferences = getActivity().getSharedPreferences("preferences", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString("eatList", "");
        eatList = gson.fromJson(json, listOfObjects);
    }
}
