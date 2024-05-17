package com.ex.appgiapha.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ex.appgiapha.CauChuyen;
import com.ex.appgiapha.ChiTietGiaPhaActivity;
import com.ex.appgiapha.FMTree;
import com.ex.appgiapha.MainActivity;
import com.ex.appgiapha.R;
import com.ex.appgiapha.ThongKe;
import com.ex.appgiapha.databinding.GiaphaFragBinding;
import com.ex.appgiapha.db.GiaPhaDAO;
import com.ex.appgiapha.db.LevelDAO;
import com.ex.appgiapha.db.ThanhVienDAO;
import com.ex.appgiapha.dialog.CustomAlertDialog;
import com.ex.appgiapha.model.GiaPhaInfo;
import com.ex.appgiapha.model.GiaPhaTree;
import com.ex.appgiapha.model.Level;
import com.ex.appgiapha.model.ThanhVien;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment{
    GiaphaFragBinding binding;
    String json;
    LevelDAO levelDAO;
    GiaPhaDAO giaPhaDAO;
    GiaPhaInfo giaPhaInfo;
    ThanhVienDAO thanhVienDAO;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = GiaphaFragBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        thanhVienDAO = new ThanhVienDAO(this.getContext());
        giaPhaDAO = new GiaPhaDAO(this.getContext());
        levelDAO = new LevelDAO(this.getContext());
        binding.rlDsTv.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ChiTietGiaPhaActivity.class);
            startActivity(intent);
        });
        binding.rlGiaPha.setOnClickListener(v -> {
            Intent intent = new Intent(this.getContext(), FMTree.class);
            intent.putExtra("id",1);
            intent.putExtra("json",json);
            startActivity(intent);
        });
        binding.layoutGP.btnEdit.setOnClickListener(v -> {
            CustomAlertDialog.showAddFamilyTreeDialog(HomeFragment.this.getContext(), new CustomAlertDialog.OnAddFamilyTreeListener() {
                @Override
                public void onAddFamilyTree(String familyTreeName) {
                    giaPhaDAO.updateGiaPha(1,familyTreeName);
                    fillData();
                    Toast.makeText(HomeFragment.this.getContext(), "Cập nhật gia phả thành công", Toast.LENGTH_SHORT).show();
                }
            }, giaPhaInfo.getTenGiaPha());
        });
        binding.csMXH.setOnClickListener(v -> {
            Intent intent = new Intent(this.getContext(), ThongKe.class);
            startActivity(intent);
        });
        binding.rlTKBC.setOnClickListener(v -> {
            Intent intent = new Intent(this.getContext(), CauChuyen.class);
            startActivity(intent);
        });
    }

    @Override
    public void onResume() {
        getDataGiaPha();
        fillData();
        super.onResume();
    }
    @SuppressLint("SetTextI18n")
    void fillData(){
        giaPhaInfo = giaPhaDAO.getGiaPhaInfoById(1);
        binding.layoutGP.rvLevel.setText(giaPhaInfo.getSoLuongLevel()+" "+getString( R.string.doi2));
        binding.layoutGP.rvTv.setText(giaPhaInfo.getSoLuongThanhVien()+" "+ getString(R.string.thanh_vien));
        binding.layoutGP.tenGiapha.setText(giaPhaInfo.getTenGiaPha());
    }
    void getDataGiaPha(){
        List<GiaPhaTree> listGiaPha = new ArrayList<>();
        List<Level> levelList = levelDAO.getLevelsByGiaPhaID(1);
        for(int i =0;i<levelList.size();i++){
            List<ThanhVien> listThanhVien = thanhVienDAO.getThanhVienByIdLevel(levelList.get(i).getId());
            if(!listThanhVien.isEmpty()){
                listGiaPha.add(new GiaPhaTree(levelList.get(i).getName(),listThanhVien));
            }
        }
        Gson gson = new Gson();
        json = gson.toJson(listGiaPha);

    }
}
