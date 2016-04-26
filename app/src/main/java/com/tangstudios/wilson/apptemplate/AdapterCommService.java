package com.tangstudios.wilson.apptemplate;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by Wilson on 3/8/2016.
 */
public class AdapterCommService extends RecyclerView.Adapter<AdapterCommService.MyViewHolder>  {

    private LayoutInflater inflater;
    private Context context;

    List<CommServiceInformation> data = Collections.emptyList();

    public AdapterCommService(Context context, List<CommServiceInformation> data) {

        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;

    }

    public void delete(int position) {

        data.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, data.size());

    }

    @Override
    public  MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.comm_service_row, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        CommServiceInformation current = data.get(position);
        holder.number.setText("" + (position + 1));
        holder.activity.setText(current.activity);
        holder.date.setText(current.date);
        holder.time.setText(current.time);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView number, activity, date, time;

        public MyViewHolder(View itemView) {
            super(itemView);

            number = (TextView) itemView.findViewById(R.id.fragmentNumber);
            activity = (TextView) itemView.findViewById(R.id.fragmentActivity);
            date = (TextView) itemView.findViewById(R.id.fragmentDate);
            time = (TextView) itemView.findViewById(R.id.fragmentTime);

        }
    }

}
