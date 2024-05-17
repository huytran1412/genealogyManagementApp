package com.ex.appgiapha;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ex.appgiapha.adapter.LevelAdapter;
import com.ex.appgiapha.databinding.ActivityChiTietGiaPhaBinding;
import com.ex.appgiapha.db.GiaPhaDAO;
import com.ex.appgiapha.db.LevelDAO;
import com.ex.appgiapha.db.ThanhVienDAO;
import com.ex.appgiapha.model.GiaPha;
import com.ex.appgiapha.model.GiaPhaTree;
import com.ex.appgiapha.model.Level;
import com.ex.appgiapha.model.ThanhVien;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ChiTietGiaPhaActivity extends AppCompatActivity {
    ActivityChiTietGiaPhaBinding binding;
    int id ;
    ThanhVienDAO thanhVienDAO;
    LevelDAO levelDAO;
    GiaPhaDAO giaPhaDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChiTietGiaPhaBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        id= getIntent().getIntExtra("id", 1);
        String tenGiaPha = getIntent().getStringExtra("tenGiaPha");
        setContentView(binding.getRoot());
        binding.tenGiapha.setText(tenGiaPha);
        thanhVienDAO = new ThanhVienDAO(this);
        levelDAO = new LevelDAO(this);
        setButtonEvent();
        binding.btnBack.setOnClickListener(v->{
            finish();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        makeList();
    }

    void setButtonEvent(){
        binding.btnAddGiaPha.setOnClickListener(v->{
            LevelDAO levelDAO = new LevelDAO(this);
            levelDAO.addLevelForGiaPha(id);
            makeList();
        });
    }

    void makeList(){
        LevelDAO levelDAO = new LevelDAO(this);
        levelDAO.getLevelsByGiaPhaID(id);
        LevelAdapter levelAdapter = new LevelAdapter(this,levelDAO.getLevelsByGiaPhaID(id),id);
        binding.rvLevel.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        binding.rvLevel.setAdapter(levelAdapter);
    }
}