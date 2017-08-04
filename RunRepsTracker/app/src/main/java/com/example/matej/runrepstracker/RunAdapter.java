package com.example.matej.runrepstracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Matej on 4.8.2017..
 */

public class RunAdapter extends BaseAdapter {
    private ArrayList<Run> mRuns;

    public RunAdapter(ArrayList<Run> runs) {
        mRuns = runs;
    }
    @Override
    public int getCount() {
        return this.mRuns.size();    }

    @Override
    public Object getItem(int position) {
        return this.mRuns.get(position);    }

    @Override
    public long getItemId(int position) {
        return position;    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RunAdapter.ViewHolder runViewHolder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.run_item, parent, false);
            runViewHolder = new ViewHolder(convertView);
            convertView.setTag(runViewHolder);
        } else {
            runViewHolder = (RunAdapter.ViewHolder) convertView.getTag();
        }

        Run run = this.mRuns.get(position);
        runViewHolder.tvTime.setText("Time:" + run.getTime());
        runViewHolder.tvDist.setText("Distance:" + run.getDist());
        return convertView;
    }
    void insertRun(Run run) {
        mRuns.add(run);
        notifyDataSetChanged();
    }

    public void deleteAt(int position) {
        mRuns.remove(position);
        notifyDataSetChanged();
    }

    public static class ViewHolder {
        public TextView tvTime, tvDist;

        public ViewHolder(View runView) {
            tvTime = (TextView) runView.findViewById(R.id.tvTime);
            tvDist = (TextView) runView.findViewById(R.id.tvDist);
        }
    }
}
