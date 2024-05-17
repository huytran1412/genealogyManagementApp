package com.ex.appgiapha;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ex.appgiapha.databinding.ActivityChiTietThanhVienBinding;
import com.ex.appgiapha.db.ThanhVienDAO;
import com.ex.appgiapha.dialog.ThanhVienDialog;
import com.ex.appgiapha.model.ThanhVien;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ChiTietThanhVien extends AppCompatActivity  {
    ActivityChiTietThanhVienBinding binding;
    ThanhVienDAO thanhVienDAO;
    public Dialog d;
    int giaPhaId = 1;
    public Button btnSave, btnCancel;
    private Calendar myCalendar = Calendar.getInstance();
    int tenLevel;
    private DatePickerDialog.OnDateSetListener date;
    int idSelected =-1 ;
    List<ThanhVien> listThanhVienDoiTren = new ArrayList<>();
    int gioiTinh = 0;
    ThanhVien thanhVien;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChiTietThanhVienBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        thanhVienDAO = new ThanhVienDAO(this);
        tenLevel = getIntent().getIntExtra("tenLevel", 1);
        thanhVien = (ThanhVien) getIntent().getSerializableExtra("tv");
        binding.editTextDiaChi.setText(thanhVien.getDiaChi());
        binding.editTextEmail.setText(thanhVien.getEmail());
        binding.editTextNgaySinh.setText(thanhVien.getNgaySinh());
        binding.editTextTenThanhVien.setText(thanhVien.getTen());
        binding.editTextMat.setText(thanhVien.getNgayMat());
        Log.d("ThanhVien", "onCreate: "+thanhVien.getGioiTinh());
        binding.radioButtonNam.setChecked(thanhVien.getGioiTinh() == 0);
        binding.radioButtonNu.setChecked(thanhVien.getGioiTinh() == 1);
        if(tenLevel==1){
            binding.spinnerConCua.setVisibility(View.GONE);
        }else{
            getThanhVienDoiTren();
        }
        binding.radioButtonNam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gioiTinh = 0;
            }
        });
        binding.radioButtonNu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gioiTinh = 1;
            }
        });
        if(tenLevel==1){
            binding.spinnerConCua.setVisibility(View.GONE);
        }
        setDatePickerListener(        binding.editTextNgaySinh, ngaySinhDateListener);
        setDatePickerListener(        binding.editTextMat, ngayMatDateListener);
        setEvent();
    }
    void getThanhVienDoiTren(){
        int position=0;
        listThanhVienDoiTren = thanhVienDAO.getThanhVienByDoiAndGiaPha(tenLevel-1,giaPhaId);
        if(!listThanhVienDoiTren.isEmpty()){
            idSelected = listThanhVienDoiTren.get(0).getId();
        }
        // initial spinner
        List<Integer> listId = new ArrayList<>();
        for (ThanhVien thanhVien : listThanhVienDoiTren) {
            listId.add(thanhVien.getId());
        }
        position = listId.indexOf(thanhVien.getConCua());
        Log.d("position", "getThanhVienDoiTren: "+position);
        ArrayAdapter<String> dataAdapter2;
        List<String> listName = new ArrayList<>();
        for (ThanhVien thanhVien : listThanhVienDoiTren) {
            listName.add(thanhVien.getTen());
        }
        dataAdapter2 = new ArrayAdapter<String>(this, R.layout.item_sp, listName);
        binding.spinnerConCua.setAdapter(dataAdapter2);
        // set the default value of the spinner
        binding.spinnerConCua.setSelection(position);
        // Thiết lập sự kiện khi một mục được chọn trong Spinner
        // set the listener for the spinner item selection set itemSelected
        binding.spinnerConCua.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                idSelected  = listThanhVienDoiTren.get(position).getId();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });
    }
    private void setDatePickerListener(final EditText editText, final DatePickerDialog.OnDateSetListener listener) {
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(ChiTietThanhVien.this, listener, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private final DatePickerDialog.OnDateSetListener ngaySinhDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel(binding.editTextNgaySinh);
        }
    };

    private final DatePickerDialog.OnDateSetListener ngayMatDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel(binding.editTextMat);
        }
    };

    private void updateLabel(EditText editText) {
        String myFormat = "dd/MM/yyyy"; // In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editText.setText(sdf.format(myCalendar.getTime()));
    }
    void setEvent(){
        Log.d("ThanhVien", "setEvent: "+thanhVien.getGioiTinh());
       binding.btnBack.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               finish();
           }
       });
       binding.btnSave.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               int id = thanhVien.getId();
               if (validateInput()) {
                   ThanhVien thanhVien = new ThanhVien(
                            id,
                           binding.editTextTenThanhVien.getText().toString(),
                           binding.editTextNgaySinh.getText().toString(),
                           binding.editTextDiaChi.getText().toString(),
                           binding.editTextMat.getText().toString(),
                           binding.editTextEmail.getText().toString(),
                           idSelected,
                           tenLevel,
                           giaPhaId,
                           gioiTinh
                   );
                int rs=   thanhVienDAO.updateThanhVien(thanhVien);
                if(rs>0){
                    Toast.makeText(ChiTietThanhVien.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
               }else{
                    Toast.makeText(ChiTietThanhVien.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                }
               }
           }
       });
    }

    private boolean validateInput() {
        boolean valid = true;
        String ten = binding.editTextTenThanhVien.getText().toString();
        String ngaySinh = binding.editTextNgaySinh.getText().toString();
        String email = binding.editTextEmail.getText().toString();
        String ngayMat = binding.editTextMat.getText().toString();
        // Validate ten (name)
        if (TextUtils.isEmpty(ten)) {
            binding.textViewTenThanhVienLabel.setError("Tên không được để trống");
            valid = false;
        } else {
            binding.textViewTenThanhVienLabel.setError(null);
        }

        // Validate email
        if(!TextUtils.isEmpty(email)){
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.editTextEmail.setError("Email không hợp lệ");
                valid = false;
            } else {
                binding.editTextEmail.setError(null);
            }
        }
        if(tenLevel!=1){
            if(idSelected==-1){
                Toast.makeText(this, "Vui lòng chọn thành viên đời trên", Toast.LENGTH_SHORT).show();
                valid = false;
            }
        }
        return valid;
    }
}