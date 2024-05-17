package com.ex.appgiapha.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
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
import androidx.recyclerview.widget.RecyclerView;

import com.ex.appgiapha.R;
import com.ex.appgiapha.db.CauChuyenDAO;
import com.ex.appgiapha.model.CauChuyen;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CauChuyenAdapter extends RecyclerView.Adapter<CauChuyenAdapter.CauChuyenViewHolder> {
    private Context mContext;
    private List<CauChuyen> cauChuyenList;
    CauChuyenDAO cauChuyenDAO;
    EventAdapter.UpdateEvent updateEvent;
    public CauChuyenAdapter(Context context, List<CauChuyen> cauChuyenList, EventAdapter.UpdateEvent updateEvent) {
        this.mContext = context;
        this.cauChuyenList = cauChuyenList;
        cauChuyenDAO = new CauChuyenDAO(context);
        this.updateEvent = updateEvent;
    }

    @NonNull
    @Override
    public CauChuyenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_story, parent, false);
        return new CauChuyenViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CauChuyenViewHolder holder, @SuppressLint("RecyclerView") int position) {
        CauChuyen cauChuyen = cauChuyenList.get(position);
        holder.tvContent.setText(cauChuyen.getContent());
        holder.tvTime.setText(cauChuyen.getNgayTao());
        holder.imageButtonMore.setOnClickListener(v -> {
            // Create a PopupMenu
            PopupMenu popup = new PopupMenu(mContext, holder.imageButtonMore);
            // Inflating the Popup using xml file
            popup.getMenuInflater().inflate(R.menu.edit_delete, popup.getMenu());
            // register popup with OnMenuItemClickListener
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {
                    if (item.getItemId() == R.id.delete) {
                        cauChuyenDAO.deleteCauChuyen(cauChuyen.getId());
                        cauChuyenList.remove(position);
                        notifyDataSetChanged();
                        return true;
                    } else if (item.getItemId() == R.id.edit) {
                        showDialog(cauChuyen);
                        return true;
                    } else {
                        return false;
                    }
                }
            });
            popup.show();
        });
    }

    @Override
    public int getItemCount() {
        return cauChuyenList.size();
    }

    public class CauChuyenViewHolder extends RecyclerView.ViewHolder {
        TextView tvTime, tvContent;
        ImageButton imageButtonMore;

        public CauChuyenViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTime = itemView.findViewById(R.id.tvTIme);
            tvContent = itemView.findViewById(R.id.tvContent);
            imageButtonMore = itemView.findViewById(R.id.imageButtonMore);
        }
    }
    private void showDialog(CauChuyen currentItem) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.cau_chuyen_dialog, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        Button btnAdd = view.findViewById(R.id.btnAddStory);
        EditText edtStory = view.findViewById(R.id.edtStory);
        edtStory.setText(currentItem.getContent());
        btnAdd.setOnClickListener(v -> {
            Date currentDate = new Date();
            // Định dạng ngày tháng năm giờ:phút ngày/tháng/năm
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd/MM/yyyy", Locale.getDefault());

            // Chuyển đổi và hiển thị ngày tháng năm theo định dạng
            String formattedDate = sdf.format(currentDate);
            String story = edtStory.getText().toString();
            if(story.isEmpty()){
                Toast.makeText(mContext, "Vui lòng nhập câu chuyện", Toast.LENGTH_SHORT).show();
                return;
            }
            cauChuyenDAO.updateCauChuyen(new CauChuyen(currentItem.getId(), story, formattedDate,1));
            Toast.makeText(mContext, "Thêm câu chuyện thành công", Toast.LENGTH_SHORT).show();
            updateEvent.onUpdateEvent();
            alertDialog.dismiss();
        });
    }
}
