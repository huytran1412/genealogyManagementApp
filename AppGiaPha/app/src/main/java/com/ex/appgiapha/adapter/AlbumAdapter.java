package com.ex.appgiapha.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
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

import com.ex.appgiapha.ChiTietAlbum;
import com.ex.appgiapha.R;
import com.ex.appgiapha.db.AlbumDAO;
import com.ex.appgiapha.model.Album;
import com.ex.appgiapha.model.CauChuyen;
import com.ex.appgiapha.model.ThanhVien;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder> {

    private Context mContext;
    private List<Album> mAlbumList;
    AlbumDAO albumDAO;
    EventAdapter.UpdateEvent updateEvent;
    public AlbumAdapter(Context context, List<Album> albumList, EventAdapter.UpdateEvent updateEvent) {
        mContext = context;
        mAlbumList = albumList;
        albumDAO = new AlbumDAO(context);
        this.updateEvent = updateEvent;
    }

    @NonNull
    @Override
    public AlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_album, parent, false);
        return new AlbumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Album album = mAlbumList.get(position);
        holder.tvName.setText(album.getTenAlbum());
        holder.cardView.setOnClickListener(v -> {
            // Xử lý sự kiện khi click vào item
            mContext.startActivity(new Intent(mContext, ChiTietAlbum.class)
                    .putExtra("id", album.getId())
                    .putExtra("tenAlbum", album.getTenAlbum()));
        });

        // Xử lý sự kiện nút more nếu cần
        holder.imageButtonMore.setOnClickListener(v -> {
            // Create a PopupMenu
            PopupMenu popup = new PopupMenu(mContext, holder.imageButtonMore);
            // Inflating the Popup using xml file
            popup.getMenuInflater().inflate(R.menu.edit_delete, popup.getMenu());
            // register popup with OnMenuItemClickListener
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {
                    if (item.getItemId() == R.id.delete) {
                         albumDAO.deleteAlbum(album.getId());
                        mAlbumList.remove(position);
                        notifyDataSetChanged();
                        return true;
                    } else if (item.getItemId() == R.id.edit) {
                        showDialog(album);
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
        return mAlbumList.size();
    }

    public class AlbumViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView tvName;
        ImageButton imageButtonMore;

        public AlbumViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card);
            tvName = itemView.findViewById(R.id.tvTen);
            imageButtonMore = itemView.findViewById(R.id.imageButtonMore);
        }
    }
    private void showDialog(Album currentItem) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.album_dialog, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        Button btnAdd = view.findViewById(R.id.btnAdd);
        EditText edtStory = view.findViewById(R.id.edtAlbum);
        edtStory.setText(currentItem.getTenAlbum());
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
            albumDAO.updateAlbum(new Album(currentItem.getId(), story, 1));
            Toast.makeText(mContext, "Thêm câu chuyện thành công", Toast.LENGTH_SHORT).show();
            updateEvent.onUpdateEvent();
            alertDialog.dismiss();
        });
    }
    public void filterList(List<Album> filteredList) {
        this.mAlbumList= filteredList;
        notifyDataSetChanged();
    }
}
