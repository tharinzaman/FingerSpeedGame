package com.example.fingerspeed2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.fingerspeed2.ROOM.AttemptEntityJava;
import java.util.List;

import kotlinx.coroutines.flow.Flow;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private List<AttemptEntityJava> attempts;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvDate;
        private final TextView tvResult;
        private final TextView tvTapsPerSecond;
        private final TextView tvTapsCompleted;

        public ViewHolder(View view) {
            super(view);
            tvDate = view.findViewById(R.id.tvDate);
            tvResult = view.findViewById(R.id.tvResult);
            tvTapsPerSecond = view.findViewById(R.id.tvTapsPerSecond);
            tvTapsCompleted = view.findViewById(R.id.tvTapsCompleted);
        }
    }

    public HistoryAdapter(List<AttemptEntityJava> attempts) {
        this.attempts = attempts;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.history_table_layout, viewGroup, false);
    return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvDate.setText(attempts.get(position).getDate());
        holder.tvResult.setText(attempts.get(position).getResult().toString());
        holder.tvTapsPerSecond.setText(String.valueOf(attempts.get(position).getTapsPerSecond()));
        holder.tvTapsCompleted.setText(String.valueOf(attempts.get(position).getTapsCompleted()));
    }

    @Override
    public int getItemCount() {
        return attempts.size();
    }

}
