package com.ex.appgiapha.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.ex.appgiapha.FullScreenImageActivity;
import com.ex.appgiapha.R;
import com.ex.appgiapha.db.AnhDAO;
import com.ex.appgiapha.model.Anh;

import java.util.List;

public class AnhAdapter extends RecyclerView.Adapter<AnhAdapter.ImageViewHolder> {
    private Context context;
    private List<Anh> imageUrls;

    AnhDAO anhDAO;

    public AnhAdapter(Context context, List<Anh> imageUrls) {
        this.context = context;
        this.imageUrls = imageUrls;
        anhDAO = new AnhDAO(context);
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.anh_item, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Anh anh = imageUrls.get(position);
        holder.bind(anh);
    }

    @Override
    public int getItemCount() {
        return imageUrls.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgView);
        }

        public void bind(Anh anh) {
            // Load image using Glide library
            Glide.with(context)
                    .load(Uri.parse(anh.getLink()))
                    .placeholder(R.drawable.picture) // Placeholder image while loading
                    .error(R.drawable.picture)// Error image if loading fails
                    .into(imageView);
            // Click listener for image
            imageView.setOnClickListener(v -> {
                Intent intent = new Intent(context, FullScreenImageActivity.class);
                intent.putExtra("link", anh.getLink());
                context.startActivity(intent);
            });
            imageView.setOnLongClickListener(v -> {
                PopupMenu popup = new PopupMenu(context, imageView);
                // Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.delete, popup.getMenu());
                // register popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.delete) {
                            anhDAO.deleteAnh(anh.getId());
                            imageUrls.remove(anh);
                            notifyItemRemoved(getAdapterPosition());
                            return true;
                        }
                        return false;
                    }
                });
                popup.show();
                return true;
            });
        }

    }
}
