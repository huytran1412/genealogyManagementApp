package com.ex.appgiapha.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ex.appgiapha.R;
import com.ex.appgiapha.adapter.AlbumAdapter;
import com.ex.appgiapha.adapter.EventAdapter;
import com.ex.appgiapha.databinding.KhoAnhBinding;
import com.ex.appgiapha.db.AlbumDAO;
import com.ex.appgiapha.model.Album;

import java.util.ArrayList;
import java.util.List;

public class KhoAnhFragment extends Fragment{
    KhoAnhBinding binding;
    List<Album> albumList = new ArrayList<>();
    AlbumDAO albumDAO;
    AlbumAdapter albumAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = KhoAnhBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnAddGiaPha.setOnClickListener(v -> {
            showDialog();
        });
        albumDAO = new AlbumDAO(this.getContext());
        makeList();
    }
    void makeList(){
        albumList =albumDAO.getAllAlbum();
        albumAdapter = new AlbumAdapter(this.getContext(), albumList, new EventAdapter.UpdateEvent() {
            @Override
            public void onUpdateEvent() {
                makeList();
            }
        });
        binding.rvLevel.setLayoutManager(new LinearLayoutManager(this.getContext(),LinearLayoutManager.VERTICAL, false));
        binding.rvLevel.setAdapter(albumAdapter);
        binding.searchView.addTextChangedListener(new TextWatcher() {
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
    }
    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        LayoutInflater inflater = LayoutInflater.from(this.getContext());
        View view = inflater.inflate(R.layout.album_dialog, null);
        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        Button btnAdd = view.findViewById(R.id.btnAdd);
        EditText edtStory = view.findViewById(R.id.edtAlbum);
        btnAdd.setOnClickListener(v -> {
            String albumName = edtStory.getText().toString();
            if(albumName.isEmpty()){

                Toast.makeText(this.getContext(), "Vui lòng nhap ten ", Toast.LENGTH_SHORT).show();
                return;
            }
            long rs =  albumDAO.addAlbum(new Album(albumName, 1));
            if(rs > 0){
                Toast.makeText(this.getContext(), "Them thanh cong", Toast.LENGTH_SHORT).show();
            }
            makeList();
            alertDialog.dismiss();
        });
    }
    void filter(String text){
        List<Album> filteredList = new ArrayList<>();
        for (Album album : albumList) {
            if (album.getTenAlbum().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(album);
            }
        }
        albumAdapter.filterList(filteredList);
    }


}
