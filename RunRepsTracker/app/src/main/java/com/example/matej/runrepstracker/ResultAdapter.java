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

public class ResultAdapter extends BaseAdapter {
    private ArrayList<Result> mResults;

    public ResultAdapter(ArrayList<Result> results) {
        mResults = results;
    }

    @Override
    public int getCount() {
        return this.mResults.size();
    }

    @Override
    public Object getItem(int position) {
        return this.mResults.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder resultViewHolder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.pushup_item, parent, false);
            resultViewHolder = new ViewHolder(convertView);
            convertView.setTag(resultViewHolder);
        } else {
            resultViewHolder = (ViewHolder) convertView.getTag();
        }

        Result result = this.mResults.get(position);
        resultViewHolder.tvPushUp.setText("Number of PushUps per set:" + result.getPushUp());
        resultViewHolder.tvSets.setText("Number of sets:" + result.getSets());
        return convertView;
    }

    void insert(Result result) {
        mResults.add(result);
        notifyDataSetChanged();
    }

    public void deleteAt(int position) {
        mResults.remove(position);
        notifyDataSetChanged();
    }

    public static class ViewHolder {
        public TextView tvPushUp, tvSets;

        public ViewHolder(View resultView) {
            tvPushUp = (TextView) resultView.findViewById(R.id.tvPushUp);
            tvSets = (TextView) resultView.findViewById(R.id.tvSet);
        }
    }
}
