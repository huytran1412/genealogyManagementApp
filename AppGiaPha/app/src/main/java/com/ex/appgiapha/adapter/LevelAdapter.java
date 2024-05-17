package com.ex.appgiapha.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ex.appgiapha.DetailLevel;
import com.ex.appgiapha.R;
import com.ex.appgiapha.model.Level;

import java.util.List;

public class LevelAdapter extends RecyclerView.Adapter<LevelAdapter.LevelViewHolder> {

    private Context context;
    private List<Level> levelList;
    int idGiaPha;

    public LevelAdapter(Context context, List<Level> levelList, int idGiaPha) {
        this.idGiaPha = idGiaPha;
        this.context = context;
        this.levelList = levelList;
    }

    @NonNull
    @Override
    public LevelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_level, parent, false);
        return new LevelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LevelViewHolder holder, int position) {
        Level level = levelList.get(position);
        holder.textViewTenLevel.setText(context.getString(R.string.doi_thu)+" " + level.getName());
        holder.textViewSoLuongThanhVien.setText(context.getString(R.string.so_luong) +": "+ level.getSoLuongThanhVien());
        holder.cardViewGiaPha.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailLevel.class);
            intent.putExtra("id", level.getId());
            intent.putExtra("tenLevel", level.getName());
            intent.putExtra("idGiaPha", idGiaPha);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return levelList.size();
    }

    static class LevelViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTenLevel;
        TextView textViewSoLuongThanhVien;
        CardView cardViewGiaPha;

        public LevelViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTenLevel = itemView.findViewById(R.id.textViewTenLevel);
            textViewSoLuongThanhVien = itemView.findViewById(R.id.textViewSoLuongThanhVien);
            cardViewGiaPha = itemView.findViewById(R.id.cardViewGiaPha);
        }
    }
}
