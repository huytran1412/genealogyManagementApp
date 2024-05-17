package com.ex.appgiapha;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ex.appgiapha.adapter.ThanhVienAdapter;
import com.ex.appgiapha.databinding.ActivityThongKeBinding;
import com.ex.appgiapha.db.ThanhVienDAO;
import com.ex.appgiapha.model.ThanhVien;

import java.util.ArrayList;
import java.util.List;

public class ThongKe extends AppCompatActivity {
    ThanhVienDAO thanhVienDAO;
    ActivityThongKeBinding binding;
    List<ThanhVien> listThanhVien;
    ThanhVienAdapter thanhVienAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityThongKeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        thanhVienDAO = new ThanhVienDAO(this);

        EditText searchView = findViewById(R.id.searchView);
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        makeList();
    }

    void makeList(){
        listThanhVien = thanhVienDAO.getAllMembersByGiaphaId(1);
        thanhVienAdapter = new ThanhVienAdapter(this, listThanhVien, -1);
        binding.rvLevel.setAdapter(thanhVienAdapter);
        binding.rvLevel.setLayoutManager(new LinearLayoutManager(this));
        updateGenderCount(listThanhVien);
    }

    void filter(String text){
        List<ThanhVien> filteredList = new ArrayList<>();
        for (ThanhVien thanhVien : listThanhVien) {
            if (thanhVien.getTen().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(thanhVien);
            }
        }
        thanhVienAdapter.filterList(filteredList);
        updateGenderCount(filteredList);
    }

    void updateGenderCount(List<ThanhVien> thanhViens) {
        int nam = 0;
        int nu = 0;
        for (ThanhVien thanhVien : thanhViens) {
            if (thanhVien.getGioiTinh() == 0) {
                nam++;
            } else {
                nu++;
            }
        }
        binding.tvNam.setText(getString(R.string.textViewGioiTinhNam) +": " + nam);
        binding.tvNu.setText(getString(R.string.textViewGioiTinhNu) +": " + nu);
    }
}
