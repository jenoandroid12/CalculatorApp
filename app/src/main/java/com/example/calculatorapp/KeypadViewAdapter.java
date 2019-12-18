package com.example.calculatorapp;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class KeypadViewAdapter extends BaseAdapter {

    private ArrayList<KeyJDO> mKeypadList;
    private Context mContext;

    public KeypadViewAdapter(Context pContext, ArrayList<KeyJDO> pKeypadList) {
        mKeypadList =pKeypadList;
        mContext =pContext;
    }

    @Override
    public int getCount() {
        return mKeypadList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        TextView lKeypadItem;
        View keypadView;
        KeyJDO lKeyJDO = mKeypadList.get(position);
        keypadView = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.gridlayout_item, null);
        lKeypadItem =keypadView.findViewById(R.id.keypad_keys);
        lKeypadItem.setText(lKeyJDO.getText());
        lKeypadItem.setTextColor(lKeyJDO.getColor());
        lKeypadItem.setBackgroundResource(lKeyJDO.getBackGround());

        return keypadView;
    }

}
