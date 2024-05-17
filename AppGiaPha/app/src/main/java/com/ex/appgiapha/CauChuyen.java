package com.ex.appgiapha;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ex.appgiapha.adapter.CauChuyenAdapter;
import com.ex.appgiapha.adapter.EventAdapter;
import com.ex.appgiapha.databinding.ActivityCauChuyenBinding;
import com.ex.appgiapha.db.CauChuyenDAO;
import com.ex.appgiapha.model.SuKien;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CauChuyen extends AppCompatActivity {
    ActivityCauChuyenBinding binding;
    CauChuyenDAO cauChuyenDAO;
    CauChuyenAdapter adapter;
    List<com.ex.appgiapha.model.CauChuyen> cauChuyenList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityCauChuyenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnAddGiaPha.setOnClickListener(v -> {
    showDialog();
        });
        binding.btnBack.setOnClickListener(v -> {
            onBackPressed();
        });
        cauChuyenDAO = new CauChuyenDAO(this);
    }

void makeList(){
        cauChuyenList = cauChuyenDAO.getAllCauChuyen();
        adapter = new CauChuyenAdapter(this, cauChuyenList, new EventAdapter.UpdateEvent() {
            @Override
            public void onUpdateEvent() {
                makeList();
            }
        });
        binding.rvCauChuyen.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(this, androidx.recyclerview.widget.LinearLayoutManager.VERTICAL, false));
        binding.rvCauChuyen.setAdapter(adapter);

}
    @Override
    protected void onResume() {
        super.onResume();
        makeList();
    }
    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.cau_chuyen_dialog, null);
        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        Button btnAdd = view.findViewById(R.id.btnAddStory);
        EditText edtStory = view.findViewById(R.id.edtStory);
        btnAdd.setOnClickListener(v -> {
            Date currentDate = new Date();

            // Định dạng ngày tháng năm giờ:phút ngày/tháng/năm
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd/MM/yyyy", Locale.getDefault());

            // Chuyển đổi và hiển thị ngày tháng năm theo định dạng
            String formattedDate = sdf.format(currentDate);
            Log.d("TAG", "showDialog: " + formattedDate);
            String story = edtStory.getText().toString();
            if(story.isEmpty()){
                Toast.makeText(this, "Vui lòng nhập câu chuyện", Toast.LENGTH_SHORT).show();
                return;
            }
          cauChuyenDAO.addCauChuyen(new com.ex.appgiapha.model.CauChuyen(story, formattedDate, 1));
            Toast.makeText(this, "Thêm câu chuyện thành công", Toast.LENGTH_SHORT).show();
            makeList();
            alertDialog.dismiss();
        });
    }
}