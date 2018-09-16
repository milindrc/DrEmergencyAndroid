package com.matrixdev.dremergency.Controllers.Problem;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.matrixdev.dremergency.Models.Tips.TipData;
import com.matrixdev.dremergency.R;

import java.util.ArrayList;

public class TipsAdapter extends RecyclerView.Adapter<TipsAdapter.VH> {
    ArrayList<TipData> data;
    ProblemActivity activity;
    TipsFragment fragment;

    public TipsAdapter(ArrayList<TipData> data, ProblemActivity activity, TipsFragment fragment) {
        this.data = data;
        this.activity = activity;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tips_item,null,false);
        return new VH(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.setup(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class VH extends RecyclerView.ViewHolder {
        private final View view;
        private final TextView tipText;

        public VH(View itemView) {
            super(itemView);
            view = itemView;
            tipText = (TextView)itemView.findViewById(R.id.tip_data);
        }


        public void setup(final TipData data) {
            tipText.setText(data.getSolution());
        }
    }


}
