package com.example.matej.runrepstracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Matej on 9.6.2017..
 */

public class SitUpAdapter extends BaseAdapter {
    private ArrayList<SitUp> mSitUps;

    public SitUpAdapter(ArrayList<SitUp> situps) {
        mSitUps = situps;
    }
    @Override
    public int getCount() {
        return this.mSitUps.size();    }

    @Override
    public Object getItem(int position) {
        return this.mSitUps.get(position);    }

    @Override
    public long getItemId(int position) {
        return position;    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SitUpAdapter.ViewHolder situpViewHolder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.situp_item, parent, false);
            situpViewHolder = new ViewHolder(convertView);
            convertView.setTag(situpViewHolder);
        } else {
            situpViewHolder = (SitUpAdapter.ViewHolder) convertView.getTag();
        }

        SitUp situp = this.mSitUps.get(position);
        situpViewHolder.tvSitUp.setText("Number of SitUps per set:" + situp.getSitUp());
        situpViewHolder.tvSets.setText("Number of sets:" + situp.getSets());
        return convertView;
    }
    void insertSitUp(SitUp situp) {
        mSitUps.add(situp);
        notifyDataSetChanged();
    }

    public void deleteAt(int position) {
        mSitUps.remove(position);
        notifyDataSetChanged();
    }

    public static class ViewHolder {
        public TextView tvSitUp, tvSets;

        public ViewHolder(View situpView) {
            tvSitUp = (TextView) situpView.findViewById(R.id.tvSitUp);
            tvSets = (TextView) situpView.findViewById(R.id.tvSet);
        }
    }
}
