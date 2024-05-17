package com.ex.appgiapha;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ex.appgiapha.adapter.ThanhVienAdapter;
import com.ex.appgiapha.databinding.ActivityDetailLevelBinding;
import com.ex.appgiapha.db.ThanhVienDAO;
import com.ex.appgiapha.dialog.ThanhVienDialog;
import com.ex.appgiapha.model.ThanhVien;

import java.util.List;

public class DetailLevel extends AppCompatActivity {
    ActivityDetailLevelBinding binding;
    ThanhVienDAO thanhVienDAO;
    int id ;
    int tenLevel = 0;
    int idGiaPha;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityDetailLevelBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        id= getIntent().getIntExtra("id", 0);
        idGiaPha = getIntent().getIntExtra("idGiaPha", 0);
       tenLevel  = getIntent().getIntExtra("tenLevel", 0);
        binding.tenLevel.setText(getString(R.string.doi_thu)+" "+tenLevel);
        binding.btnAddGiaPha.setOnClickListener(v -> {
            ThanhVienDialog dialog = new ThanhVienDialog(this, idGiaPha, tenLevel, new ThanhVienDialog.OnUpdatedListener() {
                @Override
                public void onUpdated() {
                    makeList();
                }
            });
            dialog.show();
        });
        thanhVienDAO = new ThanhVienDAO(this);
        makeList();

    }

    @Override
    protected void onResume() {
        super.onResume();
        makeList();
    }

    void makeList(){
      List<ThanhVien> listThanhVien = thanhVienDAO.getThanhVienByIdLevel(id);
        ThanhVienAdapter thanhVienAdapter = new ThanhVienAdapter(this,listThanhVien,tenLevel);
        binding.rvLevel.setAdapter(thanhVienAdapter);
        binding.rvLevel.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
    }

}