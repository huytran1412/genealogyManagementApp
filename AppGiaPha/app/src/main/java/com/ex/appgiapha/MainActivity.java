package com.ex.appgiapha;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ex.appgiapha.adapter.GiaPhaAdapter;
import com.ex.appgiapha.databinding.ActivityMainBinding;
import com.ex.appgiapha.db.GiaPhaDAO;
import com.ex.appgiapha.dialog.CustomAlertDialog;
import com.ex.appgiapha.model.GiaPha;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    GiaPhaDAO giaPhaDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        setButtonEvent();
        giaPhaDAO = new GiaPhaDAO(this);
        makeListGiaPha();
    }
    void makeListGiaPha(){
        List<GiaPha> giaPhaList = giaPhaDAO.getAllGiaPha();
        GiaPhaAdapter giaPhaAdapter = new GiaPhaAdapter(giaPhaList, this);
        binding.rvGiaPha.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.rvGiaPha.setAdapter(giaPhaAdapter);
    }
    void setButtonEvent(){
        binding.btnAddGiaPha.setOnClickListener(v->{

        });
    }
}