package com.example.kenny.selector;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ItemAdapter extends BaseAdapter {
    private Context context;
    private List<Item> itemList;

    ItemAdapter(Context context, List<Item> peopleList) {
        this.context = context;
        this.itemList = peopleList;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public View getView(int position, View itemView, ViewGroup parent) {
        if (itemView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            itemView = layoutInflater.inflate(R.layout.item_view, parent, false);
        }

        Item item = itemList.get(position);

        ImageView ivImage = itemView.findViewById(R.id.ivImage);
        TextView tvName = itemView.findViewById(R.id.tvName);
        ivImage.setImageResource(getDrawableID(item.getImageName()));
        tvName.setText(item.getName());

        switch(position % 5) {
            case 0:
                itemView.setBackgroundColor(context.getResources().getColor(R.color.colorItem01));
                break;
            case 1:
                itemView.setBackgroundColor(context.getResources().getColor(R.color.colorItem02));
                break;
            case 2:
                itemView.setBackgroundColor(context.getResources().getColor(R.color.colorItem03));
                break;
            case 3:
                itemView.setBackgroundColor(context.getResources().getColor(R.color.colorItem04));
                break;
            case 4:
                itemView.setBackgroundColor(context.getResources().getColor(R.color.colorItem05));
                break;
        }

        return itemView;
    }

    @Override
    public Object getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public int getDrawableID(String name) {
        String drawableName = name;
        int resID = context.getResources().getIdentifier(drawableName , "drawable", context.getPackageName());
        return resID;
    }
}
