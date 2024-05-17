package com.ex.appgiapha.adapter;

import static java.security.AccessController.getContext;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ex.appgiapha.ChiTietThanhVien;
import com.ex.appgiapha.R;
import com.ex.appgiapha.db.ThanhVienDAO;
import com.ex.appgiapha.model.ThanhVien;

import java.util.List;

public class ThanhVienAdapter extends RecyclerView.Adapter<ThanhVienAdapter.ViewHolder> {

    private List<ThanhVien> thanhVienList;
    private Context context;
    ThanhVienDAO thanhVienDAO;
    int tenLevel;

    public ThanhVienAdapter(Context context, List<ThanhVien> thanhVienList, int tenLevel) {
        this.context = context;
        this.thanhVienList = thanhVienList;
        thanhVienDAO = new ThanhVienDAO(context);
        this.tenLevel = tenLevel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tv, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ThanhVien thanhVien = thanhVienList.get(position);
        holder.textViewTenThanhVien.setText(thanhVien.getTen());
        if(tenLevel == -1){
            holder.btnMore.setVisibility(View.GONE);
        }
        holder.btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context, holder.btnMore);
                popupMenu.inflate(R.menu.edit_delete); // Inflate menu resource
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getItemId() == R.id.edit) {
                            Intent intent = new Intent(context, ChiTietThanhVien.class);
                            intent.putExtra("tv", thanhVien);
                            intent.putExtra("tenLevel", tenLevel);
                            context.startActivity(intent);
                            return  true;
                        }
                        if (item.getItemId() == R.id.delete) {
                            showDeleteConfirmationDialog(thanhVien.getId(),position);
                            return true;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }
    private void showDeleteConfirmationDialog(int id,int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Xác nhận xoá");
        builder.setMessage("Bạn có chắc muốn xoá thành viên này?");
        builder.setPositiveButton("Xoá", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              boolean rs =  thanhVienDAO.deleteThanhVien(id);
                if(rs){
                    thanhVienList = thanhVienDAO.getThanhVienByIdLevel(tenLevel);
                    notifyDataSetChanged();
                }
            }
        });
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
    void updateListWhenDelete(int position){
        thanhVienList.remove(position);
        notifyItemRemoved(position);
    }
    @Override
    public int getItemCount() {
        return thanhVienList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTenThanhVien;
        CardView cardViewGiaPha;
        ImageButton btnMore;
        public ViewHolder(View itemView) {
            super(itemView);
            textViewTenThanhVien = itemView.findViewById(R.id.textViewTenThanhVien);
            cardViewGiaPha = itemView.findViewById(R.id.cardViewGiaPha);
            btnMore = itemView.findViewById(R.id.imageButtonMore);
        }
    }

    public void filterList(List<ThanhVien> filteredList) {
        this.thanhVienList= filteredList;
        notifyDataSetChanged();
    }

}
