package com.ex.appgiapha.fragment;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ex.appgiapha.R;
import com.ex.appgiapha.adapter.EventAdapter;
import com.ex.appgiapha.databinding.EventFragmentBinding;
import com.ex.appgiapha.db.SuKienDAO;
import com.ex.appgiapha.model.SuKien;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class SuKienFragment extends Fragment {
    private EventFragmentBinding binding;
    SuKienDAO suKienDAO;
    String selectedDate;
    private Handler handler = new Handler(Looper.getMainLooper());
    List<SuKien> listSuKien =new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = EventFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpView();
        startUpdatingTime();
        suKienDAO = new SuKienDAO(this.getContext());
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // Tháng bắt đầu từ 0 nên cần cộng thêm 1
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        selectedDate = String.format("%02d/%02d/%d", dayOfMonth, month, year);
        binding.calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                // Định dạng ngày
                selectedDate = String.format(Locale.getDefault(), "%02d/%02d/%d", dayOfMonth, (month + 1), year);
                makeList();
            }
        });
        makeList();
    }

    private void setUpView() {
        binding.calendarView.setFirstDayOfWeek(Calendar.MONDAY);
    }

    private void startUpdatingTime() {
        handler.post(updateTimeRunnable);
    }

    private Runnable updateTimeRunnable = new Runnable() {
        @Override
        public void run() {
            updateTime();
            handler.postDelayed(this, 60000); // Cập nhật mỗi phút
        }
    };

    private void updateTime() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1; // Tháng bắt đầu từ 0
        int year = calendar.get(Calendar.YEAR);

        // Hiển thị thông tin ngày giờ lên TextViews
        binding.time.setText(String.format("%02d:%02d", hour, minute));
        binding.time2.setText(String.valueOf(day));
        binding.time3.setText(String.valueOf(month));
        binding.time4.setText(String.valueOf(year));
        binding.btnAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

    }
    void makeList(){
        listSuKien = suKienDAO.getSuKienByNgay(1,selectedDate);
        binding.recyclerViewEvent.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));
        binding.recyclerViewEvent.setAdapter(new EventAdapter(this.getContext(), listSuKien,new EventAdapter.UpdateEvent() {
            @Override
            public void onUpdateEvent() {
                makeList();
            }
        }));
        if(listSuKien.size() == 0){
            binding.cardViewEvent.setVisibility(View.VISIBLE);}
        else{
            binding.cardViewEvent.setVisibility(View.GONE);
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacks(updateTimeRunnable);
    }
    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        LayoutInflater inflater = LayoutInflater.from(this.getContext());
        View view = inflater.inflate(R.layout.su_kien_dialog, null);
        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        EditText editTextFamilyTreeName = view.findViewById(R.id.editTextFamilyTreeName);
        Button buttonAddFamilyTree = view.findViewById(R.id.buttonAddFamilyTree);

        buttonAddFamilyTree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String suKien = editTextFamilyTreeName.getText().toString();
                if(!suKien.isEmpty()) {
                    suKienDAO.addSuKien(1,suKien, selectedDate);
                    alertDialog.dismiss();
                    makeList();
                    Toast.makeText(getContext(), "Thêm sự kiện thành công", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getContext(), "Vui lòng nhập tên sự kiện", Toast.LENGTH_SHORT).show();
                }
                alertDialog.dismiss(); // Đóng AlertDialog sau khi xử lý xong
            }
        });
    }
}

