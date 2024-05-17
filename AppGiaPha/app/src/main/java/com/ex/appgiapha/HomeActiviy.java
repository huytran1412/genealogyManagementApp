package com.ex.appgiapha;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ex.appgiapha.databinding.ActivityHomeActiviyBinding;
import com.ex.appgiapha.fragment.AccountFragment;
import com.ex.appgiapha.fragment.HomeFragment;
import com.ex.appgiapha.fragment.KhoAnhFragment;
import com.ex.appgiapha.fragment.SuKienFragment;

public class HomeActiviy extends AppCompatActivity {
    ActivityHomeActiviyBinding binding;
    static String currentFragment = "home";
    static final String HOME_FRAGMENT = "home";
    static final String EVENT_FRAGMENT = "event";
    static final String IMAGE_FRAGMENT = "image";
    static final String ACCOUNT_FRAGMENT = "account";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LanguageManager languageManager = new LanguageManager(this);
        languageManager.updateResource(languageManager.getLanguage());
        EdgeToEdge.enable(this);
        binding = ActivityHomeActiviyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportFragmentManager().beginTransaction().replace(binding.fmContainer.getId(), new HomeFragment()).commit();
        binding.bottomNav.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_home_gia_pha) {
                replaceFragment(HOME_FRAGMENT);
            }
            if (item.getItemId() == R.id.nav_home_su_kien) {
                replaceFragment(EVENT_FRAGMENT);
            }
            if (item.getItemId() == R.id.nav_home_kho_anh) {
                replaceFragment(IMAGE_FRAGMENT);
            }
            if (item.getItemId() == R.id.nav_home_tai_khoan) {
                replaceFragment(ACCOUNT_FRAGMENT);
            }
            return true;
        });
    }
    void replaceFragment(String key){
            if(key.equals(HOME_FRAGMENT) && !currentFragment.equals(HOME_FRAGMENT)) {
                getSupportFragmentManager().beginTransaction().replace(binding.fmContainer.getId(), new HomeFragment()).commit();
            }
            if(key.equals(EVENT_FRAGMENT) && !currentFragment.equals(EVENT_FRAGMENT)) {
                getSupportFragmentManager().beginTransaction().replace(binding.fmContainer.getId(), new SuKienFragment()).commit();
            }
            if(key.equals(IMAGE_FRAGMENT) && !currentFragment.equals(IMAGE_FRAGMENT)) {
                getSupportFragmentManager().beginTransaction().replace(binding.fmContainer.getId(), new KhoAnhFragment()).commit();
            }
            if(key.equals(ACCOUNT_FRAGMENT) && !currentFragment.equals(ACCOUNT_FRAGMENT)) {
                getSupportFragmentManager().beginTransaction().replace(binding.fmContainer.getId(), new AccountFragment(new AccountFragment.OnLanguageChangeListener() {
                    @Override
                    public void onUpdateLanguage() {
                        binding.bottomNav.setSelectedItemId(R.id.nav_home_gia_pha);
                        recreate();
                    }
                })).commit();
            }
            currentFragment = key;
    }
}