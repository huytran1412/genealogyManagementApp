package com.ex.appgiapha;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import java.util.Locale;

public class LanguageManager {
    SharedPreferences sharedPreferences;
    Context c;
    public LanguageManager(Context c) {
        this.c = c;
        sharedPreferences = c.getSharedPreferences("LANG", Context.MODE_PRIVATE);
    }
    public void updateResource(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        c.getResources().updateConfiguration(config, c.getResources().getDisplayMetrics());
        saveLanguage(lang);
    }
    void saveLanguage(String lang) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("lang", lang);
        editor.apply();
    }
    public String getLanguage() {
        return sharedPreferences.getString("lang", "en");
    }
}
