package com.ex.appgiapha.dialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.ex.appgiapha.R;
import com.ex.appgiapha.adapter.ThanhVienSpinnerAdapter;
import com.ex.appgiapha.db.ThanhVienDAO;
import com.ex.appgiapha.model.ThanhVien;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
public class ThanhVienDialog extends Dialog implements
        android.view.View.OnClickListener  {
    ThanhVienDAO thanhVienDAO;
    public Context context;
    public Dialog d;
    int giaPhaId = 1;
    public Button btnSave, btnCancel;
    public EditText editTextTenThanhVien, editTextNgaySinh, editTextDiaChi, editTextEmail, editTextMat;
    public Spinner spinnerConCua;
    private TextView textViewTenThanhVienLabel, textViewNgaySinhLabel, textViewMatLabel;
    private Calendar myCalendar = Calendar.getInstance();
    private RadioButton radioButtonNam, radioButtonNu;
    int tenLevel;
    private DatePickerDialog.OnDateSetListener date;
    int idSelected =-1 ;
    List<ThanhVien> listThanhVienDoiTren = new ArrayList<>();
    ThanhVienDialog.OnUpdatedListener listener;
    int gioiTinh = 0;
    public ThanhVienDialog(Context context, int giaPhaId,int tenLevel,ThanhVienDialog.OnUpdatedListener listener) {
        super(context);
        this.context = context;
        this.giaPhaId = giaPhaId;
        this.tenLevel = tenLevel;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setUpView();
        btnSave = findViewById(R.id.buttonThem);
        btnCancel = findViewById(R.id.buttonHuy);
        editTextTenThanhVien = findViewById(R.id.editTextTenThanhVien);
        editTextNgaySinh = findViewById(R.id.editTextNgaySinh);
        editTextDiaChi = findViewById(R.id.editTextDiaChi);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextMat = findViewById(R.id.editTextMat);
        spinnerConCua = findViewById(R.id.spinnerConCua);
        textViewTenThanhVienLabel = findViewById(R.id.textViewTenThanhVienLabel);
        radioButtonNam = findViewById(R.id.radioButtonNam);
        radioButtonNu = findViewById(R.id.radioButtonNu);
        radioButtonNam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gioiTinh = 0;
            }
        });
        radioButtonNu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gioiTinh = 1;
            }
        });
        btnSave.setOnClickListener(this);
        if(tenLevel==1){
            spinnerConCua.setVisibility(View.GONE);
        }
        btnCancel.setOnClickListener(this);
        setDatePickerListener(editTextNgaySinh, ngaySinhDateListener);
        setDatePickerListener(editTextMat, ngayMatDateListener);
        thanhVienDAO = new ThanhVienDAO(context);
        getThanhVienDoiTren();

    }

    void setUpView() {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.add_thanhvien_dialog);
        Window window = this.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawableResource(android.R.color.transparent);
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
    }
    void getThanhVienDoiTren(){
        int position=0;
        listThanhVienDoiTren = thanhVienDAO.getThanhVienByDoiAndGiaPha(tenLevel-1,giaPhaId);
       if(!listThanhVienDoiTren.isEmpty()){
           idSelected = listThanhVienDoiTren.get(0).getId();
       }
        ArrayAdapter<String> dataAdapter2;
        List<String> listName = new ArrayList<>();
        for (ThanhVien thanhVien : listThanhVienDoiTren) {
            listName.add(thanhVien.getTen());
           position = listName.indexOf(thanhVien.getTen());
        }
        dataAdapter2 = new ArrayAdapter<String>(this.getContext(), R.layout.item_sp, listName);
        spinnerConCua.setAdapter(dataAdapter2);
        // set the default value of the spinner
        spinnerConCua.setSelection(position);
        // Thiết lập sự kiện khi một mục được chọn trong Spinner
        // set the listener for the spinner item selection set itemSelected
        spinnerConCua.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                new DatePickerDialog(context, listener, myCalendar
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
            updateLabel(editTextNgaySinh);
        }
    };

    private final DatePickerDialog.OnDateSetListener ngayMatDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel(editTextMat);
        }
    };

    private void updateLabel(EditText editText) {
        String myFormat = "dd/MM/yyyy"; // In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editText.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.buttonThem){
            if (validateInput()) {
                ThanhVien thanhVien = new ThanhVien(
                        editTextTenThanhVien.getText().toString(),
                        editTextNgaySinh.getText().toString(),
                        editTextDiaChi.getText().toString(),
                        editTextMat.getText().toString(),
                        editTextEmail.getText().toString(),
                        idSelected,
                        tenLevel,
                        giaPhaId,
                        gioiTinh
                );
                thanhVienDAO.addThanhVien(thanhVien, giaPhaId);
                Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                listener.onUpdated();
                dismiss();
            }
        } else if(v.getId()==R.id.buttonHuy){
            dismiss();
        }
    }

    private boolean validateInput() {
        boolean valid = true;
        String ten = editTextTenThanhVien.getText().toString();
        String ngaySinh = editTextNgaySinh.getText().toString();
        String email = editTextEmail.getText().toString();
        String ngayMat = editTextMat.getText().toString();
        // Validate ten (name)
        if (TextUtils.isEmpty(ten)) {
            textViewTenThanhVienLabel.setError("Tên không được để trống");
            valid = false;
        } else {
            textViewTenThanhVienLabel.setError(null);
        }

        // Validate email
      if(!TextUtils.isEmpty(email)){
          if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
              editTextEmail.setError("Email không hợp lệ");
              valid = false;
          } else {
              editTextEmail.setError(null);
          }
      }
      if(tenLevel!=1){
          if(idSelected==-1){
              Toast.makeText(context, "Vui lòng chọn thành viên đời trên", Toast.LENGTH_SHORT).show();
              valid = false;
          }
      }
        return valid;
    }
    public interface OnUpdatedListener {
        void onUpdated();
    }
}
