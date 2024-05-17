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

import com.ex.appgiapha.ChiTietGiaPhaActivity;
import com.ex.appgiapha.R;
import com.ex.appgiapha.model.GiaPha;

import java.util.List;

public class GiaPhaAdapter extends RecyclerView.Adapter<GiaPhaAdapter.ViewHolder> {

    private List<GiaPha> giaPhaList;
    private Context context;

    public GiaPhaAdapter(List<GiaPha> giaPhaList, Context context) {
        this.giaPhaList = giaPhaList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_giapha, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GiaPha giaPha = giaPhaList.get(position);
        holder.textViewTenGiaPha.setText(giaPha.getTenGiaPha());
        holder.textViewSoDoi.setText("Số đời: " + giaPha.getSoLuongDoi());
        holder.cardView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ChiTietGiaPhaActivity.class);
            intent.putExtra("id", giaPha.getId());
            intent.putExtra("tenGiaPha", giaPha.getTenGiaPha());
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return giaPhaList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTenGiaPha, textViewSoDoi;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTenGiaPha = itemView.findViewById(R.id.textViewTenGiaPha);
            textViewSoDoi = itemView.findViewById(R.id.textViewSoDoi);
            cardView = itemView.findViewById(R.id.cardViewGiaPha);
        }
    }
}
