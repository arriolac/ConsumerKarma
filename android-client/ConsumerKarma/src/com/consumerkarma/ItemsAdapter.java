package com.consumerkarma;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.consumerkarma.datastructure.Item;

public class ItemsAdapter extends ArrayAdapter<Item> {

    private List<Item> mItems;
    
    public ItemsAdapter(Context context, List<Item> items) {
        super(context, R.layout.item_cell, items);
        mItems = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        
        Item item = mItems.get(position);
        if (row == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            row = inflater.inflate(R.layout.item_cell, null);
        }
        
        // Init text
        ImageView img = (ImageView) row.findViewById(R.id.item_img);
        TextView title = (TextView) row.findViewById(R.id.item_title);
        title.setText(item.getTitle());
        
        return row;
    }
}
