package com.example.chenfanxiao.assignment2;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Chenfan Xiao on 2016/10/25.
 */

public class DetailsAdapter extends ArrayAdapter {
    LayoutInflater inflater;
    Context context;
    ArrayList<String> data;
    public static ArrayList<Boolean> isSelected;

    public DetailsAdapter(Context context, ArrayList<String> data) {
        super(context, R.layout.row, data);
        this.context = context;
        this.data = data;
        isSelected = new ArrayList<Boolean>();
        initData();

    }

    public void initData()
    {
        for (int i=0;i<data.size();i++)
        {
            isSelected.add(i,false);
        }
    }

    @Override
    public View getView(int index, View row, ViewGroup parent) {

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        row = inflater.inflate(R.layout.row, parent, false);
        final int position = index;
        TextView nameText = (TextView) row.findViewById(R.id.RowText);

        final CheckBox cb = (CheckBox) row.findViewById(R.id.checkBox);
        if (isSelected.get(position)!=null)
            cb.setChecked(isSelected.get(position));
        nameText.setText(data.get(index));
        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isSelected.set(position, cb.isChecked());
                        if (cb.isChecked()) {
                            String itemName = data.get(position);
                            data.remove(position);
                            data.add(0, itemName);
                            boolean itemChecked = isSelected.get(position);
                            isSelected.remove(position);
                            isSelected.add(0, itemChecked);
                            notifyDataSetChanged();

                        }
                    }
                }, 500);


            }
        });

        //check box to do
        return row;
    }
}
