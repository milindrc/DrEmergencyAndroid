package com.matrixdev.dremergency.Controllers.Dashboard;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.matrixdev.dremergency.Controllers.Problem.ProblemActivity;
import com.matrixdev.dremergency.Models.Problem.ProblemData;
import com.matrixdev.dremergency.R;
import com.matrixdev.dremergency.Utils.Constants;

import java.util.ArrayList;

public class ProblemAdapter extends RecyclerView.Adapter<ProblemAdapter.VH> {
    DashboardActivity activity;
    ArrayList<ProblemData> data;

    public ProblemAdapter(DashboardActivity activity, ArrayList<ProblemData> data) {
        this.activity = activity;
        this.data = data;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.problem_card_layout, null, false);

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
        private final TextView cardName;
        private final View view;
        private final ImageView cardImage;

        public VH(View itemView) {
            super(itemView);
            view = itemView;
            cardName = (TextView) itemView.findViewById(R.id.card_name);
            cardImage = (ImageView) itemView.findViewById(R.id.card_image);
        }

        void setup(final ProblemData problemData) {
            if (problemData.getProfile() != null && problemData.getProfile().length() != 0) {
                if (problemData.getProfile().contains(","))
                    Glide.with(activity).load(problemData.getProfile().split(",")[0]).into(cardImage);
                else
                    Glide.with(activity).load(problemData.getProfile()).into(cardImage);
            } else
                cardImage.setImageDrawable(null);

            cardName.setText(problemData.getName());
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(activity, ProblemActivity.class);
                    intent.putExtra(Constants.INTENT_PROBLEM,problemData);
                    activity.startActivity(intent);
                }
            });
        }
    }
}
