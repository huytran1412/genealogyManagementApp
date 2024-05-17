package com.ex.appgiapha.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ex.appgiapha.LanguageManager;
import com.ex.appgiapha.R;
import com.ex.appgiapha.databinding.AccountFragBinding;
import com.ex.appgiapha.databinding.GiaphaFragBinding;

public class AccountFragment extends Fragment{
    public interface OnLanguageChangeListener {
        void onUpdateLanguage();
    }
    public OnLanguageChangeListener listener;
    public AccountFragment(OnLanguageChangeListener listener) {
        this.listener = listener;
    }
    AccountFragBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = AccountFragBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    void setUpLayout(){
        LanguageManager languageManager = new LanguageManager(getContext());
        String lang = languageManager.getLanguage();
        if(lang.equals("en")){
            binding.btnEnLang.setTextColor(getResources().getColor(R.color.gray));
        }else{
            binding.btnViLang.setTextColor(getResources().getColor(R.color.gray));
        }
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LanguageManager languageManager = new LanguageManager(getContext());
        binding.btnEnLang.setOnClickListener(v -> {
                languageManager.updateResource("en");
                listener.onUpdateLanguage();
        });
        binding.btnViLang.setOnClickListener(v -> {
            languageManager.updateResource("vi");
            listener.onUpdateLanguage();
        });
        setUpLayout();
    }
}
