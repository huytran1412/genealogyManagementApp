package com.ex.appgiapha.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ex.appgiapha.model.ThanhVien;

import java.util.List;

public class ThanhVienSpinnerAdapter extends ArrayAdapter<ThanhVien> {

    private Context context;
    private List<ThanhVien> thanhVienList;

    public ThanhVienSpinnerAdapter(Context context, int resource, List<ThanhVien> objects) {
        super(context, resource, objects);
        this.context = context;
        this.thanhVienList = objects;
    }

    @Override
    public int getCount() {
        return thanhVienList.size();
    }

    @Nullable
    @Override
    public ThanhVien getItem(int position) {
        return thanhVienList.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
        }

        TextView textViewTenThanhVien = convertView.findViewById(android.R.id.text1);
        ThanhVien currentItem = getItem(position);
        if (currentItem != null) {
            textViewTenThanhVien.setText(currentItem.getTen());
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThanhVien selectedItem = getItem(position);
                if (selectedItem != null) {
                    int selectedMemberId = selectedItem.getId();
                    Log.d("Spinner", "Selected member ID: " + selectedMemberId);
                }
            }
        });

        return convertView;
    }
}
