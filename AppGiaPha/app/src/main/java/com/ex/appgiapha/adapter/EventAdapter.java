package com.ex.appgiapha.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ex.appgiapha.R;
import com.ex.appgiapha.db.SuKienDAO;
import com.ex.appgiapha.model.SuKien;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {
    public interface UpdateEvent {
        void onUpdateEvent();
    }

    private Context mContext;
    private List<SuKien> mEventList;

    SuKienDAO suKienDAO;
    UpdateEvent updateEvent;

    public EventAdapter(Context context, List<SuKien> eventList, UpdateEvent updateEvent) {
        mContext = context;
        mEventList = eventList;
        suKienDAO = new SuKienDAO(context);
        this.updateEvent = updateEvent;
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        public CardView cardView;
        public TextView textViewTenThanhVien;
        public ImageButton imageButtonMore;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardViewGiaPha);
            textViewTenThanhVien = itemView.findViewById(R.id.textViewTenThanhVien);
            imageButtonMore = itemView.findViewById(R.id.imageButtonMore);
        }
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_tv, parent, false);
        return new EventViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, @SuppressLint("RecyclerView") int position) {
        SuKien currentItem = mEventList.get(position);
        holder.textViewTenThanhVien.setText(currentItem.getTenSuKien());
        holder.imageButtonMore.setOnClickListener(v -> {
            // Create a PopupMenu
            PopupMenu popup = new PopupMenu(mContext, holder.imageButtonMore);
            // Inflating the Popup using xml file
            popup.getMenuInflater().inflate(R.menu.edit_delete, popup.getMenu());
            // register popup with OnMenuItemClickListener
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {
                    if (item.getItemId() == R.id.delete) {
                        suKienDAO.deleteSuKien(currentItem.getId());
                        mEventList.remove(position);
                        notifyDataSetChanged();
                        return true;
                    } else if (item.getItemId() == R.id.edit) {
                        showDialog(currentItem);
                        return true;
                    } else {
                        return false;
                    }
                }
            });
            popup.show(); //showing popup menu
        });
    }

    @Override
    public int getItemCount() {
        return mEventList.size();
    }
    private void showDialog(SuKien suKien) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.su_kien_dialog, null);
        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        EditText editTextFamilyTreeName = view.findViewById(R.id.editTextFamilyTreeName);
        Button buttonAddFamilyTree = view.findViewById(R.id.buttonAddFamilyTree);
        buttonAddFamilyTree.setText("Cập nhật");
        editTextFamilyTreeName.setText(suKien.getTenSuKien());
        buttonAddFamilyTree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String suKienS = editTextFamilyTreeName.getText().toString();
                if(!suKienS.isEmpty()) {
                    suKienDAO.updateSuKien(suKien.getId(), suKienS, suKien.getNgay());
                    alertDialog.dismiss();
                    updateEvent.onUpdateEvent();
                    Toast.makeText(mContext, "Cập nhật sự kiện thành công", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(mContext, "Vui lòng nhập tên sự kiện", Toast.LENGTH_SHORT).show();
                }
                alertDialog.dismiss(); // Đóng AlertDialog sau khi xử lý xong
            }
        });
    }
}
