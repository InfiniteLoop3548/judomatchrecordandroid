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
 * Created by Wilson on 11/11/2015.
 */
public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    private LayoutInflater inflater;
    private Context context;

    List<MatchRecordInformation> data = Collections.emptyList();

    public Adapter(Context context, List<MatchRecordInformation> data) {

        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;

    }

    public void delete(int position) {

        data.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, data.size());

    }

    public void add() {


    }

    @Override
    public  MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.match_record_row, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        MatchRecordInformation current = data.get(position);
        holder.matchTitle.setText(current.matchTitle);
        holder.opponentName.setText(current.opponentName);
        holder.beltColor.setText(current.beltColor);
        holder.tournament.setText(current.tournament);
        holder.result.setText(current.result);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView matchTitle, opponentName, beltColor, tournament, result;

        public MyViewHolder(View itemView) {
            super(itemView);

            matchTitle = (TextView) itemView.findViewById(R.id.fragmentMatchTitle);
            opponentName = (TextView) itemView.findViewById(R.id.fragmentOpponent);
            beltColor = (TextView) itemView.findViewById(R.id.fragmentBeltColor);
            tournament = (TextView) itemView.findViewById(R.id.fragmentTournament);
            result = (TextView) itemView.findViewById(R.id.fragmentResult);

        }
    }
}
