package com.ex.appgiapha;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class FullScreenImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image);
        FloatingActionButton fab = findViewById(R.id.btnAddGiaPha);
        fab.setOnClickListener(v -> finish());
        String imageUrl = getIntent().getStringExtra("link");
        PhotoView photoView = findViewById(R.id.photo_view);
        Glide.with(this)
                .load(imageUrl)
                .into(photoView);
    }
}