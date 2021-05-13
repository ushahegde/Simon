package com.hegdeapps.simonsays;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Adapter for Simon cells.
 * Created by hegde on 07-06-2016.
 */
class MySimonGridAdapter extends BaseAdapter {

    private final LayoutInflater mLayoutInflater;
    private final int mLayoutResId;
    private final ArrayList<SimonCell> mList;
    private final Context mContext;



    public MySimonGridAdapter(Context ctx, ArrayList<SimonCell> cellList, int layoutResId) {
        mContext = ctx;
        mList = cellList;
        mLayoutInflater = LayoutInflater.from(ctx);
        mLayoutResId = layoutResId;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public SimonCell getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup viewGroup) {
        if(convertView==null){
            convertView = mLayoutInflater.inflate(mLayoutResId,null);
        }
        SimonCell cell = mList.get(pos);
        Drawable dr;
        if(cell.getState()==SimonCell.ON) {
             dr = mContext.getResources().getDrawable(cell.onDrawable);
        }else{
             dr = mContext.getResources().getDrawable(cell.offDrawable);
        }
        ImageView imV  = (ImageView)convertView;
        imV.setImageDrawable(dr);
        return convertView;
    }
}
